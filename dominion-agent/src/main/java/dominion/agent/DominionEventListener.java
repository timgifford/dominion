package dominion.agent;

public class DominionEventListener {
    private int restrictedCalls = 0;
    private String callingMethod;
    private String restrictedMethod;

    public void onRestrictedMethod(String restrictedMethodName, String caller) {
        this.callingMethod = caller;
        this.restrictedMethod = restrictedMethodName;
        this.restrictedCalls++;
    }

    public String getCallingMethod() {
        return callingMethod;
    }

    public String getRestrictedMethod() {
        return restrictedMethod;
    }

    public int getCount() {
        return this.restrictedCalls;
    }

    public void reset(){
        this.restrictedCalls = 0;
        this.callingMethod = "";
        this.restrictedMethod = "";
    }
}
