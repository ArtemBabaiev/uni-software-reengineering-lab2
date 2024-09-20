package edu.pro;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainLab21 {

	public static void main(String[] args) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
		long start = System.nanoTime();

		execute();

		long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
		long finish = System.nanoTime();

		System.out.println("------");
		System.out.println("Memory increased: " + (usedMemoryAfter - usedMemoryBefore) / 1_048_576.0 + "MB");
		System.out.println("Execution Time: " + TimeUnit.NANOSECONDS.toMillis(finish - start) + "ms");

	}

	private static void execute() throws IOException {
		InputStream resourceStream = MainLab21.class.getClassLoader().getResourceAsStream("edu/pro/txt/harry.txt");
		String fileContent = new String(resourceStream.readAllBytes());
		resourceStream.close();
		String cleanedFileContent = fileContent.replaceAll("[^\\w\\s]", " ").toLowerCase(Locale.ROOT);
		String[] words = cleanedFileContent.split("\\s+");

		List<String> distinctWords = new ArrayList<>();

		for (String word : words) {
			if (!word.isBlank() && !distinctWords.contains(word)) {
				distinctWords.add(word);
			}
		}

		List<Word> wordCounts = new ArrayList<>();

		for (String distinctWord : distinctWords) {
			int count = 0;
			for (String word : words) {
				if (distinctWord.equals(word)) {
					count++;
				}
			}
			wordCounts.add(new Word(distinctWord, count));
		}

		wordCounts.sort((o1, o2) -> Integer.compare(o2.getCount(), o1.getCount()));

		for (int i = 0; i < 30; i++) {
			Word distinctWord = wordCounts.get(i);
			System.out.println(distinctWord.getText() + " " + distinctWord.getCount());
		}
	}
}
