package DAO;

import DATABASE.DatabaseConnection;
import DATA_TYPES.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCUserDAO implements UserDAO {

    //SQL statements
    private static final String SQL_INSERT = "INSERT INTO users (username, password_hash) VALUES (?, ?) RETURNING id, username, password_hash, created_at";
    private static final String SQL_FIND_BY_ID = "SELECT id, username, password_hash, created_at FROM users WHERE id = ?";
    private static final String SQL_FIND_BY_USERNAME = "SELECT id, username, password_hash, created_at FROM users WHERE username = ?";
    private static final String SQL_FIND_ALL = "SELECT id, username, password_hash, created_at FROM users ORDER BY id";
    private static final String SQL_EXISTS_BY_USERNAME = "SELECT 1 FROM users WHERE username = ?";
    private static final String SQL_UPDATE_USERNAME = "UPDATE users SET username = ? WHERE id = ?";
    private static final String SQL_UPDATE_PASSWORD = "UPDATE users SET password_hash = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM users WHERE id = ?";


    /** mapRow method is used to map the data from the ResultSet into an instance of a User**/
    private User mapRow(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword_hash(resultSet.getString("password_hash"));

        return user;
    }

    /** insert is a method that inserts a new user into the database
     *
     * @param user is the user to be inserted
     * @return is the id of the user
     * @throws SQLException for database errors
     */
    @Override
    public int insert(User user) throws SQLException {

        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(SQL_INSERT)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword_hash());

            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    int id = rs.getInt("id");
                    user.setId(id);
                    return id;
                }

            }throw new SQLException("INSERT FAILED");
        }

    }

    @Override
    public Optional<User> findById(int id) throws SQLException {

        try(Connection c = DatabaseConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }

        return Optional.empty();

    }

    @Override
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<User>();

        try(Connection c = DatabaseConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(SQL_FIND_ALL);
        ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                users.add(mapRow(rs));
            }
        }
        return users;
    }

    @Override
    public Optional<User> findByUsername(String username) throws SQLException {

        try(Connection c = DatabaseConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(SQL_FIND_BY_USERNAME)) {

            ps.setString(1,  username);

            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean existByUsername(String username) throws SQLException {

        try(Connection c =  DatabaseConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(SQL_EXISTS_BY_USERNAME)) {

            ps.setString(1, username);
            try(ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }


    }

    @Override
    public boolean updateUsername(int id, String newUsername) throws SQLException {

        try(Connection c  = DatabaseConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(SQL_UPDATE_USERNAME)) {
            ps.setString(1, newUsername);
            ps.setInt(2, id);

            int updated = ps.executeUpdate();
            return updated > 0;
        }
    }

    @Override
    public boolean updatePasswordHash(int id, String newPasswordHash) throws SQLException {

        try(Connection c = DatabaseConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(SQL_UPDATE_PASSWORD)) {
            ps.setString(1, newPasswordHash);
            ps.setInt(2, id);

            int updated = ps.executeUpdate();
            return updated > 0;
        }

    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(SQL_DELETE_BY_ID)) {
            ps.setInt(1, id);

            int deleted = ps.executeUpdate();
            return deleted > 0;
        }

    }
}
