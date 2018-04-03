package com.cit.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class TopPhrases {

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String pathToFile="";
		String fileName="";
		try {
			System.out.print("Enter the path to file to work please:");
			pathToFile = br.readLine();
			System.out.print("Now the file name please:");
			fileName = br.readLine();
			if(!pathToFile.equals("") && !fileName.equals("")){
				List<Entry<String,Integer>> occurrences = findListFrequentPhrases(pathToFile,fileName);
				if(!occurrences.isEmpty()){
					System.out.println("The following results were found :");
					for( Map.Entry<String, Integer> entry : occurrences){
						System.out.println("'"+entry.getKey() +"' where found "+String.valueOf(entry.getValue())+" Times.");
					}
					System.out.println("Thanks for use our system!. :)");
				}
			} else {
				System.out.print("Sorry you need to input the file name and file path. Restart and try again.");
			}
		} catch (IOException e) {
			System.out.print("Sorry unfortunately we got an error. Restart and try again.");
		}
	}
	/**
	 * Complexity:
 	 * Building the dictionaries is O(N), where N is the total number of individual words in the file.
 	 * Sorting each temporary dictionary is O(k log k), where 'k' is the number of words in the dictionary.
 	 * Writing each temporary dictionary is O(k)
 	 * The merge is O(M log x), where M is the combined number of entries across all the temporary files, and x is the number of temporary files.
 	 * Selecting the items is O(m log n), where m is the number of unique words, and n is the number of words you want to select.
	 * @param folderPath
	 * @param fileName
	 * @return
	 */
	public static List<Map.Entry<String,Integer>> findListFrequentPhrases(String folderPath, String fileName) {
        Map<String, Integer> occurrences = new HashMap<>();

        Path path = Paths.get(folderPath, fileName);
        try (BufferedReader br = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] phrases = line.split("\\|");
                for (String phrase: phrases) {
                    if (!occurrences.containsKey(phrase)) {
                        occurrences.put(phrase, 1);
                    } else {
                    		occurrences.put(phrase, occurrences.get(phrase) + 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

        List<Map.Entry<String, Integer>> hashMapEntries = new ArrayList<>(occurrences.entrySet());
        Collections.sort(hashMapEntries, (e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()));
        return hashMapEntries
                .stream()
                .limit(100000).collect(Collectors.toList());
    }
	
}
