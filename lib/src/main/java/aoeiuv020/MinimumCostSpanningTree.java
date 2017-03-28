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
		Multiset<Integer> set=map.keys();
		Collection<EndpointPair<N>> values=map.values();
		//已经连上的结点，
		Set<N> nodes=resultGraph.nodes();
		N lastNode=graph.nodes().iterator().next();
		// FIXME: 没判断是否连通，
		for(int i=0;i<8&&nodes.size()<graph.nodes().size();++i)
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
			EndpointPair<N> nextEdge=null;
			Integer nextEdgeWeight;
			nextEdgeWeight=set.iterator().next();
			Iterator<EndpointPair<N>> iterator;
			iterator=values.iterator();
			// 从保存的所有边里取出第一条权值最小的同时目的结点未被新图包含的边，
			while(iterator.hasNext()){
				nextEdge=iterator.next();
				iterator.remove();
				if(!nodes.contains(nextEdge.target())){
					break;
				}
			}
			resultGraph.putEdgeValue(nextEdge.source(),nextEdge.target(),nextEdgeWeight);
			lastNode=nextEdge.target();
			logger.debug("nextEdge {}",nextEdge);
		}
		logger.debug("resultGraph {}",resultGraph);
		return resultGraph;
	}
}
