package cc.piner.accountbook;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test() {
        long l = 100000000;
        int i = (int) l;
        System.out.println(i);
    }

    @Test
    public void formatTest() {
        double[] d = {12.33, 2, 123, 23.4, 233,23};
        StringBuilder str = new StringBuilder();
        for (double v : d) {
            str.append(String.format("%6.2f", v)).append("|\n");
        }
        System.out.println(str);
    }
}