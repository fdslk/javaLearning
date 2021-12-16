import org.junit.Test;
import static org.junit.Assert.*;

public class OutPutTests {
    @Test
    public void GivenOneAndOne_WhenAdd_ThenReturnTwo(){
        int result = Operation.add(1, 1);
        assertEquals(result, 2);
    }
    @Test
    public void GivenTwoMinusOne_ThenReduce_ThenReturnOne(){
        int result = Operation.reduce(2, 1);
        assertEquals(result, 1);
    }
}
