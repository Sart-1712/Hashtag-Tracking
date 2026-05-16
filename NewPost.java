package hashtagManagement;
import java.io.*;

class NewPost {

    public void newPost(Post post) throws Exception {
        
        FileWriter fw = new FileWriter("posts.txt", true);
        
        String tagsJoined = String.join(",", post.getHashtags());

        // the posts.txt file contains the data with '|' as a seperator.
        
        String line = post.getId() + "|" + post.getUsername() + "|" + post.getContent() + "|" + tagsJoined;
        
        fw.write(line + "\n");
        
        fw.close();

        System.out.println("Post created successfully!");
    }
}