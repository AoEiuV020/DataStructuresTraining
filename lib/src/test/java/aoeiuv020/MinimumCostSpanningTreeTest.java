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
	// 不可修改的用于测试时作为正确结果的图，同时是最小代价生成树，
	private static ImmutableValueGraph<Integer,Integer> currectGraph;
	static {
		MutableValueGraph graph;
		graph=ValueGraphBuilder
			.undirected()
			.allowsSelfLoops(false)
			.build();

		graph.putEdgeValue(0,2,1);
		graph.putEdgeValue(5,2,4);
		graph.putEdgeValue(3,5,2);
		graph.putEdgeValue(1,2,5);
		graph.putEdgeValue(1,4,3);
		currectGraph=ImmutableValueGraph.copyOf(graph);

		graph.putEdgeValue(0,1,6);
		graph.putEdgeValue(0,3,5);
		graph.putEdgeValue(2,4,6);
		graph.putEdgeValue(5,4,6);
		graph.putEdgeValue(3,2,5);
		valueGraph=ImmutableValueGraph.copyOf(graph);
	}
	@Test
	public void prim() throws Exception {
		ValueGraph<Integer,Integer> resultGraph=MinimumCostSpanningTree.prim(valueGraph);
		assertFalse(Graphs.hasCycle(resultGraph));
		assertTrue(Graphs.equivalent(resultGraph,currectGraph));
	}
	@Test
	public void kruskal() throws Exception {
		ValueGraph<Integer,Integer> resultGraph=MinimumCostSpanningTree.kruskal(valueGraph);
		assertFalse(Graphs.hasCycle(resultGraph));
		assertTrue(Graphs.equivalent(resultGraph,currectGraph));
	}
}
