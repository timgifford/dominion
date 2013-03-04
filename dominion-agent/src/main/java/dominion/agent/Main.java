package dominion.agent;

import java.lang.instrument.Instrumentation;

public class Main {

    public static void premain(String agentArguments, Instrumentation instrumentation) {
        instrumentation.addTransformer(new DominionClassFileTransformer());
    }

}
