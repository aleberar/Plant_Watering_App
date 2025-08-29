package DATA_TYPES;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class User {
    //every user gets a list of plants
    private ArrayList<Plant> plants;
    private String username;
    private String password_hash;
    private int id;
    private LocalDateTime created_at;

    //constructor
    public User() {
        plants = new ArrayList<Plant>();
    }
    public User(String username, String password_hash) {
        this.username = username;
        this.password_hash = password_hash;
    }


    //getters and setters
    public ArrayList<Plant> getPlants() {
        return plants;
    }
    public void setPlants(ArrayList<Plant> plants) {
        this.plants = plants;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    //methods
    //add plant
    public void addPlant(Plant p) {
        plants.add(p);
    }
    //remove plant
    public void removePlant(Plant p) {
        plants.remove(p);
    }

}
