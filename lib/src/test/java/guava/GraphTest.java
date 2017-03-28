package guava;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by AoEiuV020 on 3/22/17.
 * 测试guava里的graph相关方法，
 * 主要是数据库不熟，
 * 就连“度”这种东西不试一下都不能确定，
 */

public class GraphTest {
    @Test
    public void degree() {
        MutableGraph<Integer> graph = GraphBuilder.directed().allowsSelfLoops(true).build();
        graph.addNode(1);
        graph.addNode(2);
        graph.putEdge(1, 2);
        assertEquals(1, graph.degree(1));
        assertEquals(0, graph.inDegree(1));
        assertEquals(1, graph.outDegree(1));
        graph.putEdge(1, 1);
        assertEquals(3, graph.degree(1));
        assertEquals(1, graph.inDegree(1));
        assertEquals(2, graph.outDegree(1));
        graph.putEdge(1, 1);
        assertEquals(3, graph.degree(1));
        assertEquals(1, graph.inDegree(1));
        assertEquals(2, graph.outDegree(1));
        graph.putEdge(2,1);
        assertEquals(4,graph.degree(1));
        assertEquals(2, graph.inDegree(1));
        assertEquals(2, graph.outDegree(1));
    }
}
