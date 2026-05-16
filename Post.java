package hashtagManagement;
import java.util.ArrayList;
import java.util.List;

public class Post implements Printable {
    String id;
    String username;
    private String content;
    private List<String> hashtags;

    @Override
    public void print(){
        System.out.println(content);//defining the method print of the interface Printable.
    }

    // Constructor for creating a BRAND NEW post
    public Post(String username, String content) {
        this.id = String.valueOf(System.currentTimeMillis()); // Generate unique ID
        this.username = username;
        this.content = content;
        this.hashtags = extractHashtags(content);
    }

    // Constructor for reading an EXISTING post from the file
    public Post(String id, String username, String content, List<String> hashtags) {
        this.id = id;
        this.username = username;
        this.content = content;
        this.hashtags = hashtags;
    }

    // Automatically extracts words starting with '#'
    private List<String> extractHashtags(String text) {
        List<String> tags = new ArrayList<>();
        String[] words = text.split("\\s+"); // Split by spaces
        for (String word : words) {
            if (word.startsWith("#") && word.length() > 1) {

                // Clean it exactly like HashManage (Keep the '#' for the text file if you want,
                // but clean the letters/punctuation and make it lowercase)
                String cleanTag = word.substring(1).replaceAll("[^a-zA-Z0-9._]", "").toLowerCase();

                if (!cleanTag.isEmpty()) {
                    // Reattach the '#' before saving to the text file list
                    tags.add("#" + cleanTag);
                }
            }
        }
        return tags;
    }
    //getters
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    // Setter for editing content
    public void setContent(String newContent) {
        this.content = newContent;
        this.hashtags = extractHashtags(newContent);
    }
}