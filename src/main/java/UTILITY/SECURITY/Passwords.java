package UTILITY.SECURITY;

import org.mindrot.jbcrypt.BCrypt;
import java.nio.charset.StandardCharsets;

public class Passwords {
    private Passwords() {}

    /** WORK_FACTOR is a local variable used for hashing (basically the cost)**/
    private static final int WORK_FACTOR = 12;

    /** hash is the method that creates the hashed version of the password given by the user
     * @param plain is the original password given by the user that goes through the process of validation
     * @return the return value is the encrypted / hashed version of the original password
     */
    public static String hash(String plain){
        requiredValid(plain);
        return BCrypt.hashpw(plain, BCrypt.gensalt(WORK_FACTOR));
    }


    /** requiredValid is a method that takes the password given by the user and checks if it's length fits the parameters
     * @param plain is the original password given by the user
     */
    private static void requiredValid(String plain){
        if (plain == null || plain.length() < 8){
            throw new IllegalArgumentException("Password must contain at least 8 characters");
        }

        if(plain.getBytes(StandardCharsets.UTF_8).length > 72){
            throw new IllegalArgumentException("Password must contain at most 72 characters");
        }
    }

    /** verify is a method that checks weather or not the hash password corresponds with the password given by the user
     * it is used to validate the entry into the account of a user
     * @param plain is the password introduced by the user
     * @param hash is the hashed password of the user that is stored in the database
     * @return returns true if the passwords match and false if they don't
     */
    public static boolean verify(String plain, String hash){
        if(plain == null || hash == null || hash.isBlank()){
            return false;
        }
        return BCrypt.checkpw(plain, hash);
    }


}
