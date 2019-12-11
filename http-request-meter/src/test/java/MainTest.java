import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MainTest {

    Object[][] a = {
            {159L, 4}, {160L, 2}, {161L, 1}, {162L, 3}, {163L, 2}, {164L, 9}, {165L, 3}, {166L, 2}, {167L, 9},
            {168L, 10}, {169L, 6}, {170L, 9}, {171L, 10}, {172L, 10}, {173L, 12}, {174L, 7}, {175L, 6}, {176L, 6},
            {177L, 8}, {178L, 8}, {179L, 9}, {180L, 9}, {181L, 6}, {182L, 4}, {183L, 3}, {185L, 2}, {186L, 2}, {187L, 1},
            {188L, 1}, {189L, 2}, {190L, 1}, {191L, 3}, {192L, 2}, {193L, 2}, {195L, 2}, {196L, 2}, {197L, 4},
            {198L, 1}, {199L, 3}, {200L, 1}, {201L, 1}, {202L, 2}, {203L, 1}, {206L, 3}, {207L, 1}, {208L, 1}, {209L, 1},
            {217L, 1}, {228L, 1}, {372L, 1}
    };

    @Test
    public void testPercentiles() {
        Map<Long, Integer> map = new HashMap<>();
        for (Object[] o : a) {
            long key = (long) o[0];
            int value = (int) o[1];
            Integer put = map.put(key, value);
        }
        Main.printPercentiles(map, 200);
    }

}