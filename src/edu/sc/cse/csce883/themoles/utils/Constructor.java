package edu.sc.cse.csce883.themoles.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.Map.Entry;
/**
 * 
 * @author meng
 * Purpose: To provide methods of
 * 			1. building a set of words for given dictionary (.txt)
 * 			2. filtering stop words from given collection (TreeMap<String, Integer>)
 * 			3. writing a list of word statistics (TreeMap<String, Integer>) into a file (.txt)
 */
public class Constructor {
	
	// build a set of words in given dictionary.
	public TreeSet buildWordList(String dictionary) {
		TreeSet wordlist = new TreeSet();
		String newWord;
		
		// building word set
		try {
			Scanner input = new Scanner(new File(dictionary));
			
			System.out.println("Building ...");
			while (input.hasNextLine()) {
				// in the dictionaries, one word per line.
				newWord = input.nextLine().toLowerCase();
				wordlist.add(newWord);
			}
			input.close();
			System.out.println("Done building!");
			System.out.println("There are " + wordlist.size() + " words in " + dictionary);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		return wordlist;
	}
	
	// clean the list.
	// that is, remove non-words from given list.
	// "*", "csce", and the like will be removed.
	public TreeMap<String, Integer> clean(TreeMap<String, Integer> dirtyList, String dictName) {
		TreeMap<String, Integer> cleanList = new TreeMap<String, Integer>();
		TreeSet dictionary = buildWordList(dictName);
		
		for (Entry<String, Integer> entry : 
					dirtyList.entrySet()) {
			if (dictionary.contains(entry.getKey())) {
				cleanList.put(entry.getKey(), entry.getValue());
			}
		}
		
		return cleanList;
	}
	
	// filter the list according to the given stop words list.
	// that is, remove stop words from given list.
	public TreeMap<String, Integer> filter(TreeMap<String, Integer> dirtyList, String stoplist) {
		TreeMap<String, Integer> cleanList = new TreeMap<String, Integer>();
		TreeSet stopwords = buildWordList(stoplist);
		
		for (Entry<String, Integer> entry : 
					dirtyList.entrySet()) {
			if (!stopwords.contains(entry.getKey())) {
				cleanList.put(entry.getKey(), entry.getValue());
			}
		}
		
		return cleanList;
	}
	
	// output keys and corresponding values into a txt file
	public void parse2Txt(TreeMap<String, Integer> toParse, String file) {
		try {
			System.out.println("start writing " + toParse.size());
			FileWriter stream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(stream);
			
			for (Entry<String, Integer> entry : 
					toParse.entrySet()) {
//				System.out.println(entry.getKey() + "\t" + entry.getValue());
				out.write(entry.getValue() + ", " + entry.getKey() + "\n");
				out.flush();
			}
			out.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		System.out.println("done writing");
	}
	
	// for test
	void printTreeMap(TreeMap<String, Integer> toPrint) {
		for (Entry<String, Integer> entry : 
					toPrint.entrySet()) {
		System.out.println(entry.getKey() + "\t" + entry.getValue());
		}
	}
	
	public static void main(String[] args) {		
		String dictionary = "./dataset/dictionary/dictionary.txt";
		String stoplist = "./dataset/dictionary/stopwords_en.txt";
		String wordStat = "./dataset/dictionary/stat_test.txt";
		
		Constructor worker = new Constructor();
		String[] words = {"my", "SOFTWARE", "engineer", "yours", "CSCE", "*", "go", "Computer", "compromit"};
		TreeMap<String, Integer> testList = new TreeMap<String, Integer>();
		
		for (String word : words) {
			testList.put(word.toLowerCase(), 1);
		}
		
		
		// print testlist
//		worker.printTreeMap(testList);
		
		// clean the list
		testList = worker.clean(testList, dictionary);
		// cleaned list
//		worker.printTreeMap(testList);
		
		// filter out stop words
		testList = worker.filter(testList, stoplist);
		
		// write the statics into a .txt
		worker.parse2Txt(testList, wordStat);
		System.out.println("Done!!!");
	}
}
