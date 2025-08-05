package PLANT_PAGES;

import DATA_TYPES.Plant;
import DATA_TYPES.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class AddPageController {

    //fxml components of the page
    @FXML private Button addButton;
    @FXML private Button backButton;
    @FXML private TextField nicknameTextField;
    @FXML private DatePicker waterDatePicker;
    @FXML private ColorPicker plantColorPicker;
    @FXML private ImageView plantImageView;

    Plant plant;
    User user;
    Plant selectedPlant;

    //methods for setting the user and plant
    public void setUser(User user){
        this.user = user;
    }

    /** the setPlant method is used to give the exact type to the plant**/
    public void setPlant(Plant plant){
        this.plant = plant;
        updateUI();
    }

    /** the updateUI method sets the values for the elements held within the page**/
    private void updateUI() {
        plantImageView.setImage(new Image(getClass().getResourceAsStream(plant.getImagePath())));
    }


    /** the setPlantData method takes the values from the text field, color picker and date picker from the add page and
     * initializes a plant with those values.**/
    public void setPlantData(){
        String plantType;
        String plantNickname;
        Color plantColor;
        LocalDate waterDate;

        plantType = plant.getName();
        plantNickname = nicknameTextField.getText();
        plantColor = plantColorPicker.getValue();
        waterDate = waterDatePicker.getValue();

        LocalDateTime dateTime = waterDate.atStartOfDay();

        //create plant
        selectedPlant = new Plant(plantType, dateTime, plantNickname, plantColor);

    }



    //METHODS FOR THE ACTIONS OF THE BUTTONS
    @FXML
    public void addButtonAction(ActionEvent event) {
        //adding the selectedPlant to the user's collection of plants
        setPlantData();
        user.addPlant(selectedPlant);

        //for verification purposes
        System.out.println("Plant added: " + selectedPlant.getName());
        System.out.println("User now has " + user.getPlants().size() + " plant(s).");

    }

    @FXML
    public void backButtonAction(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(MAIN_APP.Main.enterScene);
        stage.setResizable(false);


        stage.show();
    }


}
