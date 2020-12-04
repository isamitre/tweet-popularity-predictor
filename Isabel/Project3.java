
import java.util.*;
import java.util.regex.*;

class Project3 {
  public static void main(String[] args) {
    // Scanner in = new Scanner(System.in);
    // String tweet = removeStopWords(in.nextLine());
    HashMap<String, int[]> hashMap = buildHashMap();
    TreeMap<String, int[]> treeMap = buildTreeMap();

    String tweet = "A quick brown fox jumps over the lazy dog";
    tweet = removeStopWords(tweet.toLowerCase());
    String[] words = tweet.split(" ");
    System.out.println("size after removal: " + words.length);

    float hashScore = getHashScore(hashMap, words);
    float treeScore = getTreeScore(treeMap, words);
    System.out.println("hashScore: " + hashScore);
    System.out.println("treeScore: " + treeScore);

    ArrayList<Pair<String, Float>> powerWordsHashList = getPowerWordsHashList(hashMap, words, hashScore);
    ArrayList<Pair<String, Float>> powerWordsTreeList = getPowerWordsTreeList(treeMap, words, treeScore);
    System.out.println(powerWordsHashList.toString());
    System.out.println(powerWordsTreeList.toString());
  }

  // replaces all stop words with an empty string
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
    Iterator iter2 = averages.entrySet().iterator();
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
    Iterator iter2 = averages.entrySet().iterator();
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

  // calulates and returns the number of power words that will be calculated
  public static int getNumPowerWords(String[] words) {
    int numPowerWords = words.length/2;
    if (numPowerWords > 5) {
      return 5;
    }
    return numPowerWords;
  }

  // for testing, returns a HashMap
  public static HashMap<String, int[]> buildHashMap() {
    HashMap<String, int[]> map = new HashMap<String, int[]>();
    int[] arr1 = {3, 7};
    map.put("quick", arr1); // quick: 2.33
    int[] arr2 = {3, 7};
    map.put("brown", arr2); // brown: 2.33
    int[] arr3 = {3, 7};
    map.put("fox", arr3);   // fox:   2.33
    int[] arr4 = {1, 2};
    map.put("jumps", arr4); // jumps: 2.0
    int[] arr5 = {6, 18};
    map.put("lazy", arr5);  // lazy:  3.0
    int[] arr6 = {5, 5};
    map.put("dog", arr6);   // dog:   1.0
    return map;
  }
  // for testing, returns a TreeMap
  public static TreeMap<String, int[]> buildTreeMap() {
    TreeMap<String, int[]> map = new TreeMap<String, int[]>();
    int[] arr1 = {3, 7};
    map.put("quick", arr1); // quick: 2.33
    int[] arr2 = {3, 7};
    map.put("brown", arr2); // brown: 2.33
    int[] arr3 = {3, 7};
    map.put("fox", arr3);   // fox:   2.33
    int[] arr4 = {1, 2};
    map.put("jumps", arr4); // jumps: 2.0
    int[] arr5 = {6, 18};
    map.put("lazy", arr5);  // lazy:  3.0
    int[] arr6 = {5, 5};
    map.put("dog", arr6);   // dog:   1.0
    return map;
  }
}
