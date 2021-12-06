import hello.Greeter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GreeterTest {
    @Test
    public void GivenAnyThing_WhenCallSayHello_ReturnHelloWorld(){
        Greeter greeter = new Greeter();
        assertEquals(greeter.sayHello(), "Hello world!");
    }
}
