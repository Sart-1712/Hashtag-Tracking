package hashtagManagement;

import java.io.*;
import java.util.*;

class Userservice extends Service{
    String username;
    String email;
    String passwd;
    String bio; 

    @Override
    void execute() {
        System.out.println("User service");
    }
    // now we have to take input the values
    public void enter() throws Exception{
        System.out.println("Enter username:");
        this.username = main.sc.nextLine();

        while(!isUserUnique_name(username)){
           System.out.println("Re-enter:");
            this.username = main.sc.nextLine(); 
        }

        System.out.println("Enter email:");
        this.email = main.sc.nextLine();
        while((this.email.indexOf("@")==-1 || this.email.indexOf(".")==-1) ){
            System.out.println("|        ERROR        |");
            System.out.println("|Enter valid email ID |");
            // System.out.println("\n");

            System.out.println("Re-enter:");
            this.email = main.sc.nextLine(); 
            while(!isUserUnique_mail(email)){
              System.out.println("|User already registered from this email !!|");
              this.email = main.sc.nextLine(); 
              System.out.println("Re-enter:");
            }
        }
        

        System.out.println("Enter password (minimum 4 characters) :");
        this.passwd = main.sc.nextLine();
        while(passwd.length()<4){
            System.out.println("| - - - - - - - - - - - - - |");
            System.out.println("|           ERROR           |");
            System.out.println("|Enter valid password please|");
            System.out.println("| - - - - - - - - - - - - - |");
            this.passwd = main.sc.nextLine();
        }
        System.out.println("Enter your BIO (Optional) ");
        this.bio = main.sc.nextLine();

        // return true;
    }

    public void enterLogin() throws Exception {
    System.out.println("Enter username:");
    this.username = main.sc.nextLine();

    System.out.println("Enter email:");
    this.email = main.sc.nextLine();

    // to get email address from terminal in and storing that in a variable to be later used to verify login.

        while(this.email.indexOf("@")==-1 || this.email.indexOf(".")==-1 ){
            System.out.println("|        ERROR        |");
            System.out.println("|Enter valid email ID |");
            // System.out.println("\n");

            this.email = main.sc.nextLine();
        }


    System.out.println("Enter password:");
    this.passwd = main.sc.nextLine();
        while(passwd.length()<4){
            System.out.println("| - - - - - - - - - - - - - |");
            System.out.println("|           ERROR           |");
            System.out.println("|Enter valid password please|");
            System.out.println("| - - - - - - - - - - - - - |");
            this.passwd = main.sc.nextLine();
        }

    }

    // will be implementing two methods one forlogin and one for registration

    public boolean register(String username, String email, String passwd,String bio) throws Exception {
        try (FileWriter f = new FileWriter("users.txt", true)) {
            f.write(username + ", " + email + ", " + passwd + ", " + bio + "\n");
        }

        System.out.println("User registered");
        return true;
    }

    public boolean login(String username, String email, String passwd) throws Exception {
        File file = new File("users.txt");
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                // split the data separately

                if (parts[0].trim().equals(username) && parts[1].trim().equals(email)
                        && parts[2].trim().equals(passwd)) {
                    return true;
                }

            }
        }
        System.out.println("Unsuccesfull login");
        return false;
    }

    private static void rewriteFile(List<String> lines) throws Exception {
        // Standard FileWriter without try-catch
        FileWriter fw = new FileWriter("users.txt", false);
        for (String line : lines) {
            fw.write(line + "\n");
        }
        fw.close();
    }

    public static void editBio(String username, String email, String passwd, String newbio) throws Exception {

    File file = new File("users.txt");

    List<String> allLines = new ArrayList<>();

    Scanner fileScanner = new Scanner(file);

    while (fileScanner.hasNextLine()) {
        String line = fileScanner.nextLine();
        String[] parts = line.split(",");

        if (parts.length >= 3) {

            String a = parts[0].trim();
            String b = parts[1].trim();

            if (a.equals(username) && b.equals(email)) {
                String updatedLine = a + ", " + b + ", " + passwd + ", " + newbio;
                allLines.add(updatedLine);
            } else {
                allLines.add(line);
            }
        }
    }

    fileScanner.close();

    rewriteFile(allLines);

    System.out.println("Successfully updated!");
}

// will be ensuring that there are unique usernames...
public boolean isUserUnique_name(String username) throws Exception {
    File file = new File("users.txt");

    if (!file.exists()) return true; 

    Scanner sc = new Scanner(file);

    while (sc.hasNextLine()) {
        String[] parts = sc.nextLine().split(",");

        String existingUsername = parts[0].trim();

        if (existingUsername.equals(username)) {
            System.out.println("Username already taken! CHOOSE ANOTHER");
            sc.close();
            return false;
        }
    }

    sc.close();
    return true;
}

// will also have to check for unique mails to avoid clashes!
public boolean isUserUnique_mail(String email) throws Exception {
    File file = new File("users.txt");

    if (!file.exists()) return true; 

    Scanner sc = new Scanner(file);

    while (sc.hasNextLine()) {
        String[] parts = sc.nextLine().split(",");

        
        String existingEmail = parts[1].trim();

        if (existingEmail.equals(email)) {
            System.out.println("Email already registered!");
            sc.close();
            return false;
        }

       
    }

    sc.close();
    return true;
}
}