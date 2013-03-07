package dominion.agent;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EventHubTest extends DominionTest{

    private EventHub eventHub;
    private DominionEventListener listener;

    @Before
    public void setUp() throws Exception {
        eventHub = new EventHub();
        listener = mock(DominionEventListener.class);
        eventHub.addListener(listener);
    }

    @Test
    public void isSingleton() {
        EventHub.getInstance();
    }

    @Test
    public void handlesNullEventListener() {
        eventHub.addListener(null);
        eventHub.restrictedMethodCalled("restrictedMethodName");
    }

    @Test
    public void searchStackTraceToFindMethodInvoker() {
        int lineNumber = 0;
        StackTraceElement[] stackTrace = Arrays.asList(
                new StackTraceElement("className", "restrictedMethodName", "fileName", lineNumber),
                new StackTraceElement("com.example.NaughtyTest", "testUsingRestrictedMethod", "fileName", lineNumber)
        ).toArray(new StackTraceElement[]{});

        eventHub.setStackTrace(stackTrace);
        eventHub.restrictedMethodCalled("restrictedMethodName");

        verify(listener).onRestrictedMethod("restrictedMethodName", "testUsingRestrictedMethod");
    }

}
