package dominion.agent;

import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class DominionClassFileTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader classLoader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!className.startsWith("org/mockito/Mockito")) {//org.mockito.stubbing.Stubber
            return null;
        }

        try {
//            System.out.println("Profiling: " + className);
            return addProfilingInformation(className, classBeingRedefined, classfileBuffer);
        }
        catch (NotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }

    public byte[] addProfilingInformation(String className, Class<?> clazz,
                                          byte[] b) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        CtClass cl = null;
        byte[] returnBytes = null;
        try {
            cl = pool.makeClass(new java.io.ByteArrayInputStream(b));

            if (cl.isInterface() == false) {

                CtMethod[] methods = cl.getDeclaredMethods();
                for (int i = 0; i < methods.length; i++) {

                    if (methods[i].isEmpty() == false) {
                        addProfilingInformation(cl, methods[i]);
                    }

                }
                returnBytes = cl.toBytecode();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Could not instrument  " + className + ",  exception : " + e.getMessage());
        }
        finally {
            if (cl != null) {
                cl.detach();
            }
        }

        return returnBytes;
    }

    private void addProfilingInformation(CtClass clas, CtMethod mold) throws NotFoundException,
            CannotCompileException {

        if(!isInstumentedMethod(mold.getLongName())){
            return;
        }

        String type = mold.getReturnType().getName();
        String originalMethodName = mold.getName();
        String instrumentMethodName = originalMethodName + "$impl";
        CtMethod newMethod = CtNewMethod.copy(mold, originalMethodName, clas, null);
        mold.setName(instrumentMethodName);
        clas.addMethod(newMethod);



        String codeBody = getInstrumentedMethodBody(originalMethodName, instrumentMethodName, type);
        newMethod.setBody(codeBody);
    }

    private boolean isInstumentedMethod(String longMethodName) {
        System.out.println("Profiled Long Name: " + longMethodName);

        return longMethodName.equalsIgnoreCase("org.mockito.Mockito.doCallRealMethod()")
                || longMethodName.equalsIgnoreCase("org.mockito.Mockito.spy(java.lang.Object)");
    }

    private String getInstrumentedMethodBody(String originalMethodName, String instrumentMethodName, String type) {
        StringBuffer body = new StringBuffer();
        body.append("{\n");
        body.append("try {\n");
        body.append("dominion.agent.EventHub.getInstance().restrictedMethodCalled(\"" + originalMethodName + "\");\n");

        if (!"void".equals(type)) {
            body.append(type + " result = ");
        }

        body.append(instrumentMethodName + "($$);\n");

        if (!"void".equals(type)) {
            body.append("return result;\n");
        }

        body.append(
                "}\n" +
                "\tcatch(java.lang.Throwable e){\n" +
                "\te.printStackTrace();\n" +
                "\tthrow new java.lang.RuntimeException(e);\n" +
                "}\n");
        body.append("finally {}\n");
        body.append("}");
        return body.toString();
    }

}
