package nl.inholland.finalproject.Model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class User implements Serializable {
    private String username;
    private Role role;
    private byte[] salt;
    private String hashedPassword;

    public enum Role{
        ADMIN, USER
    }

    public User(String username, String password, Role role){
        this.username = username;
        this.role = role;
        this.salt = getSalt();
        this.hashedPassword = hashPassword(password, salt);
    }

    public String getUsername(){
        return username;
    }

    public Role getRole(){
        return role;
    }

    private byte[] getSalt(){
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }

    private String hashPassword(String password, byte[] salt){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPasswordBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPasswordBytes);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
            return null;
        }
    }

    public boolean checkPassword(String password){
        String hashedInputPassword = hashPassword(password, this.salt);
        assert hashedInputPassword != null;
        return hashedInputPassword.equals(this.hashedPassword);
    }

}
