package edu.cmu.lti.f13.hw4.hw4_137175.casconsumers;


//iterations
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;


//read file
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


//analize text
import java.text.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

//extract features
//a feature is a simple object that contains a name and a value and is implemented by

//import org.cleartk.classifier.Feature;
//import org.uimafit.util.JCasUtil;
//import org.cleartk.classifier.feature.extractor.CleartkExtractor.Context;
//import org.cleartk.classifier.feature.function.FeatureFunctionExtractor;
//import org.cleartk.classifier.feature.function.LowerCaseFeatureFunction;
//import org.cleartk.type.test.Token;



public class Bag_Words<Item> implements Iterable<Item> {
    
	private static final String StdIn = "";
	private int N;               // elements in bag
    private Node<Item> first;    // beginning of bag
	//private static Object stdOut;

	
    public static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    
     //Empty bag.
    public Bag_Words() {
        first = null;
        N = 0;
    }

    
     // Check what is in the bag?
     // true if this bag is empty; false otherwise
    public boolean isEmpty() {
        return first == null;
    }

    //The number of items in the bag
    public int size(Object stdOut) {
        return N;
    }

    
    // Adds the item to this bag
    public boolean add(String st) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = (Item) st;
        first.next = oldfirst;
        N++;
		return false;
    }    

    
     // Returns an iterator that iterates over the items in the bag
    public Iterator<Item> iterator()  {
        return new ListIterator<Item>(first);  
    }

    // Iterator
	private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }

  
      
    static public String getContents(File aFile) {
        //checks on aFile
        StringBuilder contents = new StringBuilder();
        
        try {
          //reading one line at a time
        	/*
             * readLine cases:
             * it returns the content of a line MINUS the newline.
             * it returns null only for the END of the stream.
             * it returns an empty String if two newlines appear in a row.
             */
          BufferedReader input =  new BufferedReader(new FileReader(aFile));
          try {
            String line = null; 
            
            while (( line = input.readLine()) != null){
              contents.append(line);
              contents.append(System.getProperty("line.separator"));
            }
          }
          finally {
            input.close();
          }
        }
        catch (IOException ex){
          ex.printStackTrace();
        }
        
        return contents.toString();
      }
    

    /*Main*/
    @SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
    	
    	try{
    	
    	File testFile = new File("C:/Users/Tania/hw4-137175/src/main/resources/data/documents.txt");
        
    	FileReader reader = new FileReader(testFile);
    	
        //bag
    	ArrayList<String> bag = new ArrayList<String>(); 
    	
    	BufferedReader buffReader;
		
    	buffReader = new BufferedReader(reader);
    	    	    	
    	String st; //is a line, string 
    	
    	String delimiter = ",";
		
    	System.out.println("***Showing information text collection input in ArrayList***");
    	System.out.println("");
    	
    	
    	while((st = buffReader.readLine()) != null){
    	    
    		//split
    		st.split(delimiter); //split by delimiters
    	    
    		bag.add(st);  //add to array
    		 		
    	}
    	
    	/******methods********/
        bag.iterator();
        bag.isEmpty();
        bag.size();
    
        testFile.exists();
        testFile.length();
        testFile.canRead();
        testFile.lastModified();
    	
    	/*********************/
    	System.out.println( "\n"+ bag );
    	System.out.println( "\n");
    	System.out.println("Number of sentences :"+ bag.size()); 
    	
    	System.out.println( "\n");
     	System.out.println("Test Sentence-Array Possition:\n"+ bag.get(10));
     	
     	System.out.println( "\n");
     	System.out.println("File length:"+ testFile.length());
  	
    	
    	}
    	catch (Exception e)
    	{
    		System.out.println("If a TrOuBLE appears!!");
            System.err.println("Error: "+ e.getMessage());
            System.exit(0);
    	}
    
    }
}