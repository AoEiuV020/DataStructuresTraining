package aoeiuv020;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * 选择算法测试类，
 * Created by AoEiuV020 on 2017/04/22.
 */
public class SelectTest {
    private static final List<Integer> unsorted;
    private static final List<Integer> sorted;

    static {
        int count = 47;
        List<Integer> list = new ArrayList<>(count);
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < count; i++) {
            list.add(random.nextInt() % 100);
        }
        unsorted = Collections.unmodifiableList(list);
        list = new ArrayList<>(unsorted);
        list.sort(Integer::compare);
        sorted = Collections.unmodifiableList(list);
    }
    @Test
    public void select() throws Exception {
        assertEquals(sorted.get(0), Select.select(unsorted,0));
        assertEquals(sorted.get(sorted.size()/2), Select.select(unsorted,unsorted.size()/2));
        assertEquals(sorted.get(sorted.size()-1), Select.select(unsorted,unsorted.size()-1));
    }
}

