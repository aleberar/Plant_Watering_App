package TESTING;

import DAO.JDBCUserDAO;
import DAO.UserDAO;
import DATA_TYPES.User;
import UTILITY.SECURITY.Passwords;

public class DaoSmokeTest {
    public static void main(String[] args) throws Exception {
        UserDAO dao = new JDBCUserDAO();

        // 1) create a unique username
        String username = "testuser_" + System.currentTimeMillis();
        String hash = Passwords.hash("S0meGoodPass!");

        // 2) insert
        User u = new User();
        u.setUsername(username);
        u.setPassword_hash(hash);
        int newId = dao.insert(u);
        System.out.println("Inserted user id = " + newId);

        // 3) findById
        System.out.println("findById -> " + dao.findById(newId).orElseThrow());

        // 4) findByUsername
        System.out.println("findByUsername -> " + dao.findByUsername(username).orElseThrow());

        // 5) exists
        System.out.println("exists -> " + dao.existByUsername(username));

        // 6) update username
        String newUsername = username + "_renamed";
        boolean upd = dao.updateUsername(newId, newUsername);
        System.out.println("updateUsername -> " + upd);

        // 7) update password hash
        boolean pwdUpd = dao.updatePasswordHash(newId, Passwords.hash("AnotherG00dOne!"));
        System.out.println("updatePasswordHash -> " + pwdUpd);

        // 8) delete
        boolean del = dao.deleteById(newId);
        System.out.println("deleteById -> " + del);

        // 9) exists after delete
        System.out.println("exists after delete -> " + dao.existByUsername(newUsername));
    }
}
