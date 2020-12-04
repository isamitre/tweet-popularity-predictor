import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HashTable_Creation {
	
	// This function takes in a String, which is intended to be the inserted Tweet. With this inserted Tweet, we go through,
	// and by using both Regular Expression and the replaceAll function, we are parsing out all unnecessary information. 
	// A String with all these factors not needed is returned.
	public static String parseTweet(String tweet){
		// This replaces any and all links in the tweet
		tweet = tweet.replaceAll("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", "");
		
		// Gets rid of any characters in the tweet
		tweet = tweet.replaceAll("[.%#!?\\-]", "");
		
		// Gets rid of any numbers in the tweet
		tweet = tweet.replaceAll("[0-9]", "");
		
		// Gets rid of any user handles that is in the tweet
		tweet = tweet.replaceAll("(?<=^|(?<=[^a-zA-Z0-9-_\\\\.]))@([A-Za-z][A-Za-z0-9_]+)", "");
		
		// Gets rid of any extra spaces throughout the tweet
		tweet = tweet.replaceAll(" +", " ");
		
		// Gets rid any spaces in the beginning of the tweet
		tweet = tweet.trim();
		
		return tweet;
	}
	
	// This function takes in a String, which represents the inputted tweet, and will parse out any stopwords throughout
	// the tweet. Stopwords were defined in the group as non-unique words that appear frequently. A string with all stopwords removed
	// is then returned.
	public static String removeStopWords(String tweet){
		Pattern p = Pattern.compile("\\b(a|about|above|after|again|against|all|am|an|and|any|aren't|arent|are|as|at|be|because|been|"
				+ "before|being|below|between|both|but|by|can't|cannot|could|couldn't|did|didn't|do|does|doesn't|doing|don't|down|"
				+ "during|each|for|from|further|got|had|hadn't|has|hasn't|have|haven't|having|he'd|he'll|he's|he|here|here's|hers|herself|her|"
				+ "him|himself|his|how's|how'd|how|i'd|i'll|i'm|im|i've|ive|i|if|in|into|is|isn't|it's|its|it|itself|let's|me|more|most|mustn't|my|myself|"
				+ "no|nor|not|of|off|on|once|only|or|other|ought|our|ours|ourselves|out|over|own|same|shan't|she'd|she'll|she's|she|should|shouldn't|"
				+ "so|some|such|that's|than|that|the|their|theirs|them|themselves|then|there's|there|these|they'd|they'll|they're|they've|they|this|those|"
				+ "through|to|too|under|until|up|very|was|wasn't|we'd|we'll|we're|we've|we|were|weren't|what's|what|when's|when|where's|where|which|while|"
				+ "who's|who|whom|why's|why|with|won't|would've|wouldve|would|also|many|go|feel|feels|like|yes|no|allow|anybody|aside|used|use|say|ought|"
				+ "wouldn't|you'd|you'll|you're|you've|you|your|yours|yourself|yourselves|might|will|just|oh|always|hey|much|might|former|further|"
				+ "goes|gets|get|despite|just|few|maybe|often|such|sure|see|now|really|though|either|or)\\b\\s?");
		Matcher m = p.matcher(tweet);
		String parsedTweet = m.replaceAll("");
		return parsedTweet;
	}
	
	// Main function. This creates both the Hash Table and the Red Black Tree in the form of a Tree Map,
	// and then reads all the tweets, parses all information not needed, and at the end of the program,
	// besides printing how long the process takes, has a finished table with each unique word, the
	// number of times that word has shown up in a unique tweet, and its polarity score
	public static void main(String[] args) throws IOException{
		
		// Path to the CSV. CHANGE AS NEEDED
		String path = "C:\\Users\\Kian\\Documents\\School\\Code\\training.1600000.processed.noemoticon.csv";
		String line = "";
		
		// BufferedReader that will read in each line of the CSV
		BufferedReader kb = new BufferedReader(new FileReader(path));
		
		boolean useHash = true;
		
		// The creation of both the HashMap and TreeMap. Depending on the boolean value, we may use either or
		HashMap<String, int[]> polarityDictHash = new HashMap<String, int[]>();
		TreeMap<String, int[]> polarityDictRed = new TreeMap<String, int[]>();
		
		if(useHash){
			
			// Starts timing the entire process
			long startTime = System.nanoTime();
			
			// Keeps going until it reads every tweet
			while((line = kb.readLine()) != null){
				
				// These next few lines are taking the format of the CSV file, and making it into a proper string, and extracting both the tweet
				// and the polarity value associated
				line = line.replace(",", " ");
				
				String[] tempSplit = line.split("\"");
				String[] newEntry = {tempSplit[1], tempSplit[11]};
				
				// This assings the tweet to a string, and passes it to both functions above and parse out all the information
				// Afterwards, it splits the string, so each word is an element in a String array
				String newTweet = parseTweet(newEntry[1].toLowerCase());
				newTweet = removeStopWords(newTweet);
				
				String[] tweetSplit = newTweet.split("\\s+");
				
				// This goes through the string array, and will check if the word is in the dictionary already or not. 
				// If it is, then it will simple update the integer array associated as needed. If it's a unique word not added,
				// then we will create an integer array with its respective polarity number, and add it to the dictionary.
				for(int j = 0; j < tweetSplit.length; j++){
					String currentWord = tweetSplit[j];
					
					if(!polarityDictHash.containsKey(currentWord)){
						
						int[] wordVal = {1, Integer.parseInt(newEntry[0])};
						polarityDictHash.put(currentWord, wordVal);
						
					}else{
						
						int[] wordVal = polarityDictHash.get(currentWord);
						wordVal[0] = wordVal[0]+1;
						wordVal[1] = wordVal[1]+Integer.parseInt(newEntry[0]);
						
						polarityDictHash.put(currentWord, wordVal);
						
					}
				}
			}
			
			// This stops the timer, and will calculate how long the code took to run, as well as print how many seconds it took
			long endTime = System.nanoTime();
			System.out.println((endTime-startTime)/1000000000+" seconds");
		}else{
			
			// Starts timing the entire process
			long startTime = System.nanoTime();
			
			// Keeps going until it reads every tweet
			while((line = kb.readLine()) != null){
				
				// These next few lines are taking the format of the CSV file, and making it into a proper string, and extracting both the tweet
				// and the polarity value associated
				line = kb.readLine();
				line = line.replace(",", " ");
				
				String[] tempSplit = line.split("\"");
				String[] newEntry = {tempSplit[1], tempSplit[11]};
				
				// This assings the tweet to a string, and passes it to both functions above and parse out all the information
				// Afterwards, it splits the string, so each word is an element in a String array
				String newTweet = parseTweet(newEntry[1].toLowerCase());
				newTweet = removeStopWords(newTweet);
				
				String[] tweetSplit = newTweet.split("\\s+");
				
				
				// This goes through the string array, and will check if the word is in the dictionary already or not. 
				// If it is, then it will simple update the integer array associated as needed. If it's a unique word not added,
				// then we will create an integer array with its respective polarity number, and add it to the dictionary.
				for(int j = 0; j < tweetSplit.length; j++){
					String currentWord = tweetSplit[j];
					
					if(!polarityDictRed.containsKey(currentWord)){
						
						int[] wordVal = {1, Integer.parseInt(newEntry[0])};
						polarityDictRed.put(currentWord, wordVal);
						
					}else{
						
						int[] wordVal = polarityDictRed.get(currentWord);
						wordVal[0] = wordVal[0]+1;
						wordVal[1] = wordVal[1]+Integer.parseInt(newEntry[0]);
						
						polarityDictRed.put(currentWord, wordVal);
						
					}
				}
			}
			
			// This stops the timer, and will calculate how long the code took to run, as well as print how many seconds it took
			long endTime = System.nanoTime();
			System.out.println((endTime-startTime)/1000000000+" seconds");
		}
	}
}