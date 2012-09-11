package com.ttf.tsp.shared;

public class City {

	public double lat;
	public double lon;

	public City(double maxDistance) {
		lat = Random.getRandom() * maxDistance;
		lon = Random.getRandom() * maxDistance;
	}

	public City(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}

	public double euclideanDistance(City city) {
		return Math.sqrt((lat - city.lat) * (lat - city.lat) + (lon - city.lon)
				* (lon - city.lon));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(lat);
		sb.append(",");
		sb.append(lon);
		sb.append(")");
		return sb.toString();
	}

}
