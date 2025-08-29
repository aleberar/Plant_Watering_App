package DATA_TYPES;//import java.util.Date;
//import java.sql.Date;
import javafx.scene.paint.Color;

import java.time.LocalDateTime;

public class Plant {

    private int id;
    private int user_id;
    private String name;
    private int time; // nr of hours until next watering
    private LocalDateTime lastWateredDate;
    private LocalDateTime nextWateredDate;
    private String imagePath;
    private String description;
    private String nickname;
    private Color color;

    //constructors
    public Plant(String name, int time, LocalDateTime lastWateredDate, LocalDateTime nextWateredDate, String imagePath,  String description) {
        this.name = name;
        this.time = time;
        this.lastWateredDate = lastWateredDate;
        this.nextWateredDate = nextWateredDate;
        this.imagePath = imagePath;
        this.description = description;

    }
    public Plant(String name, String imagePath,  String description) {
        this.name = name;
        this.imagePath = imagePath;
        this.description = description;
    }
    public Plant(String name, String imagePath, LocalDateTime lastWateredDate, String description) {
        this.name = name;
        this.imagePath = imagePath;
        this.lastWateredDate = lastWateredDate;
        this.description = description;
    }

    public Plant(String name, LocalDateTime lastWateredDate, String nickname, Color color) {
        this.name = name;
        this.lastWateredDate = lastWateredDate;
        this.nickname = nickname;
        this.color = color;

    }
    public Plant() {}


    //getters and setters
    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    public LocalDateTime getLastWateredDate() {
        return lastWateredDate;
    }

    public LocalDateTime getNextWateredDate() {
        return nextWateredDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setLastWateredDate(LocalDateTime lastWateredDate) {
        this.lastWateredDate = lastWateredDate;
    }

    public void setNextWateredDate(LocalDateTime nextWateredDate) {
        this.nextWateredDate = nextWateredDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNickname() {
        return nickname;
    }

    public Color getColor() {
        return color;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    //methods

    /** function that calculates the next watering date
     * it uses the method plusHours from the time package to update the next watering date**/
    public void generateNextWateredDate() {
        LocalDateTime latest = lastWateredDate;
        nextWateredDate =  latest.plusHours(time);
    }


}
