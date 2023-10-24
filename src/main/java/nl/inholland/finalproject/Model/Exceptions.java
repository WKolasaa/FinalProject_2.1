package nl.inholland.finalproject.Model;

public class Exceptions extends Exception{
    public static class UserNotFoundException extends Exception {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class InvalidPasswordException extends Exception {
        public InvalidPasswordException(String message) {
            super(message);
        }
    }

    public static class WrongInputException extends Exception {
        public WrongInputException(String message) {
            super(message);
        }
    }
}
