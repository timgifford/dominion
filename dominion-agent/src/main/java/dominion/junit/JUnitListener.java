package dominion.junit;

import dominion.agent.DominionEventListener;

public class JUnitListener extends DominionEventListener {
    private String callingMethod;
    private String restrictedMethod;

    @Override
    public void onRestrictedMethod(String restrictedMethodName, String caller) {
        this.callingMethod = caller;
        this.restrictedMethod = restrictedMethodName;
    }

    public String getCallingMethod() {
        return callingMethod;
    }

    public String getRestrictedMethod() {
        return restrictedMethod;
    }
}
