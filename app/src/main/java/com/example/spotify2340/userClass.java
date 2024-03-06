public class userClass {
    private String username;
    private String password;

    public userClass(String user, String passw) {
        this.username = user;
        this.password = passw;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setUsername(String user) {
        this.username = user;
    }
    public void setPassword(String passw) {
        this.password = passw;
    }

}
// call this constructor when creating an account on the front end
