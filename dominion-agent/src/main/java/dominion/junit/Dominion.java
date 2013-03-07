package dominion.junit;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class Dominion extends BlockJUnit4ClassRunner {
    public Dominion(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    public void run(RunNotifier notifier) {
//        notifier.addFirstListener(new JUnitRunListener());
        notifier.addListener(new JUnitRunListener());
        super.run(notifier);
    }

}
