package hashtagManagement;
import java.io.*;
import java.util.*;
//goal to detect hashtags and store in a map

public class HashManage {

    public void extractHash(String content) {

        String[] words = content.split("\\s+");
        Set<String> uniqueTags = new HashSet<>();//splitting words to search for tags in the hashset.

        for (String word : words) {
            if (word.startsWith("#") && word.length() > 1) {
                String cleanTag = word.substring(1).replaceAll("[^a-zA-Z0-9._]", "").toLowerCase();

                if (!cleanTag.isEmpty()) {
                    uniqueTags.add(cleanTag);//cleaning the tags to only involve the chosen characters in there and convert everything to null char.
                }
            }
        }

        for (String z : uniqueTags) {
            if (!store.hashtagPosts.containsKey(z)) {
                store.hashtagPosts.put(z, new ArrayList<>());
            }

            store.hashtagPosts.get(z).add(content);
        }
    }

    public void extractCOUNTS(String content) {

        String[] words = content.split("\\s+");
        Set<String> uniqueTags = new HashSet<>();

        for (String word : words) {
            if (word.startsWith("#") && word.length() > 1) {
                String cleanTag = word.substring(1).replaceAll("[^a-zA-Z0-9._]", "").toLowerCase();

                if (!cleanTag.isEmpty()) {
                    uniqueTags.add(cleanTag);
                }
            }
        }

        for (String z : uniqueTags) {
            int count = store.hashsCount.getOrDefault(z, 0);
            store.hashsCount.put(z, count + 1);
        }//counter variable for counting the number of hashtags in the content in the post.
    }

    public void loadPast() {
        store.hashtagPosts.clear();
        store.hashsCount.clear();

        try {
            File file = new File("posts.txt");
            if (!file.exists())
                return;

            try (Scanner fs = new Scanner(file)) {
                while (fs.hasNextLine()) {
                    String line = fs.nextLine();
                    String[] parts = line.split("\\|");

                    if (parts.length >= 3) {
                        String content = parts[2];
                        extractHash(content);
                        extractCOUNTS(content);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }/* VERY VERY USEFUL...DO NOT CHANGE!!
        The old hashset with the values needs to be updated everytime in order to track changes constantly as the old map only works once and gets destroyed in the same login session.
        This code everytime goes into posts.txt file and reworks the extractHash and extractCounts to work efficiently
        , whenever you try to calculate the number of posts or hastags again and again in the same login session.
        */
    }

    public void display(String keyword) {
        // for dispaying the tag pon the terminal.
        loadPast();
        keyword = keyword.replaceAll("[^a-zA-Z0-9._]", "").toLowerCase();

        if (!store.hashtagPosts.containsKey(keyword)) {
            System.out.println("No posts from this tag!!");
            return;
        }

        for (String g : store.hashtagPosts.get(keyword)) {
            System.out.println(g);
            System.out.println("\n");
        }
    }

    public void displayTrending(int topN) {

        List<Map.Entry<String, Integer>> list = new ArrayList<>(store.hashsCount.entrySet());

        list.sort((a, b) -> b.getValue() - a.getValue());

        System.out.println("Top " + topN + " Trending Hashtags:");

        for (int i = 0; i < Math.min(topN, list.size()); i++) {
            Map.Entry<String, Integer> entry = list.get(i);
            System.out.println("#" + entry.getKey() + " : " + entry.getValue());
        }
    }
}
