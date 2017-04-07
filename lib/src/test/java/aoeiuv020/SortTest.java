package aoeiuv020;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by AoEiuV020 on 2017/04/06.
 * 排序算法测试类，
 */
public class SortTest {
    private static final List<Integer> unsorted = Collections.unmodifiableList(
            Arrays.asList(21, 34, 3, 32, 82, 55, 89, 50, 37, 5, 64, 35, 9, 70)
    );
    private static final List<Integer> sorted;

    static {
        List<Integer> list = new ArrayList<>(unsorted);
        list.sort(Integer::compare);
        sorted = Collections.unmodifiableList(list);
    }

    @Test
    public void quickSort() throws Exception {
        assertEquals(sorted, Sort.quickSort(unsorted));
    }

    @Test
    public void listIterator() throws Exception {
        ListIterator<Integer> listIterator = unsorted.listIterator(unsorted.size());
        int index = unsorted.size();
        while (listIterator.hasPrevious()) {
            Integer i = listIterator.previous();
            --index;
            assertEquals(index, listIterator.previousIndex() + 1);
            assertEquals(unsorted.get(index), i);
        }
        List<Integer> list = new ArrayList<>(unsorted);
        listIterator = list.listIterator(0);
        Integer i = listIterator.next();
        listIterator.set(i + 1);
        assertEquals(list.get(0), new Integer(i + 1));
    }
}

