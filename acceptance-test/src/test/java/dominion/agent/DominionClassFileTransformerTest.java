package dominion.agent;

import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

public class DominionClassFileTransformerTest {

    @Test
    public void callsEventHubWhenJavaAgentIsSetAsJvmProperty() {
        PartialMock partialMock = mock(PartialMock.class);

        doCallRealMethod().when(partialMock).mockedMethod();
        partialMock.mockedMethod();

        DominionEventListener eventListener = EventHub.getInstance().getEventListener();
        Assert.assertEquals("callsEventHubWhenJavaAgentIsSetAsJvmProperty", eventListener.getCallingMethod());
        Assert.assertEquals("doCallRealMethod", eventListener.getRestrictedMethod());
    }
}
