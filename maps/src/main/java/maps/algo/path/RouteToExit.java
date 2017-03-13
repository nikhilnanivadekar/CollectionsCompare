package maps.algo.path;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeSet;

import maps.algo.connect.Connect;
import maps.entity.Connection;
import maps.entity.Node;

/**
 * 
 * @author llima
 *
 */
public class RouteToExit {

	/**
	 * Routes, recursively, nodes to an exit point. It currently has unlimited hops, and stops only when no more nodes can be connected.
	 * @param nodes
	 * @param exitPoints
	 * @return a map of the node and its next hop towards the exit. If there's no exit path, the node related connection will be null
	 */
	public Map<Node, Connection> findRouteToExit(Collection<Node> nodes, Collection<Node> exitPoints, Connect connector) {
		Map<Node, Connection> map = new HashMap<>();
		
		//first, we iterate over the exit points.
		Map<Node, Collection<Connection>> groupedConnections = connector.connect(nodes, exitPoints);
		
		if (groupedConnections.size() > 0) {
			//choose the preferred connection based on the comparator on Connect
			groupedConnections.forEach( (node, nodeConnections) -> {
				TreeSet<Connection> exits = new TreeSet<>(nodeConnections);
				map.put(node, exits.first());
			});
			
			//iterate the unconnected nodes with the connected nodes
			Collection<Node> unconnectedNodes = new HashSet<>(nodes);
			unconnectedNodes.removeAll(groupedConnections.keySet());
			map.putAll(findRouteToExit(unconnectedNodes, groupedConnections.keySet(), connector)); 
		}
		
		return map;
	}
}
