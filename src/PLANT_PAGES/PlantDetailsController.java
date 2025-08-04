package PLANT_PAGES;

import DATA_TYPES.Plant;
import DATA_TYPES.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


/** the PlantDetailsController is a class used to generalize the behaviour of the plant pages as they are all structured the same **/
public class PlantDetailsController {

    @FXML private Label plantNameLabel;
    @FXML private ImageView plantImageView;
    @FXML private TextArea plantInfoTextArea;
    @FXML private Button addPlantButton; //button that adds the plant to the list
    @FXML private Button backButton; //button that exits the plant page and goes back to the main page

    private Plant plant;
    private User user;

    /** the setPlant method is used to give the exact type to the plant**/
    public void setPlant(Plant plant) {
        this.plant = plant;
        updateUI();
    }


    /** the updateUI method sets the values for the elements held within the page**/
    private void updateUI() {
        plantNameLabel.setText(plant.getName());
        plantImageView.setImage(new Image(getClass().getResourceAsStream(plant.getImagePath())));
        plantInfoTextArea.setText(plant.getDescription());
    }


    public void setUser(User user){
        this.user = user;
    }

    private void addingPlant(){
        user.addPlant(plant);
    }


    @FXML
    private void backButtonAction(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(MAIN_APP.Main.enterScene);
        stage.setResizable(false);


        stage.show();
    }

}
