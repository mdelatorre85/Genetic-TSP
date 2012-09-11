package com.ttf.tsp.shared;

import java.io.FileWriter;
import java.io.IOException;

public class CSVUtil {

	private FileWriter writer;
	private int generationNumber = -1;

	/**
	 * Crea un archivo de texto - CSV
	 * 
	 * @param fileName
	 *            El nombre del archivo con extensión
	 */
	public CSVUtil(String fileName) {
		try {
			writer = new FileWriter(fileName);
			writer.append("Generacion,Fitness Promedio,Fitness Best ,Fitness Worst\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addGeneration(Generation generation) {
		generationNumber++;
		try {
			writer.append(String.valueOf(generationNumber));
			writer.append(",");
			writer.append(String.valueOf(generation.getAverageFitness()));
			writer.append(",");
			writer.append(String.valueOf(generation.getBestFitness()));
			writer.append(",");
			writer.append(String.valueOf(generation.getWorstFitness()));
			writer.append("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeFile() {
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
