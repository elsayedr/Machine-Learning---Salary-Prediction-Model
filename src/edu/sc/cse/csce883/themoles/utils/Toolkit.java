package edu.sc.cse.csce883.themoles.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author meng
 * Purpose: To provide some rarely used but necessary functions,
 * 			due to the volume of data we are dealing with.
 */
public class Toolkit {
	
	/*
	 * Purpose: To remove all except vocabularies given the webster dictionary (.txt).
	 * 			One vocabulary per line in the output file.
	 * Format of given file: String of a vocabulary is all in UPPERCASE,
	 * 						different dictation forms are separated by "; ";
	 * 						String of explanations is mixed with UPPERCASE, lowercase.
	 */
	public void pickVocabularies(String sourceDict, String targetDict) {
		try {
			Scanner input = new Scanner(new File(sourceDict));
			FileWriter stream = new FileWriter(targetDict);
			BufferedWriter out = new BufferedWriter(stream);
			String regex = "[A-Z]*[A-Z;]+[A-Z ]+[A-Z']+[A-Z-]+[A-Z.]";
			String regex_neg = "[a-z0-9:*,(_]";
			Pattern pattern = Pattern.compile(regex);
			Pattern pattern_neg = Pattern.compile(regex_neg);
			Matcher matcher, matcher_neg;
			String line;
			
			System.out.println("Picking Vocabularies ...");
			while (input.hasNextLine()) {
				line = input.nextLine();
				matcher = pattern.matcher(line);
				matcher_neg = pattern_neg.matcher(line);
//				if (matcher.find() && !matcher_neg.find()) {
				if (!matcher_neg.find()) {
					out.write(line + "\n");
					out.flush();
				}
			}
			out.close();
			System.out.println("All Picked!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}
	
	/*
	 * Purpose: remove duplicate vocabularies from given word list.
	 * 
	 */
	public void rmDuplicates(String source, String target) {
		System.out.println("Removing duplicates ...");
		try {
			TreeSet dictSet = new TreeSet();
			Scanner input = new Scanner(new File(source));
			FileWriter stream = new FileWriter(target);
			BufferedWriter out = new BufferedWriter(stream);
			int count = 0;
			String word;
			
			while (input.hasNextLine()) {
				count++;
				word = input.nextLine();
				dictSet.add(word);
				if (count != 0 && count % 500 == 0) {
					System.out.println("reading " + count + ": " + word);
				}
			}
			input.close();
			
			Iterator iterator = dictSet.iterator();
			count = 0;
			while (iterator.hasNext()) {
				count++;
				word = (String) iterator.next();
				if (count != 0 && count % 500 == 0) {
					System.out.println("writing " + count + ": " + word);
				}
				out.write(word.toLowerCase() + "\n");
				out.flush();
			}
			out.close();
			System.out.println("Done removing!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static <K, V extends Comparable<V>> TreeMap<String, Integer> 
				sortByValue(final TreeMap<String, Integer> unsorted) {
		Comparator<String> valueComparator = new Comparator<String>() {
			@Override
			public int compare(String key1, String key2) {
				int compare = unsorted.get(key1).compareTo(unsorted.get(key2));
				if (compare == 0) {
					return 1;
				} else {
					return compare;
				}
			}
			
		};
		
		TreeMap<String, Integer> sorted = new TreeMap<String, Integer>(valueComparator);
		sorted.putAll(unsorted);
		
		return sorted;
	}
	
	// for test
	void printTreeMap(TreeMap<String, Integer> toPrint) {
		for (Entry<String, Integer> entry : 
					toPrint.entrySet()) {
		System.out.println(entry.getKey() + "\t" + entry.getValue());
		}
	}
	
	public static void main(String[] args) {
		Toolkit tool = new Toolkit();
		Random random = new Random();
		
		String sourceDict = "./dataset/dictionary/webster_full.dict";
		String vocOnlyDict = "./dataset/dictionary/stopwords_en.txt";
		String noDupDict = "./dataset/dictionary/stopwords_en_nd.txt";
		
//		tool.pickVocabularies(sourceDict, targetDict);		
//		tool.rmDuplicates(vocOnlyDict, noDupDict);
		
		
		String[] words = {"my", "SOFTWARE", "engineer", "yours", "CSCE", 
							"*", "go", "Computer", "compromit"};
		TreeMap<String, Integer> testList = new TreeMap<String, Integer>();
		int value;
		
		
		for (String word : words) {
			value = random.nextInt(30);
			testList.put(word.toLowerCase(), value);
			System.out.println(word + ",\t" + value);
		}
		
		System.out.println("==========unsorted==========");
		tool.printTreeMap(testList);
		System.out.println("==========sorted==========");
		tool.printTreeMap(tool.sortByValue(testList));
	}
	
}
