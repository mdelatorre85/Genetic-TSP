package com.ttf.tsp.shared;

import java.util.Iterator;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

import com.ttf.tsp.client.Main;

public class Route {

	private static final double MAXMUTATIONPROVABILITY = .01;
	private static final double MINMUTATIONPROVABILITY = .001;
	private static final double MAXCROSSPROVABILITY = 1.0;
	private static final double MINCROSSPROVABILITY = .3;

	private int[] route;
	private double fitness = -1;

	public Route(boolean init) {
		route = new int[Main.NUMBEROFCITIES];
		if (init) {
			for (int i = 0; i < Main.NUMBEROFCITIES; i++) {
				route[i] = -666;
			}
			for (int i = 0; i < Main.NUMBEROFCITIES; i++) {
				boolean flag = true;
				while (flag) {
					int index = (int) Math.floor(Random.getRandom()
							* Main.NUMBEROFCITIES);
					if (route[index] == -666) {
						route[index] = i;
						flag = false;
					}
				}
			}
		}

	}

	public int[] getRoute() {
		return route;
	}

	public void setRoute(int[] route) {
		this.route = route;
	}

	public void evaluateFitness(double[][] distancias) {
		fitness = 0;
		for (int i = 0; i < Main.NUMBEROFCITIES - 1; i++) {
			fitness += distancias[route[i]][route[i + 1]];
		}
	}

	public double getFitness() {
		return fitness;
	}

	public boolean isFitnessCalculated() {
		return fitness != -1;
	}

	public Route cross(Route mother, int generation) {
		double probability = MINCROSSPROVABILITY
				+ (MAXCROSSPROVABILITY - MINCROSSPROVABILITY)
				* (generation / Main.MAXGENERATIONS);

		if (Random.getRandom() <= probability) {
			// Creación de la Tabla
			TreeMap<Integer, TreeSet<Integer>> table = new TreeMap<Integer, TreeSet<Integer>>();
			TreeSet<Integer> vecinos;
			for (int i = 0; i < route.length; i++) {
				for (int j = 0; j < route.length; j++) {
					if (route[j] == i) {
						vecinos = table.get(new Integer(i));
						if (vecinos == null) {
							vecinos = new TreeSet<Integer>();
							table.put(new Integer(i), vecinos);
						}
						// a la izquierda
						if (j == 0) {
							vecinos.add(new Integer(route[route.length - 1]));
						} else {
							vecinos.add(new Integer(route[j - 1]));
						}

						// a la derecha
						if (j == route.length - 1) {
							vecinos.add(new Integer(0));
						} else {
							vecinos.add(new Integer(route[j + 1]));
						}
						break;
					}
				}

				for (int j = 0; j < mother.route.length; j++) {
					if (mother.route[j] == i) {
						vecinos = table.get(new Integer(i));
						if (vecinos == null) {
							vecinos = new TreeSet<Integer>();
						}
						// a la izquierda
						if (j == 0) {
							vecinos.add(new Integer(
									mother.route[route.length - 1]));
						} else {
							vecinos.add(new Integer(mother.route[j - 1]));
						}

						// a la derecha
						if (j == mother.route.length - 1) {
							vecinos.add(new Integer(0));
						} else {
							vecinos.add(new Integer(mother.route[j + 1]));
						}
						break;
					}
				}
				// Creación de la Tabla
			}

			// 1. X = the first node from a random parent.
			// 2. While the CHILD chromo isn't full, Loop:
			// - Append X to CHILD
			// - Remove X from Neighbor Lists

			// if X's neighbor list is empty:
			// - Z = random node not already in CHILD
			// else
			// - Determine neighbor of X that has fewest neighbors
			// - If there is a tie, randomly choose 1
			// - Z = chosen node
			// X = Z

			Stack<Integer> rutaRetorno = new Stack<Integer>();

			// 1. X = the first node from a random parent.
			Integer x = (int) Math.floor(Random.getRandom() * route.length), z = 0;
			// 2. While the CHILD chromo isn't full, Loop:
			while (rutaRetorno.size() < route.length) {
				// - Append X to CHILD
				rutaRetorno.push(x);
				if (rutaRetorno.size() == route.length) {
					break;
				}

				// - Remove X from Neighbor Lists
				Iterator<TreeSet<Integer>> i = table.values().iterator();
				while (i.hasNext()) {
					i.next().remove(x);
				}

				TreeSet<Integer> xNeighbors = table.get(x);
				// if X's neighbor list is empty:
				if (xNeighbors.isEmpty()) {
					boolean flag = true;
					// - Z = random node not already in CHILD
					while (flag) {
						z = (int) Math.floor(Random.getRandom() * route.length);
						if (!rutaRetorno.contains(z)) {
							flag = false;
						}
					}
					// else
				} else {
					// - Determine neighbor of X that has fewest neighbors
					int min = Integer.MAX_VALUE;
					for (Integer k : xNeighbors) {
						if (table.get(k).size() < min) {
							min = table.get(k).size();
							z = k;
						}
					}
				}
				x = z;
			}
			Route retorno = new Route(false);
			for (int i = 0; i < retorno.route.length; i++) {
				retorno.route[i] = rutaRetorno.get(i);
			}
			return retorno;
		} else {
			if (Random.getRandom() < .5) {
				return this;
			} else {
				return mother;
			}
		}
	}

	public void mutate(int generation) {
		double probability = MINMUTATIONPROVABILITY
				+ (MAXMUTATIONPROVABILITY - MINMUTATIONPROVABILITY)
				* (generation / Main.MAXGENERATIONS);
		for (int i = 0; i < route.length; i++) {
			if (Random.getRandom() <= probability) {
				int indexFirst = (int) Math.floor(Random.getRandom()
						* route.length);
				int indexSecond = (int) Math.floor(Random.getRandom()
						* route.length);
				int valorFirst = route[indexFirst];
				int valorSecond = route[indexSecond];
				route[indexFirst] = valorSecond;
				route[indexSecond] = valorFirst;
			}
		}
	}

}
