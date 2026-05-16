package hashtagManagement;

import java.io.*;
import java.util.*;

public class PostService {

    static class PostFilter {
        public boolean byUser(String line, String username) {
            return line.contains(username);
        }
    }

    private final String FILE_NAME = "posts.txt";

    public void viewMyPosts(String username) throws Exception {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No posts found. Create one first!");
            return;
        }

        boolean found = false;

        // Use a standard file scanner without a try-catch block
        Scanner fileScanner = new Scanner(file);

        System.out.println("\n--- Your Posts ---");
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] parts = line.split("\\|");

            if (parts.length >= 3 && parts[1].equals(username)) {
                System.out.println("Post ID: " + parts[0]);
                System.out.println("Content: " + parts[2]);
                if (parts.length == 4 && !parts[3].trim().isEmpty()) {
                    System.out.println("Tags:    " + parts[3]);
                }
                System.out.println("------------------");
                found = true;
            }
        }

        fileScanner.close(); // Manually close the file scanner!

        if (!found) {
            System.out.println("You haven't posted anything yet!");
        }
    }
    // Overloaded Constructors 
    public void showPosts(){
        System.out.println("Showing all posts");
    }

    public void showPosts(String username){
        System.out.println("Showing posts by " + username);
    }

    public void showPosts(String username, int limit){
        System.out.println("Showing " + limit + " posts by " + username);
    }

    public void showPosts(int limit){
        System.out.println("Showing last " + limit + " posts");
    }

    public void editPost(String username, String targetId, String newContent) throws Exception {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No posts found to edit.");
            return;
        }

        List<String> allLines = new ArrayList<>();
        boolean postFoundAndOwned = false;

        Scanner fileScanner = new Scanner(file);

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] parts = line.split("\\|");

            if (parts.length >= 3) {
                String id = parts[0];
                String author = parts[1];

                if (id.equals(targetId) && author.equals(username)) {

                    postFoundAndOwned = true;
                    String newTags = extractTagsForEdit(newContent);
                    String updatedLine = id + "|" + author + "|" + newContent + "|" + newTags;
                    allLines.add(updatedLine);
                } else {
                    allLines.add(line);
                }
            }
        }

        fileScanner.close();

        if (!postFoundAndOwned) {
            System.out.println("Error: Post ID not found or you don't have permission to edit it.");
            return;
        }

        rewriteFile(allLines);
        System.out.println("Post successfully updated!");
    }

    public void deletePost(String username, String targetId) throws Exception {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No posts found to delete.");
            return;
        }

        List<String> allLines = new ArrayList<>();
        boolean postFoundAndOwned = false;

        Scanner fileScanner = new Scanner(file);

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] parts = line.split("\\|");

            if (parts.length >= 3) {
                String id = parts[0];
                String author = parts[1];

                if (id.equals(targetId) && author.equals(username)) {
                    postFoundAndOwned = true;
                } else {
                    allLines.add(line);
                }
            }
        }

        fileScanner.close();
        // adding security fail-safes to ensure not anyone can delete post. Only the user should be able to delete his posts(or the admin[FUTURE]).
        if (!postFoundAndOwned) {
            System.out.println("Error: Post ID not found or you don't have permission to delete it.");
            return;
        }

        rewriteFile(allLines);
        System.out.println("Post successfully deleted!");
    }

    private void rewriteFile(List<String> lines) throws Exception {
        // Standard FileWriter without try-catch
        FileWriter fw = new FileWriter(FILE_NAME, false);
        for (String line : lines) {
            fw.write(line + "\n");
        }
        fw.close(); // Manually close the writer!
    }

    private String extractTagsForEdit(String content) {
        List<String> tags = new ArrayList<>();
        String[] words = content.split("\\s+");
        for (String word : words) {
            if (word.startsWith("#") && word.length() > 1) {
                tags.add(word);
            }
        }
        return String.join(",", tags);
    }
}