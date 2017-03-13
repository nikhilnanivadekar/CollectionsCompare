package maps.algo.connect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import maps.entity.Connection;
import maps.entity.Node;

public class DistanceConnectImpl extends DistanceConnect {

	public DistanceConnectImpl(double maxDistance) {
		super(maxDistance);
	}

	@Override
	public Map<Node, Collection<Connection>> connect(Collection<Node> from, Collection<Node> to) {
		Map<Node, Collection<Connection>> c = new HashMap<>();
		for (Node f : from) {
			for (Node t : to) {
				if (shouldConnect(f, t)) {
					Collection<Connection> conns = c.get(f);
					if (conns == null) {
						conns = new ArrayList<>();
						c.put(f, conns);
					}
					conns.add(connect(f, t));
				}
			}
		}
		return c;
	}

}
