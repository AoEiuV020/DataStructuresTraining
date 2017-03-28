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
		Multimap<Integer,EndpointPair<N>> map=MultimapBuilder
			.treeKeys()
			.linkedListValues()
			.build();
		Multiset<Integer> keys=map.keys();
		Collection<EndpointPair<N>> values=map.values();
		//已经连上的结点，
		Set<N> nodes=resultGraph.nodes();
		N lastNode=graph.nodes().iterator().next();
		// FIXME: graph不连通的话可能死循环，
		while(nodes.size()<graph.nodes().size())
		{
			Set<N> aNodeSet=graph.adjacentNodes(lastNode);
			// 新结点的所有边添加进map,保存边的权值和一条有向边指向lastNode和边的目的，
			for(N nextNode:aNodeSet){
				if(nodes.contains(nextNode))
					continue;
				Integer edgeValue=graph.edgeValue(lastNode,nextNode);
				if(edgeValue!=null){
					//讲道理，这里adjacentNodes返回的结点，边权并不会为空，
					map.put(edgeValue,EndpointPair.ordered(lastNode,nextNode));
				}
			}
			Iterator<EndpointPair<N>> iterator=values.iterator();
			EndpointPair<N> nextEdge=null;
			Integer nextEdgeWeight=null;
			// 从保存的所有边里取出第一条权值最小的同时目的结点未被新图包含的边，
			// FIXME: graph不连通的话找下一条边可能返回最后一条无效的边，同时nextEdgeWeight为空，
			while(iterator.hasNext()){
				nextEdge=iterator.next();
				if(!nodes.contains(nextEdge.target())){
					nextEdgeWeight=keys.iterator().next();
					iterator.remove();
					break;
				}else{
					iterator.remove();
				}
			}
			resultGraph.putEdgeValue(nextEdge.source(),nextEdge.target(),nextEdgeWeight);
			lastNode=nextEdge.target();
			logger.debug("nextEdge {} = {}",nextEdge,nextEdgeWeight);
		}
		logger.debug("resultGraph {}",resultGraph);
		return resultGraph;
	}
}
