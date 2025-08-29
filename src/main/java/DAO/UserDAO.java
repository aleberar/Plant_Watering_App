package DAO;

import DATA_TYPES.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDAO {
    //CREATE
    int insert(User user) throws SQLException;

    //READ
    Optional<User> findById(int id) throws SQLException;
    List<User> findAll() throws SQLException;
    Optional<User> findByUsername(String username) throws SQLException;
    boolean existByUsername(String username) throws SQLException;

    //UPDATE
    boolean updateUsername(int id, String newUsername) throws SQLException;
    boolean updatePasswordHash(int id, String newPasswordHash) throws SQLException;

    //DELETE
    boolean deleteById(int id) throws SQLException;


}
