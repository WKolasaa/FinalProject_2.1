package nl.inholland.finalproject.Database;

import nl.inholland.finalproject.Model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database implements Serializable {
    private List<User> users;
    private static final long serialVersionUID = 315557262292413227L;

    public Database() {
        users = new ArrayList<>();
        User user1 = new User("admin", "admin", User.Role.ADMIN);
        User user2 = new User("user", "user", User.Role.USER);

        users.add(user1);
        users.add(user2);
    }

    public void serialize() {
        try (FileOutputStream fileOut = new FileOutputStream("database.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static Database deserialize() {
        Database database;
        try (FileInputStream fileIn = new FileInputStream("database.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            database = (Database) in.readObject();
        } catch (IOException | ClassNotFoundException c) {
            c.printStackTrace();
            database = new Database();
        }
        return database;
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
