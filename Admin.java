package hashtagManagement;

class Admin extends User {

    public Admin(String username, String email, String password, String bio) {
    super(username, email, password, "");//inherited from user class
    }

    public void deletePost(String postId) {
        System.out.println("Admin deleted post: " + postId);
    }

    public void banUser(String username) {
        System.out.println("Admin banned user: " + username);
    }

    public void viewReports() {
        System.out.println("Admin viewing reports...");
    }
}
