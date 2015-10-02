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
 public static void main(String[] args) throws FileNotFoundException {
     // open the file
     Scanner console = new Scanner(System.in);
//     System.out.print("What is the name of the text file? ");
     String fileName = "./dataset/training/train.csv";
     String dictName = "./dataset/dictionary/dictionary.txt";
     String stoplist = "./dataset/dictionary/stopwords_en.txt";
     String stasticFileName = "./dataset/dictionary/word_stastics.txt";
     
     Constructor worker = new Constructor();
     
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

     // get cutoff and report frequencies
//     wordCounts = SortByValue(wordCounts);
     
     // comment by Ying Meng
     // sort by values
//     System.out.println("\tSorting ...");
//     wordCounts = sortByValues(wordCounts);
//     System.out.println("\tDone sorting!");
     
     System.out.println("Total words = " + wordCounts.size());
     
     // remove non-words from wordCount list
     wordCounts = worker.clean(wordCounts, dictName);
     
     // remove stop words from wordCount list
     wordCounts = worker.filter(wordCounts, stoplist);
     
     System.out.println("Minimum number of occurrences for printing? ");
     int min = console.nextInt();
     TreeMap<String, Integer> wordStastics = new TreeMap<String, Integer>();
     for (String word : wordCounts.keySet()) {
         int count = wordCounts.get(word);
         if (count >= min)
//             System.out.println(count + "\t" + word);
        	 wordStastics.put(word, count);
     }
     
     // write wordStastics into a text file
     worker.parse2Txt(wordStastics, stasticFileName);
 }
 
 // comment by Ying Meng
 // new sorting method, it works correctly.
 static <String, Integer extends Comparable<Integer>> Map<String, Integer> sortByValues(final Map<String, Integer> map) {
	    Comparator<String> valueComparator =  new Comparator<String>() {
	        public int compare(String key1, String key2) {
	            int compare = map.get(key2).compareTo(map.get(key1));
	            if (compare == 0) return 1;
	            else return compare;
	        }
	    };
	    Map<String, Integer> sortedByValues = new TreeMap<String, Integer>(valueComparator);
	    sortedByValues.putAll(map);
	    return sortedByValues;
	}
}