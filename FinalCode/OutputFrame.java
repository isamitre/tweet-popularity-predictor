import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*class Pair<K, V> {
	  private final K key;
	  private final V value;

	  public Pair(K key, V value) {
	    this.key = key;
	    this.value = value;
	  }

	  public K getKey() {
	    return key;
	  }

	  public V getValue() {
	    return value;
	  }

	  public String toString() {
	    return "(" + key + ", " + value + ")";
	  }
}*/

public class OutputFrame extends JFrame implements ActionListener {
	
	// calulates and returns the number of power words that will be calculated
		  public static int getNumPowerWords(String[] words) {
		    int numPowerWords = words.length/2;
		    if (numPowerWords > 5) {
		      return 5;
		    }
		    return numPowerWords;
		  }
		  
		// returns ordered list of pairs of (word, average)
		  public static ArrayList<Pair<String, Float>> getPowerWordsTreeList(TreeMap<String, int[]> map, String[] words, float score) {
		    // num = size of returning ArrayList
		    int num = getNumPowerWords(words);

		    // creates a TreeMap using the float average as the key
		    TreeMap<Float, ArrayList<String>> averages = new TreeMap<Float, ArrayList<String>>();
		    for (int i = 0; i < words.length; i++) {
		      String word = words[i];
		      // default value of average is a neutral value of 2.0
		      float average = 2.0f;
		      if (map.containsKey(word)) {
		        // if word exists in map, then actually calculates the average
		        int[] values = map.get(word);
		        average = (float) values[1]/values[0];
		      }
		      if (averages.containsKey(average)) {
		        // if averages already contains average, then adds word to its ArrayList of words
		        averages.get(average).add(word);
		      } else {
		        // else a new list consisting of word is added to averages with average as its key
		        ArrayList<String> list = new ArrayList<String>();
		        list.add(word);
		        averages.put(average, list);
		      }
		    }

		    // if score > 2 (good popularity score), creates a minheap to store the num largest elements
		    // else (bad popularity score) creates a maxheap to store the num smallest elements
		    PriorityQueue<Float> queue;
		    if (score > 2) {
		      queue = new PriorityQueue<Float>(num);
		    } else {
		      queue = new PriorityQueue<Float>(num, Collections.reverseOrder());
		    }
		    // modeled/inspired by the example in the Module 5 slides
		    java.util.Iterator<Entry<Float, ArrayList<String>>> iter2 = averages.entrySet().iterator();
		    while(iter2.hasNext()) {
		      Map.Entry elem = (Map.Entry) iter2.next();
		      queue.offer((Float) elem.getKey());

		      // if queue becomes too large, removes undesired element
		      if (queue.size() > num) {
		        queue.poll();
		      }
		    }

		    // converts the heap into the an ArrayList of Pair(word, average)'s
		    ArrayList<Pair<String, Float>> output = new ArrayList<Pair<String, Float>>();
		    while (queue.size() > 0) {
		      float average = queue.peek();
		      queue.poll();
		      ArrayList<String> wordsList = averages.get(average);

		      for (int i = 0; i < wordsList.size(); i++) {
		        String word = wordsList.get(i);
		        Pair pair = new Pair(word, average);
		        output.add(pair);
		      }
		    }
		    // removes undesired elements until output is of size num
		    while (output.size() > num) {
		      output.remove(0);
		    }
		    return output;
		  }

		
		  // returns ordered list of pairs of (word, average)
		  public static ArrayList<Pair<String, Float>> getPowerWordsHashList(HashMap<String, int[]> map, String[] words, float score) {
		    // num = size of returning ArrayList
		    int num = getNumPowerWords(words);

		    // creates a HashMap using the float average as the key
		    HashMap<Float, ArrayList<String>> averages = new HashMap<Float, ArrayList<String>>();
		    for (int i = 0; i < words.length; i++) {
		      String word = words[i];
		      // default value of average is a neutral value of 2.0
		      float average = 2.0f;
		      if (map.containsKey(word)) {
		        // if word exists in map, then actually calculates the average
		        int[] values = map.get(word);
		        average = (float) values[1]/values[0];
		      }
		      if (averages.containsKey(average)) {
		        // if averages already contains average, then adds word to its ArrayList of words
		        averages.get(average).add(word);
		      } else {
		        // else a new list consisting of word is added to averages with average as its key
		        ArrayList<String> list = new ArrayList<String>();
		        list.add(word);
		        averages.put(average, list);
		      }
		    }

		    // if score > 2 (good popularity score), creates a minheap to store the num largest elements
		    // else (bad popularity score) creates a maxheap to store the num smallest elements
		    PriorityQueue<Float> queue;
		    if (score > 2) {
		      queue = new PriorityQueue<Float>(num);
		    } else {
		      queue = new PriorityQueue<Float>(num, Collections.reverseOrder());
		    }
		    // modeled/inspired by the example in the Module 5 slides
		    java.util.Iterator<Entry<Float, ArrayList<String>>> iter2 = averages.entrySet().iterator();
		    while(iter2.hasNext()) {
		      Map.Entry elem = (Map.Entry) iter2.next();
		      queue.offer((Float) elem.getKey());

		      // if queue becomes too large, removes undesired element
		      if (queue.size() > num) {
		        queue.poll();
		      }
		    }

		    // converts the heap into the an ArrayList of Pair(word, average)'s
		    ArrayList<Pair<String, Float>> output = new ArrayList<Pair<String, Float>>();
		    while (queue.size() > 0) {
		      float average = queue.peek();
		      queue.poll();
		      ArrayList<String> wordsList = averages.get(average);

		      for (int i = 0; i < wordsList.size(); i++) {
		        String word = wordsList.get(i);
		        Pair pair = new Pair(word, average);
		        output.add(pair);
		      }
		    }
		    // removes undesired elements until output is of size num
		    while (output.size() > num) {
		      output.remove(0);
		    }
		    return output;
		  }
		  
		  // calculates and returns the popularity score using the TreeMap map
		  public static float getTreeScore(TreeMap<String, int[]> map, String[] words) {
		    float sum = 0;
		    for (int i = 0; i < words.length; i++) {
		      String word = words[i];
		      if (map.containsKey(word)) {
		        // values = { total sum of popularity scores, count of tweets in which word appears}
		        int[] values = map.get(word);
		        float average = (float) values[1]/values[0];  // average popularity score of word
		        sum += average;
		      } else {
		        // if word does not exist in map, then a neutral score of 2 is added
		        sum += 2;
		      }
		    }
		    // returns average score of all words in tweet
		    return sum / words.length;
		  }
		
		  // calculates and returns the popularity score using the HashMap map
		  public static float getHashScore(HashMap<String, int[]> map, String[] words) {
		    float sum = 0;
		    for (int i = 0; i < words.length; i++) {
		      String word = words[i];
		      if (map.containsKey(word)) {
		        // values = { total sum of popularity scores, count of tweets in which word appears}
		        int[] values = map.get(word);
		        float average = (float) values[1]/values[0];  // average popularity score of word
		        sum += average;
		      } else {
		        // if word does not exist in map, then a neutral score of 2 is added
		        sum += 2;
		      }
		    }
		    // returns average score of all words in tweet
		    return sum / words.length;
		  }
		
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

    JButton retry = new JButton(); // this is the button that reads "Try Again!"

    OutputFrame(String tweet, String type) throws NumberFormatException, IOException { // tweet is the tweet string that the user input to the text box, type is either "ordered" or "unordered" depending on the selection
    	
    	String score = "";
        String influentialWordsString = "";
        String timeTakenString = "";

        // Path to the CSV. Enter your path here
        String path = "C:\\Users\\Kian\\Documents\\School\\Code\\training.1600000.processed.noemoticon.csv";
        String line = "";
        
        tweet = removeStopWords(tweet.toLowerCase());
        String[] userWords = tweet.split(" ");
		
		// BufferedReader that will read in each line of the CSV
		BufferedReader kb = new BufferedReader(new FileReader(path));

		// The creation of both the HashMap and TreeMap. Depending on the boolean value, we may use either or
		HashMap<String, int[]> polarityDictHash = new HashMap<String, int[]>();
		TreeMap<String, int[]> polarityDictRed = new TreeMap<String, int[]>();
        
        if (type == "unordered") {
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
        	long totalTime = (endTime-startTime)/1000000000;
        	timeTakenString = totalTime +" Seconds";
        				
        				
        	// Now we will calculate the popularity based off the polarity of the words in the user inputted tweet
        	float hashScore = getHashScore(polarityDictHash, userWords);
        	score = hashScore+"";
        	
        	// Get Influential Words
        	ArrayList<Pair<String, Float>> powerWordsHashList = getPowerWordsHashList(polarityDictHash, userWords, hashScore);
        	for(int i = 0; i < powerWordsHashList.size(); i++){
        		if(i+1 < powerWordsHashList.size()) influentialWordsString = influentialWordsString + powerWordsHashList.get(i).getKey()+",";
        		else influentialWordsString = influentialWordsString+ powerWordsHashList.get(i).getKey();
        	}
        }else {
            
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
        	long totalTime = (endTime-startTime)/1000000000;
        	timeTakenString = totalTime +" Seconds";
        				
        	// Now we will calculate the popularity based off the polarity of the words in the user inputted tweet
        	float treeScore = getTreeScore(polarityDictRed, userWords);
        	treeScore = (float)Math.round(treeScore/100) / 100;
        	score = treeScore+"";
        	
        	// Get Influential Words
        	ArrayList<Pair<String, Float>> powerWordsTreeList = getPowerWordsTreeList(polarityDictRed, userWords, treeScore);
        	for(int i = 0; i < powerWordsTreeList.size(); i++){
        		if(i+1 < powerWordsTreeList.size()) influentialWordsString = influentialWordsString + powerWordsTreeList.get(i).getKey()+",";
        		else influentialWordsString = influentialWordsString+ powerWordsTreeList.get(i).getKey();
        	}
        }
        
        ImageIcon twitter = new ImageIcon("twitter.png"); // this is the image for the twitter logo used on the screen
        ImageIcon scaleImage = new ImageIcon("betterScale.png"); // this is the image for the scale of faces that can be seen on the screen
        // the next two images are scaled versions of the 2 images above to fit the screen
        ImageIcon smallTwitter = new ImageIcon(twitter.getImage().getScaledInstance(twitter.getIconWidth() / 5, twitter.getIconHeight() / 5, Image.SCALE_SMOOTH));
        ImageIcon smallScale = new ImageIcon(scaleImage.getImage().getScaledInstance(scaleImage.getIconWidth() / 5, scaleImage.getIconHeight() / 5, Image.SCALE_SMOOTH));
        JLabel jScale = new JLabel(); // this is the label that contains the image of the scale
        JLabel leftImage = new JLabel(); // this is the label that contains the upper left twitter logo and the title "Results"
        JLabel rightImage = new JLabel(); // this is the label that contains the upper right twitter logo
        JPanel panel = new JPanel(); // this is the panel that contains the upper 2 images
        // the rest of the labels are all different texts used in the frame
        JLabel text = new JLabel();
        JLabel text2 = new JLabel();
        JLabel text3 = new JLabel();
        JLabel timeTaken = new JLabel();
        JLabel influentialWords = new JLabel();
        JLabel tweetRating = new JLabel();
        JLabel scaleText = new JLabel();
        JLabel ratingNums = new JLabel();

        jScale.setIcon(smallScale); // sets the scale's image to the image of the scale
        jScale.setBounds(305, 300, 181, 50); // sets its position and size

        retry.setBounds(283, 375, 225, 50); // sets the retry button's position and size
        retry.addActionListener(this); // allows the button to perform a function when pressed
        retry.setFocusable(false); // this makes the text inside the button to not be surrounded by a text box
        retry.setText("Try Again!"); // sets the button's text

        /*
        * Lines 62 - 96 sets the text, font, size, and position of all of the different text labels in the frame
        */
        ratingNums.setText("0         2         4");
        ratingNums.setBounds(324, 275, 181, 25);
        ratingNums.setFont(new Font("SansSerif Bold", Font.BOLD, 20));

        scaleText.setText("Rating Scale");
        scaleText.setBounds(336, 225, 118, 50);
        scaleText.setFont(new Font("SansSerif Bold", Font.BOLD, 20));

        text.setText("Time Taken to Complete:");
        text.setFont(new Font("SansSerif Bold", Font.BOLD, 20));
        text.setBounds(20, 75, 260, 50);

        timeTaken.setText(timeTakenString);
        timeTaken.setFont(new Font("SansSerif Bold", Font.PLAIN, 20));
        timeTaken.setBounds(265, 75, 250, 50);

        text2.setText("Your Tweet Rating:");
        text2.setFont(new Font("SansSerif Bold", Font.BOLD, 20));
        text2.setBounds(20, 125, 190, 50);

        tweetRating.setText(score);
        tweetRating.setFont(new Font("SansSerif Bold", Font.PLAIN, 20));
        tweetRating.setBounds(210, 125, 200, 50);

        text3.setText("Most Influential Words:");
        text3.setFont(new Font("SansSerif Bold", Font.BOLD, 20));
        text3.setBounds(20, 175, 230, 50);

        influentialWords.setText(influentialWordsString);
        influentialWords.setFont(new Font("SansSerif Bold", Font.PLAIN, 20));
        influentialWords.setBounds(250, 175, 540, 50);

        leftImage.setIcon(smallTwitter);
        leftImage.setText("Results");
        leftImage.setFont(new Font("SansSerif Bold", Font.PLAIN, 50));

        rightImage.setIcon(smallTwitter); // this sets the top right image of the frame to the twitter logo

        panel.add(leftImage); // this adds the top left image as well as the title to the top of the page
        panel.add(rightImage); // this adds the top right image to the top of the page
        panel.setBackground(new java.awt.Color(137, 207, 240)); // this sets the page's background color to light blue

        this.setTitle("Tweet Popularity Predictor"); // sets the title of the frame
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // allows the frame to be closed upon pressing top right "x"
        this.setResizable(false); // prohibits the frame from being resized
        this.setSize(800, 500); // sets the size of the frame
        //this.setVisible(true);
        // the rest of the code (lines 110 - 120) adds the components to the frame
        this.add(retry);
        this.add(text);
        this.add(text2);
        this.add(text3);
        this.add(timeTaken);
        this.add(influentialWords);
        this.add(tweetRating);
        this.add(jScale);
        this.add(scaleText);
        this.add(ratingNums);
        this.add(panel);
        //this.validate();
        this.setVisible(true);
    }
    /*
    * This method is called when the "Try Again!" button is pressed
    * The method will create a new InputFrame object to allow the user to easily use the program again
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == retry) {
            InputFrame frame = new InputFrame();
        }
    }
}