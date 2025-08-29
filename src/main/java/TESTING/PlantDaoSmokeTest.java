package TESTING;

import DAO.JDBCPlantDAO;
import DAO.JDBCUserDAO;
import DAO.PlantDAO;
import DAO.UserDAO;
import DATA_TYPES.Plant;
import DATA_TYPES.User;
import UTILITY.SECURITY.Passwords;
import javafx.scene.paint.Color;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

public class PlantDaoSmokeTest {

    // helper: convert LocalDateTime -> OffsetDateTime using system zone
    private static OffsetDateTime toOffset(LocalDateTime ldt) {
        return ldt.atZone(ZoneId.systemDefault()).toOffsetDateTime();
    }

    public static void main(String[] args) throws Exception {
        UserDAO userDAO = new JDBCUserDAO();
        PlantDAO plantDAO = new JDBCPlantDAO();

        String suffix = Long.toString(System.nanoTime()).substring(9); // short-ish unique suffix
        String username = "test_user_" + suffix;
        String passwordHash = Passwords.hash("SuperSafePwd_123");

        // 1) Create a temp user
        User u = new User();
        u.setUsername(username);
        u.setPassword_hash(passwordHash);
        int userId = userDAO.insert(u);
        System.out.println("Created test user id = " + userId + " (" + username + ")");

        try {
            // 2) Prepare a plant
            Plant p = new Plant();
            p.setName("Rosa canina");                  // species
            p.setNickname("Rosie_" + suffix);          // nickname (unique-ish)
            p.setTime(24);                              // interval in hours
            p.setColor(Color.web("#3399FF"));          // any hex you like
            p.setImagePath("PICTURES/plant1.png");     // any path you use

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime last = now.minusHours(p.getTime());  // last watering = interval ago
            LocalDateTime next = last.plusHours(p.getTime());  // next watering = now

            p.setLastWateredDate(last);
            p.setNextWateredDate(next);

            // 3) Insert
            int plantId = plantDAO.insert(userId, p);
            System.out.println("Inserted plant id = " + plantId);
            printPlant("After insert", p);

            // 4) Read by id
            Optional<Plant> byId = plantDAO.findById(plantId);
            System.out.println("findById present? " + byId.isPresent());
            byId.ifPresent(pl -> printPlant("findById", pl));

            // 5) Read by id for user
            Optional<Plant> byIdForUser = plantDAO.findByIdForUser(plantId, userId);
            System.out.println("findByIdForUser present? " + byIdForUser.isPresent());

            // 6) List all by user
            List<Plant> allForUser = plantDAO.findAllByUser(userId);
            System.out.println("findAllByUser size = " + allForUser.size());

            // 7) Exists by nickname
            boolean existsNickname = plantDAO.existsByNickname(userId, p.getNickname());
            System.out.println("existsByNickname(" + p.getNickname() + ") = " + existsNickname);

            // 8) Updates (nickname/species/interval/color/image)
            boolean upNick = plantDAO.updateNickname(plantId, userId, p.getNickname() + "_EDIT");
            boolean upSpecies = plantDAO.updateSpecies(plantId, userId, "Rosa gallica");
            boolean upInterval = plantDAO.updateInterval(plantId, userId, 12); // change to 12h
            boolean upColor = plantDAO.updateColorHex(plantId, userId, "#00AA88");
            boolean upImg = plantDAO.updateImagePath(plantId, userId, "PICTURES/plant2.png");
            System.out.printf("updateNickname=%s, updateSpecies=%s, updateInterval=%s, updateColor=%s, updateImage=%s%n",
                    upNick, upSpecies, upInterval, upColor, upImg);

            // Re-read to verify updates
            byId = plantDAO.findById(plantId);
            byId.ifPresent(pl -> printPlant("After updates", pl));

            // 9) markWateredNow -> last=now, next=now+interval (12h now)
            boolean wateredNow = plantDAO.markWateredNow(plantId, userId);
            System.out.println("markWateredNow = " + wateredNow);
            plantDAO.findById(plantId).ifPresent(pl -> printPlant("After markWateredNow", pl));

            // 10) updateWatering to a custom time (simulate watering 36h ago)
            LocalDateTime customLast = LocalDateTime.now().minusHours(36);
            boolean updatedWatering = plantDAO.updateWatering(plantId, userId, toOffset(customLast));
            System.out.println("updateWatering(36h ago) = " + updatedWatering);
            plantDAO.findById(plantId).ifPresent(pl -> printPlant("After updateWatering(36h ago)", pl));

            // 11) find due plants (cutoff = now)
            List<Plant> due = plantDAO.findDueByUser(userId, toOffset(LocalDateTime.now()));
            System.out.println("findDueByUser (<= now) count = " + due.size());

            // 12) find by exact nickname
            String exactName = p.getNickname() + "_EDIT";
            plantDAO.findByNicknameExact(userId, exactName)
                    .ifPresent(pl -> printPlant("findByNicknameExact", pl));

            // 13) search + count
            String q = "ros"; // should match Rosa*
            List<Plant> results = plantDAO.searchByNicknameOrSpecies(userId, q, 10, 0);
            int total = plantDAO.countSearchByNicknameOrSpecies(userId, q);
            System.out.println("search '" + q + "' -> page size=" + results.size() + ", total=" + total);

            // 14) delete
            boolean deleted = plantDAO.deleteById(plantId, userId);
            System.out.println("deleteById = " + deleted);

            // 15) confirm deletion
            boolean existsAfterDelete = plantDAO.existsByNickname(userId, exactName);
            System.out.println("exists after delete -> " + existsAfterDelete);

        } finally {
            // Cleanup the temp user (plants should be gone already if you have ON DELETE CASCADE;
            // if not, we deleted the plant above anyway)
            boolean userDeleted = userDAO.deleteById(userId);
            System.out.println("Deleted test user -> " + userDeleted);
        }
    }

    private static void printPlant(String label, Plant pl) {
        System.out.printf(
                "%s: id=%d, user_id=%d, species=%s, nickname=%s, interval(h)=%d, last=%s, next=%s, image=%s%n",
                label,
                pl.getId(),
                pl.getUser_id(),
                pl.getName(),
                pl.getNickname(),
                pl.getTime(),
                pl.getLastWateredDate(),
                pl.getNextWateredDate(),
                pl.getImagePath()
        );
    }
}
