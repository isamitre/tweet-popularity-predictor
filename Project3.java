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

  public static float getHashScore(HashMap<String, int[]> map, String[] words) {
    float sum = 0;
    for (int i = 0; i < words.length; i++) {
      if (map.containsKey(words[i])) {
        int[] values = map.get(words[i]);
        float average = (float) values[1]/values[0];
        sum += average;
      } else {
        sum += 2;
      }
    }
    return sum / words.length;
  }
  public static float getTreeScore(TreeMap<String, int[]> map, String[] words) {
    float sum = 0;
    for (int i = 0; i < words.length; i++) {
      if (map.containsKey(words[i])) {
        int[] values = map.get(words[i]);
        float average = (float) values[1]/values[0];
        sum += average;
      } else {
        sum += 2;
      }
    }
    return sum / words.length;
  }

  public static ArrayList<Pair<String, Float>> getPowerWordsHashList(HashMap<String, int[]> map, String[] words, float score) {
    int num = getNumPowerWords(words);

    // creates a HashMap using the float average as the key
    HashMap<Float, ArrayList<String>> averages = new HashMap<Float, ArrayList<String>>();
    for (int i = 0; i < words.length; i++) {
      float average = 2.0f;
      if (map.containsKey(words[i])) {
        int[] values = map.get(words[i]);
        average = (float) values[1]/values[0];
      }
      if (averages.containsKey(average)) {
        averages.get(average).add(words[i]);
      } else {
        ArrayList<String> list = new ArrayList<String>();
        list.add(words[i]);
        averages.put(average, list);
      }
    }

    // creates a minheap/maxheap to store the num largest/smallest elements
    // modeled/inspired by the example in the Module 5 slides
    PriorityQueue<Float> queue;
    if (score > 2) {
      queue = new PriorityQueue<Float>(num);
    } else {
      queue = new PriorityQueue<Float>(num, Collections.reverseOrder());
    }
    Iterator iter2 = averages.entrySet().iterator();
    while(iter2.hasNext()) {
      Map.Entry elem = (Map.Entry) iter2.next();
      queue.offer((Float) elem.getKey());

      if (queue.size() > num) {
        queue.poll();
      }
    }

    // converts the heap into the output
    ArrayList<Pair<String, Float>> output = new ArrayList<Pair<String, Float>>();
    while (output.size() < num) {
      float average = queue.peek();
      queue.poll();
      ArrayList<String> wordsList = averages.get(average);
      for (int i = 0; i < wordsList.size(); i++) {
        String word = wordsList.get(i);
        Pair pair = new Pair(word, average);
        output.add(pair);
      }
    }
    if (output.size() > num) {
      for (int i = output.size() - 1; i >= num; i++) {
        output.remove(i);
      }
    }
    return output;
  }
  public static ArrayList<Pair<String, Float>> getPowerWordsTreeList(TreeMap<String, int[]> map, String[] words, float score) {
    int num = getNumPowerWords(words);

    // creates a TreeMap using the float average as the key
    TreeMap<Float, ArrayList<String>> averages = new TreeMap<Float, ArrayList<String>>();
    for (int i = 0; i < words.length; i++) {
      float average = 2.0f;
      if (map.containsKey(words[i])) {
        int[] values = map.get(words[i]);
        average = (float) values[1]/values[0];
      }
      if (averages.containsKey(average)) {
        averages.get(average).add(words[i]);
      } else {
        ArrayList<String> list = new ArrayList<String>();
        list.add(words[i]);
        averages.put(average, list);
      }
    }

    // creates a minheap/maxheap to store the num largest/smallest elements
    // modeled/inspired by the example in the Module 5 slides
    PriorityQueue<Float> queue;
    if (score > 2) {
      queue = new PriorityQueue<Float>(num);
    } else {
      queue = new PriorityQueue<Float>(num, Collections.reverseOrder());
    }
    Iterator iter2 = averages.entrySet().iterator();
    while(iter2.hasNext()) {
      Map.Entry elem = (Map.Entry) iter2.next();
      queue.offer((Float) elem.getKey());

      if (queue.size() > num) {
        queue.poll();
      }
    }

    // converts the heap into the output
    ArrayList<Pair<String, Float>> output = new ArrayList<Pair<String, Float>>();
    while (output.size() < num) {
      float average = queue.peek();
      queue.poll();
      ArrayList<String> wordsList = averages.get(average);
      for (int i = 0; i < wordsList.size(); i++) {
        String word = wordsList.get(i);
        Pair pair = new Pair(word, average);
        output.add(pair);
      }
    }
    if (output.size() > num) {
      for (int i = output.size() - 1; i >= num; i++) {
        output.remove(i);
      }
    }
    return output;
  }

  public static int getNumPowerWords(String[] words) {
    int numPowerWords = words.length/2;
    if (numPowerWords > 5) {
      numPowerWords = 5;
    }
    return numPowerWords;
  }

  public static HashMap<String, int[]> buildHashMap() {
    HashMap<String, int[]> map = new HashMap<String, int[]>();
    int[] arr1 = {2, 7};
    map.put("quick", arr1); // quick: 3.5
    int[] arr2 = {3, 7};
    map.put("brown", arr2); // brown: 2.33
    int[] arr3 = {5, 20};
    map.put("fox", arr3);   // fox: 4
    int[] arr4 = {1, 2};
    map.put("jumps", arr4); // jumps: 2
    int[] arr5 = {6, 18};
    map.put("lazy", arr5);  // lazy: 3
    int[] arr6 = {5, 13};
    map.put("dog", arr6);   // dog: 2.6
    return map;
  }
  public static TreeMap<String, int[]> buildTreeMap() {
    TreeMap<String, int[]> map = new TreeMap<String, int[]>();
    int[] arr1 = {2, 7};
    map.put("quick", arr1);
    int[] arr2 = {3, 7};
    map.put("brown", arr2);
    int[] arr3 = {5, 20};
    map.put("fox", arr3);
    int[] arr4 = {1, 2};
    map.put("jumps", arr4);
    int[] arr5 = {6, 18};
    map.put("lazy", arr5);
    int[] arr6 = {5, 13};
    map.put("dog", arr6);
    return map;
  }
}
