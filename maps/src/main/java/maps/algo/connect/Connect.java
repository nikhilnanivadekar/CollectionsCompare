package maps.algo.connect;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

import maps.entity.Connection;
import maps.entity.Node;

public interface Connect extends Comparator<Connection> {

	Map<Node, Collection<Connection>> connect(Collection<Node> from, Collection<Node> to);
}
