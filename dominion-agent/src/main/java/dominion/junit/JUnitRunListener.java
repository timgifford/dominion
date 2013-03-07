package dominion.junit;

import dominion.agent.EventHub;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

public class JUnitRunListener extends RunListener {

    @Override
    public void testRunFinished(Result result) throws Exception {

        super.testRunFinished(result);
        System.out.printf("Number of Restricted Methods: %s\n", EventHub.getInstance().getEventListener().getCount());

    }
}
