package hashtagManagement;
import java.util.Scanner;

public class main {

    public static Scanner sc = new Scanner(System.in);
    static User currentUser = null;

    public static void main(String args[]) throws Exception {
        intro();
    // the intro menu starts in the main and every navigation to every menu is coded into the intro menu.
    }

    public static void intro() throws Exception {
        while (true) {
            System.out.println("\n - - - - - W E L C O M E - - - - - - - -");
            System.out.println("|     1. Register a New Account         |");
            System.out.println("|     2. Login to Your Account          |");
            System.out.println("|     3. Exit Application               |");
            System.out.println(" - - - - - - - - - - - - - - - - - - - - ");
            System.out.print("Select an option: ");

            int type = Integer.parseInt(sc.nextLine());
            if (type < 1 || type > 3) {
                System.out.println("Choose valid option please 🙂");
                continue;
            // using continue cause we want to directly re-enter intro and not relaunch program unless we specifically give it that command.
            }
            if (type == 2) {
                // we will call Userservice class ;
                Userservice u = new Userservice();
                u.enterLogin();
                boolean ok = u.login(u.username, u.email, u.passwd);
                if (ok) {
                    currentUser = new User(u.username, u.email, u.passwd,u.bio);
                    dashboard();
                } else {
                    System.out.println("Login failed!");
                }
            }
            if (type == 1) {
                Userservice u = new Userservice();
                u.enter();
                u.register(u.username, u.email, u.passwd,u.bio);
            }
            if (type == 3) {
                System.out.println("Goodbye!! Have a great Day...");
                System.exit(0);
                break;//to breakout as per user choice.
            }
        }
    }

    public static void dashboard() throws Exception {
        while (currentUser != null) {
            System.out.println("\n - - - - - - D A S H B O A R D - - - - - - ");
            System.out.println("|  1. My Profile                          |");
            System.out.println("|  2. Create a New Post                   |");
            System.out.println("|  3. Manage My Posts (View/Edit/Delete)  |");
            System.out.println("|  4. Search by Hashtag                   |");
            System.out.println("|  5. View Trending Hashtags              |");
            System.out.println("|  6. System Reports & Analytics          |");
            System.out.println("|  7. Summary report                      |");
            System.out.println("|  8. Logout                              |");
            System.out.println("|- - - - - - - - - - - - - - - - - - - - -|");
            System.out.print("Select an option: ");

            int typo = Integer.parseInt(sc.nextLine());

            if (typo < 1 || typo > 8) {
                System.out.println("Choose valid option please 🙂");
                continue;
            }
            if (typo == 1) {
                System.out.println("\n--- My Profile ---");
                System.out.println("Username: " + currentUser.getUsername());
                System.out.println("Email: " + currentUser.getEmail());
                System.out.println("Your Bio: " + currentUser.getbio());
                System.out.println("------------------");

                System.out.println("Do u want to edit your bio? Press 1 if yes otherwise 0");
                int d = Integer.parseInt(sc.nextLine());
                if(d==1){
                    //editing of bio goes here 
                   
                    System.out.println("Enter new Bio:");
                    String newbio = sc.nextLine();
                    Userservice j = new Userservice();
                    j.editBio(currentUser.getUsername(),currentUser.getEmail(), currentUser.getPassword(),newbio);
                }
                else{
                    continue;
                }

            }
            if (typo == 2) {
                System.out.println("Write your post:");
                System.out.println("|- - - - - - - - - - - - - - - - - - - - - - - - - -  - - - -|");
                System.out.println("|                      DISCLAIMER                            |");
                System.out.println("|     Please use characters (a-z, ., _, A-Z) in hashtags     |");
                System.out.println("|- - - - - - - - - - - - - - - - - - - - - - - - - -  - - - -|");
                String content = sc.nextLine();
                if (content.trim().isEmpty()) {
                    System.out.println("Post cannot be empty!");
                    continue;
                }
                Post post = new Post(currentUser.getUsername(), content);

                NewPost np = new NewPost();
                np.newPost(post);

                HashManage hs = new HashManage();
                hs.extractHash(content);
                hs.extractCOUNTS(content);
            }
            if (typo == 3) {
                managePostsMenu();//defined below
            }
            if (typo == 4) {
                search_for_hashtags();
            }
            if (typo == 5) {
                give_trending();
            }
            if (typo == 6) {
                generate_report();
            }
            if(typo == 7){
                summary_report();
            }
            if (typo == 8) {
                currentUser = null;
                System.out.println("Logged out successfully!");
                break;
            }
        }
    }

    public static void managePostsMenu() throws Exception {
        PostService ps = new PostService();

        HashManage hs = new HashManage();

        System.out.println("\nFetching your posts..");

        ps.viewMyPosts(currentUser.getUsername());

        System.out.println("\n-------- Manage Posts ---------");
        System.out.println("|    A. Edit a Post            |");
        System.out.println("|    B. Delete a Post          |");
        System.out.println("|    C. Go Back to Dashboard   |");
        System.out.print("Choose an action: ");

        String action = sc.nextLine().toUpperCase().trim();

        if (action.equals("C")) {
            dashboard();
            return;
        }

        System.out.print("Enter the ID of the post: ");
        String targetId = sc.nextLine();

        if (action.equals("A")) {
            System.out.println("Enter your new post content:");
            String newContent = sc.nextLine();

            ps.editPost(currentUser.getUsername(), targetId, newContent);

            //to maintain the map
            hs.extractHash(newContent);
            hs.extractCOUNTS(newContent);

        } else if (action.equals("B")) {
            System.out.print("Are you sure you want to delete this post? (Y/N): ");
            String confirm = sc.nextLine().toUpperCase().trim();
            if (confirm.equals("Y")) {

                // Calls the delete method in PostService
                ps.deletePost(currentUser.getUsername(), targetId);

            } else {
                System.out.println("Deletion cancelled.");
            }
        } else {
            System.out.println("Invalid action. Returning to dashboard.");
        }
    }

    public static void search_for_hashtags() throws Exception{
        System.out.println("Enter the hashtag to be searched for in Posts");
        String search = sc.nextLine();

        if (search.startsWith("#")) {
            search = search.substring(1); 
        }

        HashManage hs = new HashManage();
        hs.display(search);
    }

    public static void give_trending() throws Exception{
        System.out.println("Enter N for N top trending posts");
        int n = Integer.parseInt(sc.nextLine());
        HashManage hs = new HashManage();
        hs.displayTrending(n);
    }

    public static void generate_report() throws Exception{
        System.out.println("Enter N for N top active users");
        int n = Integer.parseInt(sc.nextLine());

        Reports r = new Reports();

        System.out.println("Total hashtags entered till:");
        r.view();
        System.out.println("Total Number of posts:");
        // System.out.println("\n");
        r.postsView();
        System.out.println("N most active users");
        r.showActive(n);
    }
    public static void summary_report() throws Exception{
        System.out.println("-------SUMMARY-------");
        Reports r = new Reports();
        r.summary();
    }
}