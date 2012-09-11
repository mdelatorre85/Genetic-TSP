package com.ttf.tsp.client;

import java.util.Stack;

import com.ttf.tsp.shared.CSVUtil;
import com.ttf.tsp.shared.City;
import com.ttf.tsp.shared.Generation;
import com.ttf.tsp.shared.Route;

public class Main {

	public static final int NUMBEROFCITIES = 32;
	private static final double MAXDISTANCE = 100d;
	public static final int MAXGENERATIONS = 300;

	public static void main(String[] args) {

		// Se genera el ambiente, es decir las ciudades
		Stack<City> cities = new Stack<City>();
		for (int i = 0; i < NUMBEROFCITIES; i++) {
			cities.push(new City(MAXDISTANCE));
		}
		// for (int i = 0; i < NUMBEROFCITIES; i++) {
		// cities.push(new City(100, i * 20));
		// }

		double[][] distancias = new double[NUMBEROFCITIES][NUMBEROFCITIES];
		for (int i = 0; i < NUMBEROFCITIES; i++) {
			for (int j = 0; j < NUMBEROFCITIES; j++) {
				distancias[i][j] = cities.get(i).euclideanDistance(
						cities.get(j));
			}
		}
		// Se genera el ambiente, es decir las ciudades
		Stack<Generation> generations = new Stack<Generation>();
		generations.push(new Generation(true));
		generations.peek().evaluateFitness(distancias);

		CSVUtil csvUtil = new CSVUtil("results.CSV");
		csvUtil.addGeneration(generations.peek());

		int currentGeneration = 0;
		while (currentGeneration < MAXGENERATIONS) {
			currentGeneration++;
			Generation nextGeneration = new Generation(false);
			while (nextGeneration.getRoutes().size() < Generation.POPULATIONSIZE) {
				Route father = generations.peek().getTournamentGeneration();
				Route mother = generations.peek().getTournamentGeneration();
				Route child = father.cross(mother, currentGeneration);

				child.mutate(currentGeneration);
				nextGeneration.getRoutes().push(child);

			}
			nextGeneration.evaluateFitness(distancias);
			generations.push(nextGeneration);
			csvUtil.addGeneration(generations.peek());
		}
		csvUtil.closeFile();
		System.out.println("Ta·dah!");
	}
}
