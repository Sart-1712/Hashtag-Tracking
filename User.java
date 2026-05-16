package hashtagManagement;


class User {
    // object initialise
    String username;
    String email;
    String passwd;
    String bio;

    // Boolean status; // if already there in my file
    public User(String username, String email, String passwd,String bio) {
        this.username = username;
        this.email = email;
        this.passwd = passwd;
        this.bio=bio;

        // Have to write here logic of checking if it is file already then redirect to
        // login else register ------> Lite, rather made a userservice.java file that specifically handles these things. 

    }
    public User(String username) {
        this.username = username;
    }

    // getters
    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.passwd;
    }
    public String getbio(){
        return this.bio;
    }

}