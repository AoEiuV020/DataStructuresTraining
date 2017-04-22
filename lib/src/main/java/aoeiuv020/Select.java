package aoeiuv020;

import org.slf4j.*;
import java.util.*;

/**
 * 选择算法类，
 * 无序数列中选出第k小的元素，
 * Created by aoeiuv on 17-4-10.
 */
public class Select {
    private static final Logger logger = LoggerFactory.getLogger(Select.class);
    /**
      * 凭感觉写的median of median选择算法，
      */
    public static <E extends Comparable> E select(List<E> list,int k){
        logger.debug("select <{},{}}",list,k);
        ArrayList<E> arrayList=new ArrayList(list);
        int index=select(arrayList,0,list.size(),k,5);
        return arrayList.get(index);
    }
    /**
      * @param right 最后一个的后一个索引，
      */
    static <E extends Comparable> int select(List<E> list,int left,int right,int k,int r){
        //... 4,20,6
        logger.debug("select <{},{},{},{}>",list,left,right,k);
        // 20-4<=6
        if(right-left<=r){
            Sort.insertionSort(list,left,right);
            return left+k;
        }
        // 4<20
        for(int i=left;i<right;i+=r){
            Sort.insertionSort(list,i,Math.min(i+r,right));
            // 19,19+(min{24,20}-19)/2
            swap(list,i,i+(Math.min(i+r,right)-i)/2);
        }
        int j=left+1;
        for(int i=left+r;i<right;i+=r){
            swap(list,i,j++);
        }
        int median=select(list,left,j,(j-left)/2,r);
        median=partition(list,left,right,median);
        int countLeft=median-left;
        logger.debug("median <{},{},{},{},{}}",list,left,countLeft,list.get(median),k);
        if(k<countLeft){
            return select(list,left,median,k,r);
        }else if(k>countLeft){
            return select(list,median,right,k-countLeft,r);
        }else{
            return median;
        }
    }
    static <E> void swap(List<E> list,int a,int b){
        E t=list.get(a);
        list.set(a,list.get(b));
        list.set(b,t);
    }
    /**
      * @param right 最后一个+1,
      */
    static <E extends Comparable> int partition(List<E> list,int start,int end,int m){
        int left = start, right = end - 1;
        swap(list,right,m);
        m=right;
        E mid = list.get(right);
        while (left < right) {
            while (list.get(left).compareTo(mid) <= 0 && left < right)
                left++;
            while (list.get(right).compareTo(mid) >= 0 && left < right)
                right--;
            swap(list, left, right);
        }
        if (list.get(left).compareTo(mid) >= 0) {
            swap(list, left, m);
        } else {
            //下一个只可能是mid，也就是没有大于mid的,不需要再交换，
            left++;
        }
        return left;
    }
}
