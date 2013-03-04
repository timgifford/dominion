package dominion.agent;

public class EventHub {
    private DominionEventListener eventListener = new DominionEventListener();

    private static EventHub instance = new EventHub();
    private StackTraceElement[] stackTrace;

    public static EventHub getInstance() {
        return instance;
    }

    public void restrictedMethodCalled(String restrictedMethodName) {
        String calledFrom = getInvokingMethodFromStackTrace(restrictedMethodName);
//        System.out.printf("[Restricted] %s called from %s\n", restrictedMethodName, calledFrom);
        if (eventListener != null) {
            eventListener.onRestrictedMethod(restrictedMethodName, calledFrom);
        }
    }

    private String getInvokingMethodFromStackTrace(String restrictedMethodName) {
        StackTraceElement[] stackTraceElements = getStackTrace();
        for (int i = 0; i < stackTraceElements.length; i++) {
            String methodName = stackTraceElements[i].getMethodName();
            if (methodName.equalsIgnoreCase(restrictedMethodName)) {
                return stackTraceElements[++i].getMethodName();
            }
        }
        return null;
    }

    public void addListener(DominionEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public StackTraceElement[] getStackTrace() {
        if (stackTrace == null)
            return Thread.currentThread().getStackTrace();
        return stackTrace;
    }

    public void setStackTrace(StackTraceElement[] stackTrace) {
        this.stackTrace = stackTrace;
    }

    public DominionEventListener getEventListener() {
        return eventListener;
    }
}
