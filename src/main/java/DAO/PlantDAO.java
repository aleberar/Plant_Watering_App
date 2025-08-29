package DAO;

import DATA_TYPES.Plant;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface PlantDAO {

    //CREATE
    int insert(int userId, Plant plant) throws SQLException;

    //READ
    Optional<Plant> findById(int id) throws SQLException;
    Optional<Plant> findByIdForUser(int plantId, int userId) throws SQLException;
    List<Plant> findAllByUser(int userId) throws SQLException;
    List<Plant> findDueByUser(int userId, OffsetDateTime cutoff) throws SQLException;

    //UPDATE
    boolean updateNickname(int plantId, int userId, String nickname) throws SQLException;
    boolean updateSpecies(int plantId, int userId, String species) throws SQLException;
    boolean updateColorHex(int plantId, int userId, String colorHex) throws SQLException;
    boolean updateInterval(int plantId, int userId, int hours) throws SQLException;
    boolean updateImagePath(int plantId, int userId, String imagePath) throws SQLException;

    //WATERED NOW
    //last_watering = now, next_watering = interval + now;
    boolean markWateredNow(int plantId, int userId) throws SQLException;

    //GENERIC WATERING
    boolean updateWatering(int plantId, int userId, OffsetDateTime lastWatering) throws SQLException;

    //DELETE
    boolean deleteById(int plantId, int userId) throws SQLException;


    //UTIL
    Optional<Plant> findByNicknameExact(int userId, String nickname) throws SQLException;
    int countByUser(int userId) throws SQLException;
    boolean existsByNickname(int userId, String nickname) throws SQLException;
    List<Plant> searchByNicknameOrSpecies(int userId, String query, int limit, int offset) throws SQLException;
    int countSearchByNicknameOrSpecies(int userId, String query) throws SQLException;

}
