package PLANT_PAGES;

import DATA_TYPES.Plant;
import DATA_TYPES.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomPlantPageController {

    @FXML private Button backButton;
    @FXML private Button addPlantButton;
    @FXML private TextField plantSpeciesTextField;
    @FXML private TextField plantWaterTimeTextField;
    @FXML private Button plant1Button;
    @FXML private Button plant2Button;
    @FXML private Button plant3Button;
    @FXML private Button plant4Button;
    @FXML private Button plant5Button;
    @FXML private Button plant6Button;

    private User user;
    private Plant plant = new Plant();
    private String plantSpecies;
    private int plantWaterTime;
    private String plantImagePath;
    Stage stage;
    Scene scene;

    public void setUser(User user) {
        this.user = user;
    }

    public void setPlantData(){
        //plant = new Plant();
        plantSpecies = plantSpeciesTextField.getText();
        plantWaterTime = Integer.parseInt(plantWaterTimeTextField.getText());
        plant.setName(plantSpecies);
        plant.setTime(plantWaterTime);
        plantImagePath = "/PICTURES/plant1.png";
        plant.setImagePath(plantImagePath);
    }

    //the click of a button sets the image
    @FXML
    public void button1Clicked(ActionEvent actionEvent) {
        setPlantData();
        plantImagePath = "/PICTURES/plant1.png";
        plant.setImagePath(plantImagePath);
    }

    @FXML
    public void button2Clicked(ActionEvent actionEvent) {
        setPlantData();
        plantImagePath = "/PICTURES/plant2.png";
        plant.setImagePath(plantImagePath);
    }

    @FXML
    public void button3Clicked(ActionEvent actionEvent) {
        setPlantData();
        plantImagePath = "/PICTURES/plant3.png";
        plant.setImagePath(plantImagePath);
    }

    @FXML
    public void button4Clicked(ActionEvent actionEvent) {
        setPlantData();
        plantImagePath = "/PICTURES/plant4.png";
        plant.setImagePath(plantImagePath);
    }

    @FXML
    public void button5Clicked(ActionEvent actionEvent) {
        setPlantData();
        plantImagePath = "/PICTURES/plant5.png";
        plant.setImagePath(plantImagePath);
    }

    @FXML
    public void button6Clicked(ActionEvent actionEvent) {
        setPlantData();
        plantImagePath = "/PICTURES/plant6.png";
        plant.setImagePath(plantImagePath);
    }

    @FXML
    public void AddPlantButtonClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader addPlantLoader = new FXMLLoader(getClass().getResource("/PLANT_PAGES/AddPage.fxml"));
        Parent root = addPlantLoader.load();
        AddPageController addPlantController = addPlantLoader.getController();

        addPlantController.setUser(user);

        //setPlantData();
        addPlantController.setPlant(this.plant);

        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root, 1800, 1000);
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();
    }

    @FXML
    private void backButtonAction(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(MAIN_APP.Main.enterScene);
        stage.setResizable(false);


        stage.show();
    }
}
