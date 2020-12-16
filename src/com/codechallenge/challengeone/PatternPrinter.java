package com.codechallenge.challengeone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is responsible for displaying the input formated in the required pattern.
 * Pattern will be decided based on the input size. 
 * 
 * @author Santhoshkumar.DS
 *
 */
public class PatternPrinter {

	private static final String INPUT_FILEPATH = "src\\com\\codechallenge\\challengeone\\input.txt";
	private static final int DEFAULT_ROWLIMIT = 9;
	
	public static void main(String args[]) {
		List<String> fileLines = new ArrayList<>();
		
		try (Stream<String> fileStream = Files.lines(Paths.get(INPUT_FILEPATH))) {
			fileLines = fileStream.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (fileLines != null && !fileLines.isEmpty()) {
			int rowLimit = DEFAULT_ROWLIMIT;
			if (fileLines.size() < DEFAULT_ROWLIMIT) {
				int temp = fileLines.size() % 2;
				if (temp == 1) {
					rowLimit = (fileLines.size()/ 2) + 1;
				} else {
					rowLimit = fileLines.size()/ 2;
				}
			}
			printInPattern(fileLines, rowLimit);
		}
	}
	
	/**
	 * Method to display the inputs in the desired pattern. 
	 * 
	 * @param fileInput
	 * @param rowLimit
	 */
	private static void printInPattern(List<String> fileInput, int rowLimit) {
		for (int i = 0; i < rowLimit; i++) {
			Stream.iterate(i, n -> n + rowLimit)
				.limit(fileInput.size())
				.filter(n -> n < fileInput.size())
				.forEach(n -> System.out.format("%-20s", fileInput.get(n)));
			System.out.println();
		}
	}
	
	
	
}
