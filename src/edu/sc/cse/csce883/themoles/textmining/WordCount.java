package edu.sc.cse.csce883.themoles.textmining;
//This program prompts the user for the name of a file and then counts the
//occurrences of words in the file (ignoring case).  It then reports the
//frequencies using a cutoff supplied by the user that limits the output
//to just those words with a certain minimum frequency.

import java.util.*;
import java.util.Map.Entry;
import java.io.*;

import edu.sc.cse.csce883.themoles.utils.Constructor;

public class WordCount {
 public static void main(String[] args) throws IOException {
     // open the file
    Scanner console = new Scanner(System.in);
//     System.out.print("What is the name of the text file? ");
    ArrayList<String> listOfKeyWords = new ArrayList<String>();
    String fileName = "./dataset/dictionary/Train_rev1.csv";
    String dictName = "./dataset/dictionary/webster_simple.dict";
    String stoplist = "./dataset/dictionary/stopwords_en.txt";
    String stasticFileName = "./dataset/dictionary/word_stat.txt";
    String originalDataset = "/home/ibrahimwelsayed/Downloads/883project/Train_rev1.csv";
     
    Constructor worker = new Constructor();
 	TreeMap<String, Integer> wordStat = new TreeMap<String, Integer>();
    System.out.println("Working ...");
    Scanner input = new Scanner(new File(fileName));

    // count occurrences
    TreeMap<String, Integer> wordCounts = new TreeMap<String, Integer>();
    while (input.hasNext()) {
        String next = input.next().toLowerCase();
        if (!wordCounts.containsKey(next)) {
            wordCounts.put(next, 1);
        } else {
            wordCounts.put(next, wordCounts.get(next) + 1);
        }
    }
    
	System.out.println("Total words = " + wordCounts.size());
	 
	// remove non-words from wordCount list
	wordCounts = worker.clean(wordCounts, dictName);
	 
	// remove stop words from wordCount list
	wordCounts = worker.filter(wordCounts, stoplist);
	 
	System.out.println("Minimum number of occurrences for printing? ");
	int min = console.nextInt();
	
	for (Entry<String, Integer> entry : 
					wordCounts.entrySet()) {
		if (entry.getValue() >= min) {
			wordStat.put(entry.getKey(), entry.getValue());
			listOfKeyWords.add(entry.getKey());

		}
	}
     
	// following block doesn't work after TreeMap has been sorted.
//     for (String word : wordCounts.keySet()) {
//         int count = wordCounts.get(word);
//         if (count >= min)
////             System.out.println(count + "\t" + word);
//        	 wordStat.put(word, count);
//     }
     
    // comment by Ying Meng
    // sort by values
    System.out.println("\tSorting ...");
    wordStat = sortByValues(wordStat);
    System.out.println("\tDone sorting!");	
		
    // write wordStat into a text file
    worker.parse2Txt(wordStat, stasticFileName);
    System.out.println("listOfKeyWords: " + listOfKeyWords.size());
    System.out.println(listOfKeyWords.get(listOfKeyWords.size()-1));
    worker.removeUnwantedRecords(originalDataset, listOfKeyWords);
 }
 
// comment by Ying Meng
static <String, Integer extends Comparable<Integer>> TreeMap<String, Integer> sortByValues(final Map<String, Integer> map) {
	    Comparator<String> valueComparator =  new Comparator<String>() {
	        public int compare(String key1, String key2) {
	            int compare = map.get(key2).compareTo(map.get(key1));
	            if (compare == 0) return 1;
	            else return compare;
	        }
	    };
	    TreeMap<String, Integer> sortedByValues = new TreeMap<String, Integer>(valueComparator);
	    sortedByValues.putAll(map);
	    return sortedByValues;
	}
}