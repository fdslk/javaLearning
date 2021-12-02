/**
 * This module is about impact of the final keyword on performance
 * <p>
 * This module explores  if there are any performance benefits from
 * using the final keyword in our code. This module examines the performance
 * implications of using final on a variable, method, and class level.
 * </p>
 *
 * @since 1.0
 * @author zqf
 * @version 1.1
 */
public final class Operation {
    private Operation() {

    }
    /**
     * @param a is the param
     * @param b is the param
     * @return a + b
     */
    public static int add(final int a, final int b) {
        return a + b;
    }
}
