package edu.cmu.lti.f13.hw4.hw4_137175.annotators;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
//import org.tartarus.snowball.ext.englishStemmer;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.tcas.Annotation;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Scanner;

import edu.cmu.lti.f13.hw4.hw4_137175.VectorSpaceRetrieval;
import edu.cmu.lti.f13.hw4.hw4_137175.typesystems.Document;
import edu.cmu.lti.f13.hw4.hw4_137175.typesystems.Token;
import edu.cmu.lti.f13.hw4.hw4_137175.utils.Utils;

 
public class DocumentVectorAnnotator extends JCasAnnotator_ImplBase {
	
	public static HashSet<String> stopwords;
	
	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {
		FSIterator<Annotation> iter = jcas.getAnnotationIndex(Document.type).iterator();
		while (iter.hasNext()) {
			Document doc = (Document) iter.next();
			FSList tokenList = createTermFreqVector(jcas, doc);
			doc.setTokenList(tokenList);
			//print the document
			printDoc(doc);
		}
	}
	
	/**
	 * 
	 * @param jcas
	 * @param doc
	 */
	private FSList createTermFreqVector(JCas jcas, Document doc) {
		
		//construct a vector of tokens and update the tokenList in CAS
	    //preprocess: lower, trimming, stopwords and stemming
		
		//whitespaces
		String stripWs = doc.getText().trim().replace("[ ]+", " ");
		
		//lowercase
		String lower = stripWs.toLowerCase();
		
		//removing stopWords
		String[] words = lower.split(" ");
		ArrayList<String> wordList = new ArrayList<String>();
		
		for(String s: words) {
			if(!stopwords.contains(s))
				wordList.add(s);
		}
		//making the stemm
		for(int i=0; i<wordList.size(); i++)
			wordList.set(i, stem(wordList.get(i)));
		
		//counting tokens
		HashMap<String, Integer> tokens = new HashMap<String, Integer>();
		for(String s: wordList )
			if(tokens.containsKey(s))
				tokens.put(s, tokens.get(s) + 1);
			else
				tokens.put(s, 1);		
		//transforming  the tokenList
		ArrayList<Token> sal = new ArrayList<Token>();
		Token tok = null;
		for(Entry<String, Integer> e: tokens.entrySet()) {
			tok = new Token(jcas);
			tok.setText(e.getKey());
			tok.setFrequency(e.getValue());
			sal.add(tok);
		}
		//append
		return Utils.fromCollectionToFSList(jcas, sal);
	}
	
	private String stem(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public static HashSet<String> loadStopwords() {
		HashSet<String> res = new HashSet<String>();
		try {
			
			System.out.println(System.getProperty("user.dir"));
			
			URL url = VectorSpaceRetrieval.class.getResource("/data/stopwords.txt");	         	 
			
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String line = null;
			while((line = br.readLine()) != null)
				if(!line.startsWith("#"))
					res.add(line);
			br.close();
			
		} catch(Exception ex) {
			System.err.println("Issue reading stopwords.txt: " +ex);
			System.exit(1);
			return null;
		}
		
		return res;
	}
	
	
	private void printDoc(Document doc) {
	
		System.out.println("Document Text: " +doc.getText());
		StringBuffer sb = new StringBuffer();
		doc.getTokenList().prettyPrint(0, 1, sb, true);
		System.out.println(sb.toString());
	}
	
	private String stemString(String s) {
		englishStemmer stemmer = new englishStemmer();
		stemmer.setCurrent(s);
		stemmer.toString();
		return stemmer.getCurrent();
	}
}
