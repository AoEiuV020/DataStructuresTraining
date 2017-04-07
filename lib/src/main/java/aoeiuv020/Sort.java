package aoeiuv020;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by AoEiuV020 on 2017/04/06.
 * 排序算法类，
 */
public class Sort {
    private static final Logger logger = LoggerFactory.getLogger(Sort.class);

    public static <E extends Comparable> List<E> quickSort(List<E> unsortedList) {
        logger.debug("unsorted list: {}", unsortedList);
        List<E> sortedList = new ArrayList<>(unsortedList);
        quickSort(sortedList, 0, sortedList.size());
        return sortedList;
    }

    /**
     * 自己凭感觉写的quick sort算法，
     * 好像符合定义，
     * 因为强行使用List和Iterator导致代码很奇怪，
     */
    @SuppressWarnings("unchecked")
    private static <E extends Comparable> void quickSort(List<E> list, int start, int end) {
        if (start >= end - 1) {
            return;
        }
        //顺序，
        ListIterator<E> asc = list.listIterator(start);
        //倒序，
        ListIterator<E> desc = list.listIterator(end);
        ListIterator<E> pivotIterator = list.listIterator(end);
        E pivot = pivotIterator.previous();
        logger.debug("quick sort <{},{},{}>", start, end, pivot);
        E previous = desc.previous();
        while (asc.nextIndex() <= desc.nextIndex()) {
            E next = asc.next();
            previous = desc.previous();
            while (next.compareTo(pivot) < 0 && asc.nextIndex() <= desc.nextIndex()) {
                logger.trace("{} < {}", next, pivot);
                next = asc.next();
            }
            while (previous.compareTo(pivot) > 0 && asc.nextIndex() <= desc.nextIndex()) {
                logger.trace("{} > {}", previous, pivot);
                previous = desc.previous();
            }
            logger.debug("swap <{},{}>", next, previous);
            asc.set(previous);
            desc.set(next);
            logger.trace("list {}", list);
        }
        int pivotIndex;
        if (previous.compareTo(pivot) < 0) {
            pivotIndex = asc.nextIndex();
            pivotIterator.set(asc.next());
            asc.set(pivot);
        } else {
            pivotIndex = asc.nextIndex() - 1;
            pivotIterator.set(previous);
            desc.set(pivot);
        }
        quickSort(list, start, pivotIndex);
        quickSort(list, pivotIndex + 1, end);
    }
}

