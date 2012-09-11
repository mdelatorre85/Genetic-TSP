package com.ttf.tsp.shared;

import java.util.Stack;

public class Generation {

	private static final int TOURNAMENTSIZE = 10;
	public static final int POPULATIONSIZE = 160;

	private Stack<Route> routes;

	private Route bestRoute;
	private double bestFitness;

	private Route worstRoute;
	private double worstFitness;

	private double averageFitness;
	private double totalFitness;

	public Generation(boolean generate) {
		routes = new Stack<>();
		if (generate) {
			for (int i = 0; i < POPULATIONSIZE; i++) {
				routes.push(new Route(true));
			}
		}
	}

	public void evaluateFitness(double[][] distancias) {
		bestFitness = Double.MAX_VALUE;
		worstFitness = -1d;
		for (Route route : routes) {
			route.evaluateFitness(distancias);
			if (route.getFitness() < bestFitness) {
				bestFitness = route.getFitness();
				bestRoute = route;
			}
			if (route.getFitness() > worstFitness) {
				worstFitness = route.getFitness();
				worstRoute = route;
			}
			totalFitness += route.getFitness();
		}
		averageFitness = totalFitness / routes.size();
	}

	public double getBestFitness() {
		return bestFitness;
	}

	public Route getBestRoute() {
		return bestRoute;
	}

	public double getAverageFitness() {
		return averageFitness;
	}

	public double getTotalFitness() {
		return totalFitness;
	}

	public Stack<Route> getRoutes() {
		return routes;
	}

	public double getWorstFitness() {
		return worstFitness;
	}

	public Route getWorstRoute() {
		return worstRoute;
	}

	public Route getTournamentGeneration() {

		Route tournamentWinner = routes.get((int) Math.floor(Random.getRandom()
				* POPULATIONSIZE)), tournamentParticipant;
		for (int i = 0; i < TOURNAMENTSIZE; i++) {
			tournamentParticipant = routes.get((int) Math.floor(Random
					.getRandom() * POPULATIONSIZE));
			if (tournamentParticipant.getFitness() < tournamentWinner
					.getFitness()) {
				tournamentWinner = tournamentParticipant;
			}
		}

		return tournamentWinner;
	}
}
