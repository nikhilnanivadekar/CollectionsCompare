package maps.algo.connect;

import maps.entity.Connection;
import maps.entity.Node;

public abstract class DistanceConnect implements Connect {

	private static final double earthRadius = 6371; // kilometers
	protected double maxDistance;
	
	public DistanceConnect(double maxDistance) {
		this.maxDistance = maxDistance;
	}

	protected boolean shouldConnect(Node from, Node to) {
		return Double.compare(calculateDistance(to, from), maxDistance) <= 0;
	}
	
	protected Connection connect(Node from, Node to) {
		Connection connection = new Connection();
		connection.setTo(to);
		connection.setFrom(from);
		connection.setDistance(calculateDistance(to, from));
		return connection;
	}
	
	protected double calculateDistance(Node n, Node m) {
		return distFrom(n.getLatitude(), n.getLongitude(), m.getLatitude(), m.getLongitude());
	}

	private double distFrom(double lat1, double lng1, double lat2, double lng2) {
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		float dist = (float) (earthRadius * c);

		return dist;
	}
	
	@Override
	public int compare(Connection o1, Connection o2) {
		return Double.compare(o1.getDistance(), o2.getDistance());
	}

}
