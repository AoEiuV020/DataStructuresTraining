package aoeiuv020;

import com.google.common.graph.*;
import com.google.common.collect.*;
import java.util.*;
import org.slf4j.*;

/**
 * Created by AoEiuV020 on 3/22/17.
 * 最小代价生成树算法类，
 */

public class MinimumCostSpanningTree {
	private static Logger logger=LoggerFactory.getLogger(MinimumCostSpanningTree.class);
	/**
	 * 自己凭感觉写的Prim算法，
	 * 符合定义，但未必符合复杂度，
	 *
	 * @param graph 必须是连通无向图
	 * @return 返回没有回路的图，即退化成树
	 */
	public static <N> ValueGraph<N,Integer> prim(ValueGraph<N,Integer> graph) {
		MutableValueGraph<N,Integer> resultGraph=ValueGraphBuilder
			.undirected()
			.allowsSelfLoops(false)
			.build();
		Multimap<Integer,EndpointPair<N>> sortedWeightEdgePair=MultimapBuilder
			.treeKeys()
			.linkedListValues()
			.build();
		Multiset<Integer> weights=sortedWeightEdgePair.keys();
		Collection<EndpointPair<N>> edges=sortedWeightEdgePair.values();
		//已经连上的结点，
		Set<N> nodes=resultGraph.nodes();
		N lastNode=graph.nodes().iterator().next();
		// FIXME: graph不连通的话可能死循环，
		while(nodes.size()<graph.nodes().size())
		{
			Set<N> aNodeSet=graph.adjacentNodes(lastNode);
			// 新结点的所有边添加进sortedWeightEdgePair,保存边的权值和一条有向边指向lastNode和边的目的，
			for(N nextNode:aNodeSet){
				if(nodes.contains(nextNode))
					continue;
				Integer edgeValue=graph.edgeValue(lastNode,nextNode);
				if(edgeValue!=null){
					//讲道理，这里adjacentNodes返回的结点，边权并不会为空，
					sortedWeightEdgePair.put(edgeValue,EndpointPair.ordered(lastNode,nextNode));
				}
			}
			Iterator<EndpointPair<N>> iterator=edges.iterator();
			EndpointPair<N> nextEdge=null;
			Integer nextEdgeWeight=null;
			// 从保存的所有边里取出第一条权值最小的同时目的结点未被新图包含的边，
			// FIXME: graph不连通的话找下一条边可能返回最后一条无效的边，
			while(iterator.hasNext()){
				nextEdge=iterator.next();
				nextEdgeWeight=weights.iterator().next();
				iterator.remove();
				if(!nodes.contains(nextEdge.target())){
					break;
				}
			}
			resultGraph.putEdgeValue(nextEdge.source(),nextEdge.target(),nextEdgeWeight);
			lastNode=nextEdge.target();
			logger.debug("nextEdge {} = {}",nextEdge,nextEdgeWeight);
		}
		logger.debug("resultGraph {}",resultGraph);
		return resultGraph;
	}
	/**
	 * 自己凭感觉写的Kruskal算法，
	 * 符合定义，但未必符合复杂度，
	 *
	 * @param graph 必须是连通无向图
	 * @return 返回没有回路的图，即退化成树
	 */
	public static <N> ValueGraph<N,Integer> kruskal(ValueGraph<N,Integer> graph) {
		logger.debug("input graph {}",graph);
		MutableValueGraph<N,Integer> resultGraph=ValueGraphBuilder
			.undirected()
			.allowsSelfLoops(false)
			.build();
		Multimap<Integer,EndpointPair<N>> sortedWeightEdgePair=MultimapBuilder
			.treeKeys()
			.linkedListValues()
			.build();
		Multiset<Integer> weights=sortedWeightEdgePair.keys();
		Collection<EndpointPair<N>> edges=sortedWeightEdgePair.values();
		// 整理排序，
		for(EndpointPair<N> edge:graph.edges()){
			sortedWeightEdgePair.put(graph.edgeValue(edge.nodeU(),edge.nodeV()),
					EndpointPair.ordered(edge.nodeU(),edge.nodeV()));
		}
		// 从保存的所有边里取出权值最小的边，
		// 先连上再判断是否形成回路，是就移除这条边，
		Iterator<EndpointPair<N>> iterator=edges.iterator();
		while(resultGraph.edges().size()<=graph.nodes().size()-1 && iterator.hasNext()){
			EndpointPair<N> nextEdge=iterator.next();
			Integer nextEdgeWeight=weights.iterator().next();
			iterator.remove();
			resultGraph.putEdgeValue(nextEdge.source(),nextEdge.target(),nextEdgeWeight);
			if(Graphs.hasCycle(resultGraph)){
				resultGraph.removeEdge(nextEdge.source(),nextEdge.target());
			}
			logger.debug("nextEdge {} = {}",nextEdge,nextEdgeWeight);
		}
		logger.debug("resultGraph {}",resultGraph);
		return resultGraph;
	}
}
