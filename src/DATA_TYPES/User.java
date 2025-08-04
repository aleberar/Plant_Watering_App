package DATA_TYPES;

import java.util.ArrayList;

public class User {
    //every user gets a list of plants
    private ArrayList<Plant> plants;

    //constructor
    public User() {
        plants = new ArrayList<Plant>();
    }


    //getters and setters
    public ArrayList<Plant> getPlants() {
        return plants;
    }
    public void setPlants(ArrayList<Plant> plants) {
        this.plants = plants;
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
