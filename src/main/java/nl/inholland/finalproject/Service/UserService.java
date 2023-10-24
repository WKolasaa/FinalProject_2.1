package nl.inholland.finalproject.Service;

import nl.inholland.finalproject.Database.Database;
import nl.inholland.finalproject.Model.Exceptions;
import nl.inholland.finalproject.Model.User;

public class UserService {
    Database database;

    public UserService(Database database){
        this.database = database;
    }

    public boolean isValid(String userName, String password) throws Exceptions.UserNotFoundException, Exceptions.InvalidPasswordException{
        User tmpUser = database.getUserByUsername(userName);
        if(tmpUser == null){
            throw new Exceptions.UserNotFoundException("User not found.");
        }
        if(!tmpUser.checkPassword(password)){
            throw new Exceptions.InvalidPasswordException("Invalid password.");
        }
        return true;
    }

    public User getUserByUsername(String username) throws Exceptions.UserNotFoundException{
        User tmpUser = database.getUserByUsername(username);
        if(tmpUser == null){
            throw new Exceptions.UserNotFoundException("User not found.");
        }
        return tmpUser;
    }
}
