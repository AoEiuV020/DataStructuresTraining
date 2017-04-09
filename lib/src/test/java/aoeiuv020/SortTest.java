package aoeiuv020;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * 排序算法测试类，
 * Created by AoEiuV020 on 2017/04/06.
 */
public class SortTest {
    private static final List<Integer> unsorted;
    private static final List<Integer> sorted;

    static {
        int count = 20;
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
    public void quickSort() throws Exception {
        assertEquals(sorted, Sort.quickSort(unsorted));
    }

    @Test
    public void quickSortWiki() throws Exception {
        assertEquals(sorted, Sort.quickSortWiki(unsorted));
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
        list = new LinkedList<>(sorted);
        listIterator = list.listIterator(2);
        assertEquals(2, listIterator.nextIndex());
        assertEquals(1, listIterator.previousIndex());
        listIterator.add(888);
        assertEquals(3, listIterator.nextIndex());
        assertEquals(2, listIterator.previousIndex());
        assertEquals(new Integer(888), list.get(2));
        listIterator.add(999);
        assertEquals(4, listIterator.nextIndex());
        assertEquals(3, listIterator.previousIndex());
        assertEquals(new Integer(888), list.get(2));
        assertEquals(new Integer(999), list.get(3));
        ListIterator removeIterator = list.listIterator(4);
        assertEquals(list.get(4), removeIterator.next());
        listIterator = list.listIterator(0);
        assertEquals(list.get(0), listIterator.next());
        listIterator.previous();
        listIterator.add(1111);
        try {
            removeIterator.remove();
            fail();
        } catch (ConcurrentModificationException ignored) {
        }
        assertEquals(list.get(1), listIterator.next());
        listIterator = list.listIterator();
        assertFalse(listIterator.hasPrevious());
        listIterator = list.listIterator(8);
        assertEquals(list.get(7), listIterator.previous());
        listIterator.add(1234);
        assertEquals(new Integer(1234), list.get(7));
        assertEquals(new Integer(1234), listIterator.previous());
    }

    @Test
    public void insertionSort() throws Exception {
        assertEquals(sorted, Sort.insertionSort(unsorted));
    }
}

