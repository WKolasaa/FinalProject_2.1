package nl.inholland.finalproject;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private List<User> users;

    public UserDatabase() {
        users = new ArrayList<>();
        User user1 = new User("admin", "admin", "admin");
        User user2 = new User("user", "user", "user");

        users.add(user1);
        users.add(user2);
    }

    public boolean isValid(String userName, String password) {
        for (User user : users) {
            if (user.getUsername().equals(userName) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public List<User> getAll(){
        return users;
    }
}
