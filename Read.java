package hashtagManagement;

import java.io.*;
import java.util.*;

class Read {
    
    // using suppress warnings as these variables weren't read.
    @SuppressWarnings("unused")
    String username;
    @SuppressWarnings("unused")
    String email;
    @SuppressWarnings("unused")
    String passwd;

    public void read() throws Exception {
        File file = new File("users.txt");
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                
                String[] parts = line.split(",");

                //This class reads if the entered credentials match from any of the users.txt file or not!
                
                String username_temp = parts[0];
                String email_temp = parts[1];
                String passwd_temp = parts[2];
                
                this.username = username_temp;
                this.email = email_temp;
                this.passwd = passwd_temp;
            }
        }
    }
}