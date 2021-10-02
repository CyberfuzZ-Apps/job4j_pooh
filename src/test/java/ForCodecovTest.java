import org.junit.Test;

import static org.junit.Assert.*;

public class ForCodecovTest {

    @Test
    public void test() {
        ForCodecov forCodecov = new ForCodecov();
        int a = 2;
        int b = 3;
        int expected = 5;
        assertEquals(expected, forCodecov.calc(a, b));
    }

}