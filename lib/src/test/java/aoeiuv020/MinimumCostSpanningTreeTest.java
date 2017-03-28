package aoeiuv020;

import com.google.common.graph.*;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by AoEiuV020 on 3/22/17.
 * 测试最小代价生成树算法类，
 */
public class MinimumCostSpanningTreeTest {
	// 不可修改的用于测试时作为输入的图，
	private static ImmutableValueGraph<Integer,Integer> valueGraph;
	static {
		MutableValueGraph graph= ValueGraphBuilder
			.undirected()
			.allowsSelfLoops(false)
			.build();
		graph.putEdgeValue(0,1,6);
		graph.putEdgeValue(0,3,5);
		graph.putEdgeValue(0,2,1);
		graph.putEdgeValue(1,2,5);
		graph.putEdgeValue(1,4,3);
		graph.putEdgeValue(2,4,6);
		graph.putEdgeValue(5,4,6);
		graph.putEdgeValue(5,2,4);
		graph.putEdgeValue(3,2,5);
		graph.putEdgeValue(3,5,2);
		valueGraph=ImmutableValueGraph.copyOf(graph);
	}
	@Test
	public void prim() throws Exception {
		ValueGraph<Integer,Integer> resultGraph=MinimumCostSpanningTree.prim(valueGraph);
		assertFalse(Graphs.hasCycle(resultGraph));
		MutableValueGraph currectGraph= ValueGraphBuilder
			.undirected()
			.allowsSelfLoops(false)
			.build();
		currectGraph.putEdgeValue(0,2,1);
		currectGraph.putEdgeValue(5,2,4);
		currectGraph.putEdgeValue(3,5,2);
		currectGraph.putEdgeValue(1,2,5);
		currectGraph.putEdgeValue(1,4,3);
		assertTrue(Graphs.equivalent(resultGraph,currectGraph));
	}
}
