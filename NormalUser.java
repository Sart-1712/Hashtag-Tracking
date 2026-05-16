package hashtagManagement;
public class NormalUser extends User {

    public NormalUser(String username, String email, String password,String bio) {
        super(username, email, password,""); // This passes the data up to the User class
    }

    public void createPost(String content){
        System.out.println("User created post: " + content);
    }

    public void likePost(String postId){
        System.out.println("User liked post: " + postId);
    }

    public void comment(String postId, String comment){
        System.out.println("User commented: " + comment);
    }
}