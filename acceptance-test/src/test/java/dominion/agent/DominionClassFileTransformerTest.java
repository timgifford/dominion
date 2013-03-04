package dominion.agent;

import dominion.junit.JUnitListener;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

public class DominionClassFileTransformerTest {

    @Test
    public void callsEventHubWhenJavaAgentIsSetAsJvmProperty() {
        JUnitListener eventListener = new JUnitListener();

        EventHub.getInstance().addListener(eventListener);
        PartialMock partialMock = mock(PartialMock.class);

        doCallRealMethod().when(partialMock).mockedMethod();
        partialMock.mockedMethod();

        Assert.assertEquals("callsEventHubWhenJavaAgentIsSetAsJvmProperty", eventListener.getCallingMethod());
        Assert.assertEquals("doCallRealMethod", eventListener.getRestrictedMethod());
    }
}
