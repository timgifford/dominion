package dominion.agent;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DominionEventListenerTest extends DominionTest {

    private DominionEventListener listener;

    @Before
    public void setUp() throws Exception {
        listener = new DominionEventListener();
    }

    @Test
    public void incrementsCountWhenCalled() {
        listener.onRestrictedMethod("restrictedMethodName", "caller");

        assertThat(listener.getCount(), is(1));
        assertThat(listener.getCallingMethod(), is("caller"));
        assertThat(listener.getRestrictedMethod(), is("restrictedMethodName"));
    }
}
