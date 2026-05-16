package hashtagManagement;

import java.io.*;
import java.util.*;

public class Reports {

    // Displays all unique hashs
    public void view() throws Exception {
        HashManage hs = new HashManage();
        hs.loadPast();
        // Have to view all contents of map
        for (String key : store.hashtagPosts.keySet()) {
            System.out.println("#" + key);
        }
    }

    public void postsView() throws Exception {

        File file = new File("posts.txt"); // ?
        if (!file.exists()) {
            System.out.println("No posts found. Create one first!");
            return;
        }

        boolean found = false;

        // Use a standard file scanner without a try-catch block
        Scanner fileScanner = new Scanner(file);

        Integer y = 0;

        // System.out.println("\n--- Your Posts ---");
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] parts = line.split("\\|");

            if (parts.length >= 4 && !parts[2].trim().isEmpty()) {
                found = true;
                y++;
            }

        }
        System.out.println(y);

        fileScanner.close(); // Manually close the file scanner!

        if (!found) {
            System.out.println("Nothing has been posted yet..");
        }

    }

    public void showActive(int n) {

        HashManage hs = new HashManage();
        hs.loadPast();

        store.user_active.clear(); // IMPORTANT

        File file = new File("posts.txt");
        if (!file.exists()) {
            System.out.println("No posts found.");
            return;
        }

        boolean found = false;

        try {
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\\|");

                if (parts.length >= 4 && !parts[1].trim().isEmpty()) {

                    store.user_active.put(
                            parts[1],
                            store.user_active.getOrDefault(parts[1], 0) + 1);

                    found = true;
                }
            }

            fileScanner.close();

        } catch (

        Exception e) {
            System.out.println("Error reading file");
        }

        if (!found) {
            System.out.println("Nothing has been posted yet..");
            return;
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(store.user_active.entrySet());

        list.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        for (int i = 0; i < Math.min(n, list.size()); i++) {
            Map.Entry<String, Integer> entry = list.get(i);
            System.out.println(entry.getKey()
                    + " NO OF POSTS: "
                    + entry.getValue());
        }
    }

    public void summary() throws Exception {

        HashManage hs = new HashManage();
        hs.loadPast();

        File file = new File("posts.txt");

        if (!file.exists()) {
            return;
        }

        Scanner sc = new Scanner(file);
        int count = 0;

        while (sc.hasNextLine()) {
            sc.nextLine();
            count++;
        }

        sc.close();
        System.out.println("Total number of posts:" + count);
        System.out.println("----------------------");
        System.out.println("Unique Hashtags: " + store.hashtagPosts.size());
        System.out.println("----------------------");

    }
    // For printing the tags.
    public void printTags(String... tags) {
        for (String t : tags)
            System.out.println(t);
    }

}
