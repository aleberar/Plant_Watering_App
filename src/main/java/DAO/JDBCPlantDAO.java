package DAO;

import DATABASE.DatabaseConnection;
import DATA_TYPES.Plant;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static DAO.Colors.fromHex;
import static DAO.Colors.toHex;

public class JDBCPlantDAO implements PlantDAO {

    //BASE COLS
    private static final String BASE_COLS = "id, user_id, species, nickname, watering_interval_hours, last_watering, next_watering, color_hex, created_at, image_path";
    //SQL
    private static final String SQL_INSERT = "INSERT INTO plants (user_id, species, nickname, watering_interval_hours, last_watering, next_watering, color_hex, image_path) VALUES (?,?,?,?,?,?,?,?) RETURNING " + BASE_COLS;
    private static final String SQL_FIND_BY_ID = "SELECT id, user_id, species, nickname, watering_interval_hours, last_watering, next_watering, color_hex, image_path FROM plants WHERE id = ?";
    private static final String SQL_FIND_BY_ID_FOR_USER = "SELECT id, user_id, species, nickname, watering_interval_hours, last_watering, next_watering, color_hex, image_path FROM plants WHERE id = ? AND user_id = ?";
    public static final String SQL_FIND_ALL_BY_USER = "SELECT id, user_id, species, nickname, watering_interval_hours, last_watering, next_watering, color_hex, image_path FROM plants WHERE user_id = ?";
    private static final String SQL_FIND_DUE_BY_USER = "SELECT id, user_id, species, nickname, watering_interval_hours, last_watering, next_watering, color_hex, image_path FROM plants WHERE user_id = ? AND next_watering <= ? ORDER BY created_at DESC";
    private static final String SQL_UPDATE_NICKNAME = "UPDATE plants SET nickname = ? WHERE user_id = ? AND id = ?";
    private static final String SQL_UPDATE_SPECIES = "UPDATE plants SET species = ? WHERE user_id = ? AND id = ? ";
    private static final String SQL_UPDATE_COLOR_HEX = "UPDATE plants SET color_hex = ? WHERE user_id = ? AND id = ?";
    private static final String SQL_UPDATE_INTERVAL = "UPDATE plants SET watering_interval_hours = ? WHERE user_id = ? AND id = ?";
    private static final String SQL_UPDATE_IMAGE_PATH = "UPDATE plants SET image_path = ? WHERE user_id = ? AND id = ?";
    private static final String SQL_MARK_WATERED_NOW = "UPDATE plants SET last_watering = NOW(), next_watering = NOW() + (watering_interval_hours  || ' hours')::interval WHERE user_id = ? AND id = ?";
    private static final String SQL_UPDATE_WATERING = "UPDATE plants SET last_watering = ?, next_watering = ? + (watering_interval_hours || ' hours')::interval WHERE user_id = ? AND id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM plants WHERE id = ? AND user_id = ?";
    private static final String SQL_FIND_BY_NICKNAME_EXACT = "SELECT id, user_id, species, nickname, watering_interval_hours, last_watering, next_watering, color_hex, image_path FROM plants WHERE user_id = ? AND nickname = ?";
    private static final String SQL_COUNT_BY_USER = "SELECT COUNT(*) FROM plants WHERE user_id = ?";
    private static final String SQL_EXISTS_BY_NICKNAME = "SELECT 1 FROM plants WHERE user_id = ? AND nickname = ?";
    private static final String SQL_SEARCH_BY_NICKNAME_OR_SPECIES = "SELECT id, user_id, species, nickname, watering_interval_hours, last_watering, next_watering, color_hex, image_path FROM plants WHERE user_id = ? AND (nickname ILIKE ? ESCAPE '\\' OR species ILIKE ? ESCAPE '\\' ) ORDER BY nickname ASC, id ASC LIMIT ? OFFSET ?";
    private static final String SQL_COUNT_SEARCH_BY_NICKNAME_OR_SPECIES = "SELECT COUNT(*) FROM plants WHERE user_id = ? AND (nickname ILIKE ? ESCAPE '\\' OR species ILIKE ? ESCAPE '\\' )";



    //HELPERS
    private static String escapeLike(String s){
        if(s == null) return "";

        return s.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
    }
    private static LocalDateTime toLocal(OffsetDateTime odt, ZoneId zone) {
        return odt == null ? null : odt.toInstant().atZone(zone).toLocalDateTime();
    }
    private static final ZoneId ZONE = ZoneId.systemDefault();
    private static OffsetDateTime toOffset(LocalDateTime ldt) {
        return (ldt == null) ? null : ldt.atZone(ZONE).toOffsetDateTime();
    }


    /** mapRow method is used to map the data from the ResultSet into an instance of a User**/
    private Plant mapRow(ResultSet resultSet) throws SQLException {
        Plant plant = new Plant();
        ZoneId zone = ZoneId.systemDefault();
        OffsetDateTime lastWatering = resultSet.getObject("last_watering", OffsetDateTime.class);
        OffsetDateTime nextWatering = resultSet.getObject("next_watering", OffsetDateTime.class);
        plant.setTime(resultSet.getInt("watering_interval_hours"));
        plant.setImagePath(resultSet.getString("image_path"));
        plant.setNickname(resultSet.getString("nickname"));
        plant.setName(resultSet.getString("species"));
        plant.setColor(fromHex(resultSet.getString("color_hex")));
        plant.setLastWateredDate(toLocal(lastWatering, zone));
        plant.setNextWateredDate(toLocal(nextWatering, zone));
        plant.setId(resultSet.getInt("id"));
        plant.setUser_id(resultSet.getInt("user_id"));

        return plant;
    }



    @Override
    public int insert(int userId, Plant plant) throws SQLException {

        try(Connection c = DatabaseConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(SQL_INSERT)) {

            ps.setInt(1, userId);
            ps.setString(2, plant.getName());
            ps.setString(3, plant.getNickname());
            ps.setInt(4, plant.getTime());
            ps.setObject(5, toOffset(plant.getLastWateredDate()));
            ps.setObject(6, toOffset(plant.getNextWateredDate()));
            ps.setString(7, toHex(plant.getColor()));
            ps.setString(8, plant.getImagePath());

            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    int id = rs.getInt(1);
                    plant.setId(id);
                    return id;
                }
            }
            throw new SQLException("INSERT FAILED");
        }
    }

    @Override
    public Optional<Plant> findById(int id) throws SQLException {

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
    public Optional<Plant> findByIdForUser(int plantId, int userId) throws SQLException {

        try(Connection c = DatabaseConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(SQL_FIND_BY_ID_FOR_USER)) {
            ps.setInt(1, plantId);
            ps.setInt(2, userId);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return Optional.of(mapRow(rs));
                }
            }

        }
        return Optional.empty();
    }

    @Override
    public List<Plant> findAllByUser(int userId) throws SQLException {
        List<Plant> plants = new ArrayList<Plant>();

        try(Connection c = DatabaseConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(SQL_FIND_ALL_BY_USER)) {
            ps.setInt(1, userId);

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    plants.add(mapRow(rs));
                }
            }
        }
        return plants;
    }

    @Override
    public List<Plant> findDueByUser(int userId, OffsetDateTime cutoff) throws SQLException {
        List<Plant> plants = new ArrayList<Plant>();

        try(Connection c = DatabaseConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(SQL_FIND_DUE_BY_USER)) {
            ps.setInt(1, userId);
            ps.setObject(2, cutoff);

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    plants.add(mapRow(rs));
                }
            }
        }
        return plants;
    }

    @Override
    public boolean updateNickname(int plantId, int userId, String nickname) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(SQL_UPDATE_NICKNAME)) {
            ps.setString(1, nickname);
            ps.setInt(2, userId);
            ps.setInt(3, plantId);

            int updated = ps.executeUpdate();
            return updated > 0;
        }

    }

    @Override
    public boolean updateSpecies(int plantId, int userId, String species) throws SQLException {

        try(Connection c = DatabaseConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(SQL_UPDATE_SPECIES)) {
            ps.setString(1, species);
            ps.setInt(2, userId);
            ps.setInt(3, plantId);

            int updated = ps.executeUpdate();
            return updated > 0;
        }

    }

    @Override
    public boolean updateColorHex(int plantId, int userId, String colorHex) throws SQLException {

        try(Connection c = DatabaseConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(SQL_UPDATE_COLOR_HEX)) {
            ps.setString(1, colorHex);
            ps.setInt(2, userId);
            ps.setInt(3, plantId);

            int updated = ps.executeUpdate();
            return updated > 0;
        }

    }

    @Override
    public boolean updateInterval(int plantId, int userId, int hours) throws SQLException {

        try(Connection c = DatabaseConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(SQL_UPDATE_INTERVAL)) {
            ps.setInt(1, hours);
            ps.setInt(2, userId);
            ps.setInt(3, plantId);

            int updated = ps.executeUpdate();
            return updated > 0;
        }

    }

    @Override
    public boolean updateImagePath(int plantId, int userId, String imagePath) throws SQLException {
         try(Connection c = DatabaseConnection.getConnection();
         PreparedStatement ps = c.prepareStatement(SQL_UPDATE_IMAGE_PATH)) {
             ps.setString(1, imagePath);
             ps.setInt(2, userId);
             ps.setInt(3, plantId);

             int updated = ps.executeUpdate();
             return updated > 0;
         }

    }

    @Override
    public boolean markWateredNow(int plantId, int userId) throws SQLException {

        try(Connection c = DatabaseConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(SQL_MARK_WATERED_NOW)) {
            ps.setInt(1, userId);
            ps.setInt(2, plantId);

            int updated = ps.executeUpdate();
            return updated > 0;
        }

    }

    @Override
    public boolean updateWatering(int plantId, int userId, OffsetDateTime lastWatering) throws SQLException {

        try(Connection c = DatabaseConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(SQL_UPDATE_WATERING)) {
            ps.setObject(1, lastWatering);
            ps.setObject(2, lastWatering);
            ps.setInt(3, userId);
            ps.setInt(4, plantId);

            int updated = ps.executeUpdate();
            return updated > 0;
        }

    }

    @Override
    public boolean deleteById(int plantId, int userId) throws SQLException {

        try(Connection c = DatabaseConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(SQL_DELETE_BY_ID)) {
            ps.setInt(1, plantId);
            ps.setInt(2, userId);

            int deleted = ps.executeUpdate();
            return deleted > 0;
        }

    }

    @Override
    public Optional<Plant> findByNicknameExact(int userId, String nickname) throws SQLException {

        try(Connection c = DatabaseConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(SQL_FIND_BY_NICKNAME_EXACT)) {
            ps.setInt(1, userId);
            ps.setString(2, nickname);

            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return Optional.of(mapRow(rs));

                }
            }
        }
        return Optional.empty();
    }

    @Override
    public int countByUser(int userId) throws SQLException {

        try(Connection c = DatabaseConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(SQL_COUNT_BY_USER)) {
            ps.setInt(1, userId);

            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    return rs.getInt(1);
                }else{
                    return 0;
                }
            }
        }
    }

    @Override
    public boolean existsByNickname(int userId, String nickname) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(SQL_EXISTS_BY_NICKNAME)) {
            ps.setInt(1, userId);
            ps.setString(2, nickname);

            try(ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }

    }

    @Override
    public List<Plant> searchByNicknameOrSpecies(int userId, String query, int limit, int offset) throws SQLException {
       if(query == null || query.isBlank()){
           return List.of();
       }

       String pattern = "%" + escapeLike(query) + "%";
       int safeLimit = (limit <= 0) ? 20 : limit;
       int safeOffset = Math.max(0, offset);

       List<Plant> results = new ArrayList<>();

       try(Connection c = DatabaseConnection.getConnection();
           PreparedStatement ps = c.prepareStatement(SQL_SEARCH_BY_NICKNAME_OR_SPECIES)){

           ps.setInt(1, userId);
           ps.setString(2, pattern);
           ps.setString(3, pattern);
           ps.setInt(4, safeLimit);
           ps.setInt(5, safeOffset);

           try(ResultSet rs = ps.executeQuery()) {
               while(rs.next()){
                   results.add(mapRow(rs));
               }
           }

       }

        return results;
    }

    @Override
    public int countSearchByNicknameOrSpecies(int userId, String query) throws SQLException {

        if(query == null || query.isBlank()){
            return 0;
        }

        String pattern = '%' + escapeLike(query) + '%';

        try(Connection c = DatabaseConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(SQL_COUNT_SEARCH_BY_NICKNAME_OR_SPECIES)){
            ps.setInt(1, userId);
            ps.setString(2, pattern);
            ps.setString(3, pattern);

            try(ResultSet rs = ps.executeQuery()){

                return rs.next() ? rs.getInt(1) : 0;

            }
        }
    }
}
