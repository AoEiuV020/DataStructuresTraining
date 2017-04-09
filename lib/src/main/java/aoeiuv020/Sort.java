package aoeiuv020;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * 排序算法类，
 * Created by AoEiuV020 on 2017/04/06.
 */
@SuppressWarnings({"unchecked", "WeakerAccess"})
public class Sort {
    private static final Logger logger = LoggerFactory.getLogger(Sort.class);

    /**
     * 自己凭感觉写的quick sort算法，
     * 好像符合定义，
     * 因为强行使用List和Iterator导致代码很奇怪，
     * 本意是用ListIterator减少get方法定位非{@link java.util.RandomAccess}的开销，
     * 然而意义不太大，对于{@link java.util.LinkedList},{@link List#listIterator()}本身也要开销，
     * 时间复杂度O(n),平均那什么次数(n/4),
     * 而且我这里是new一个{@link ArrayList}，不直接用传入的List,
     */
    public static <E extends Comparable> List<E> quickSort(List<E> unsortedList) {
        logger.debug("unsorted list: {}", unsortedList);
        List<E> sortedList = new ArrayList<>(unsortedList);
        quickSort(sortedList, 0, sortedList.size());
        return sortedList;
    }

    /**
     * @param end 最后一个元素的后一个，比如1-3是两个元素，
     */
    private static <E extends Comparable> void quickSort(List<E> list, int start, int end) {
        if (start >= end - 1) {
            return;
        }
        //顺序，
        ListIterator<E> asc = list.listIterator(start);
        E next;
        //倒序，
        ListIterator<E> desc = list.listIterator(end - 1);
        E previous = desc.previous();
        ListIterator<E> pivotIterator = list.listIterator(end);
        E pivot = pivotIterator.previous();
        logger.debug("quick sort <{},{},{}>", start, end, pivot);
        do {
            next = asc.next();
            while (next.compareTo(pivot) <= 0 && asc.nextIndex() - 1 < desc.previousIndex() + 1) {
                logger.trace("{} < {}", next, pivot);
                next = asc.next();
            }
            while (previous.compareTo(pivot) >= 0 && asc.nextIndex() - 1 < desc.previousIndex() + 1) {
                logger.trace("{} > {}", previous, pivot);
                previous = desc.previous();
            }
            logger.trace("swap <{},{}>", next, previous);
            asc.set(previous);
            desc.set(next);
            previous = next;
            logger.debug("list {}", list);
        } while (asc.nextIndex() - 1 < desc.previousIndex() + 1);
        logger.debug("{} compareTo {}", previous, pivot);
        int pivotIndex;
        if (previous.compareTo(pivot) >= 0) {
            pivotIndex = asc.nextIndex() - 1;
            pivotIterator.set(previous);
            desc.set(pivot);
        } else {
            pivotIndex = asc.nextIndex();
        }
        quickSort(list, start, pivotIndex);
        quickSort(list, pivotIndex + 1, end);
    }

    /**
     * 模仿Wiki上的quick sort示例代码，
     * 强行使用List和Comparable,
     * 原本用于数组，对应ArrayList,
     * 否则效率极低，
     */
    public static <E extends Comparable> List<E> quickSortWiki(List<E> unsortedList) {
        logger.debug("unsorted list: {}", unsortedList);
        List<E> sortedList = new ArrayList<>(unsortedList);
        quickSortWiki(sortedList, 0, sortedList.size() - 1);
        return sortedList;
    }

    /**
     * @param end 最后一个元素，比如1-2是两个元素，
     */
    private static <E extends Comparable> void quickSortWiki(List<E> list, int start, int end) {
        if (start >= end)
            return;
        E mid = list.get(end);
        int left = start, right = end - 1;
        logger.debug("quick sort <{},{},{}>", start, end, mid);
        while (left < right) {
            while (list.get(left).compareTo(mid) <= 0 && left < right)
                left++;
            while (list.get(right).compareTo(mid) >= 0 && left < right)
                right--;
            swap(list, left, right);
        }
        logger.debug("{} compareTo {}", list.get(left), list.get(end));
        if (list.get(left).compareTo(list.get(end)) >= 0) {
            swap(list, left, end);
        } else {
            //下一个只可能是mid，也就是没有大于mid的,不需要再交换，
            left++;
        }
        logger.debug("list {}", list);
        quickSortWiki(list, start, left - 1);
        quickSortWiki(list, left + 1, end);
    }

    private static <E> void swap(List<E> list, int from, int to) {
        E temp = list.get(from);
        list.set(from, list.get(to));
        list.set(to, temp);
    }

    /**
     * 自己凭感觉写的插入排序，
     * 应该是符合定义的，
     */
    public static <E extends Comparable> LinkedList<E> insertionSort(List<E> unsortedList) {
        logger.debug("insertionSort list: {}", unsortedList);
        LinkedList<E> sortedList = new LinkedList<>(unsortedList);
        insertionSort(sortedList, 0, sortedList.size());
        return sortedList;
    }

    /**
     * @param end 最后一个元素的后一个，比如1-3是两个元素，
     */
    @SuppressWarnings("SameParameterValue")
    public static <E extends Comparable> void insertionSort(List<E> list, int start, int end) {
        LinkedList<E> sortedList = new LinkedList();
        ListIterator<E> unsortedAsc = list.listIterator(start);
        ListIterator<E> sortedIterator = sortedList.listIterator(start);
        E unsortedNext = null;
        boolean flag;
        while (unsortedAsc.nextIndex() < end) {
            E old = unsortedNext;
            unsortedNext = unsortedAsc.next();
            if (old == null || unsortedNext.compareTo(old) > 0) {
                //后移找插入点，
                flag = true;
                while (sortedIterator.hasNext()) {
                    E sortedNext = sortedIterator.next();
                    if (sortedNext.compareTo(unsortedNext) >= 0) {
                        sortedIterator.previous();
                        sortedIterator.add(unsortedNext);
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    sortedIterator.add(unsortedNext);
                }
            } else {
                //前移找插入点，
                flag = true;
                while (sortedIterator.hasPrevious()) {
                    E sortedPrevious = sortedIterator.previous();
                    if (sortedPrevious.compareTo(unsortedNext) <= 0) {
                        sortedIterator.next();
                        sortedIterator.add(unsortedNext);
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    sortedIterator.add(unsortedNext);
                }
            }
            logger.debug("list {}", sortedList);
        }
        ListIterator<E> from = sortedList.listIterator();
        ListIterator<E> to = list.listIterator(start);
        while (from.hasNext()) {
            to.next();
            to.set(from.next());
        }
    }
}

