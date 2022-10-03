package cc.piner.accountbook;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cc.piner.accountbook.utils.DateUtil;

import static org.junit.Assert.assertEquals;

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

    @Test
    public void mapTest() {
        List<String> strs = new ArrayList<>();
        List<String> result = new ArrayList<>();
        strs.add("早餐");strs.add("晚餐");strs.add("午饭");
        strs.add("水果");strs.add("早餐");strs.add("早餐");
        strs.add("早餐");strs.add("午饭");strs.add("晚餐");
        strs.add("午餐");strs.add("午饭");strs.add("早餐");
        strs.add("水果");strs.add("饭卡");strs.add("饭卡");

        // 早餐：5
        // 午餐 1 午饭3
        // wancan 2
        // 饭卡 2 水果2

        HashMap<String, Integer> map = new HashMap<>();
        for (String str : strs) {
            map.put(str, map.containsKey(str) ? map.get(str)+1 : 1);
        }
        for (int i = 0; i < 3; i++) {
            int max = 0;
            String maxStr = null;
            for (String s : map.keySet()) {
                if (map.get(s) > max) {
                    maxStr = s;
                    max = map.get(s);
                }
            }
            if (maxStr != null) {
                map.remove(maxStr);
            }
            result.add(maxStr);
        }

        System.out.println(result);

    }

    @Test
    public void dateTest() {
        System.out.println(DateUtil.getMonthRemainDays());
        System.out.println(DateUtil.getWeekRemainDays());
    }
}