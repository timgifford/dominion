package dominion.agent;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

public class RestrictionTest {

    @Test
    public void failWithThreePartialMockCalls() {
        DominionEventListener eventListener = EventHub.getInstance().getEventListener();
        eventListener.reset();

        PartialMock partialMock = mock(PartialMock.class);

        doCallRealMethod().when(partialMock).mockedMethod();
        doCallRealMethod().when(partialMock).mockedMethod();
        doCallRealMethod().when(partialMock).mockedMethod();

        assertThat(eventListener.getCount(), equalTo(3));
    }
}
