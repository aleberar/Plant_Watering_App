package MAIN_APP;

import DATA_TYPES.Plant;
import DATA_TYPES.User;
import PLANT_PAGES.AddPageController;
import PLANT_PAGES.CustomPlantPageController;
import PLANT_PAGES.PlantDetailsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**this is the main controller that manages all the other controllers**/
public class Controller {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Plant selectedPlant;
    private User selectedUser = new User();

    public Controller() throws IOException {
    }

    //MONSTERA
    //function to switch from main to Monstera
    public void switchToMonstera(ActionEvent event) throws IOException {

        //linking plant controller to the main controller
        FXMLLoader plantLoader = new FXMLLoader(getClass().getResource("/PLANT_PAGES/PlantDetails.fxml"));
        Parent root = plantLoader.load();
        PlantDetailsController plantController = plantLoader.getController();
        plantController.setUser(selectedUser);

        String monsteraDetails = "Monstera plants, known for their distinctive split and holey leaves, are fascinating subjects. They are climbers in the wild, using aerial roots to scale trees, and can even produce edible fruit when ripe. Their unique leaf shape, with natural splits and holes, is not just for show; it helps them withstand wind and rain in their natural habitat, and also allows more sunlight to reach lower leaves. The common name \"Swiss cheese plant\" refers to the characteristic holes and splits in the leaves, similar to the appearance of Swiss cheese. ";
        selectedPlant = new Plant("Monstera", "/PICTURES/monstera.png", monsteraDetails);
        plantController.setPlant(selectedPlant);


        //set up the stage
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1800, 1000);
        stage.setScene(scene);
        stage.setResizable(false);

        //show the stage
        stage.show();
    }
    public void switchBackFromMonstera(ActionEvent event) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(Main.enterScene);
        //Sstage.setScene(scene);

        stage.show();
    }



    //PATHOS
    //function to switch from main to Monstera
    public void switchToPathos(ActionEvent event) throws IOException {
        //linking plant controller to the main controller
        FXMLLoader plantLoader = new FXMLLoader(getClass().getResource("/PLANT_PAGES/PlantDetails.fxml"));
        Parent root = plantLoader.load();
        PlantDetailsController plantController = plantLoader.getController();
        plantController.setUser(selectedUser);

        String pathosDetails = "Pothos, also known as devil's ivy, is a popular houseplant with fun facts including its ability to thrive in neglect, its trailing growth habit, and its various nicknames like \"money plant\". It's known for its air-purifying qualities and ability to grow to impressive lengths, both indoors and in the wild. ";
        selectedPlant = new Plant("Pathos", "/PICTURES/pathos.PNG", pathosDetails);
        plantController.setPlant(selectedPlant);


        //set up the stage
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1800, 1000);
        stage.setScene(scene);
        stage.setResizable(false);

        //show the stage
        stage.show();
    }
    public void switchBackFromPathos(ActionEvent event) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(Main.enterScene);
        //Sstage.setScene(scene);

        stage.show();
    }



    //DRACAENA
    public void swithToDracaena(ActionEvent event) throws IOException {
        //link plant controller to the main controller
        FXMLLoader plantLoader = new FXMLLoader(getClass().getResource("/PLANT_PAGES/PlantDetails.fxml"));
        Parent root = plantLoader.load();
        PlantDetailsController plantController = plantLoader.getController();

        plantController.setUser(selectedUser);
        String dracaenaDetails = "Dracaena, commonly known for its dragon-like resin, is a genus of flowering plants with over 100 species, many of which are popular houseplants. Fun facts include its name originating from the Greek word for \"female dragon,\" its relation to the asparagus family, and its toxicity to cats and dogs. ";
        selectedPlant = new Plant("Dracaena", "/PICTURES/dracaena.PNG", dracaenaDetails);
        plantController.setPlant(selectedPlant);


        //set stage
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1800, 1000);
        stage.setScene(scene);
        stage.setResizable(false);

        //making the stage visible
        stage.show();
    }




    //JADE PLANT
    public void switchToJadePlant(ActionEvent event)throws IOException {
        FXMLLoader plantLoader = new FXMLLoader(getClass().getResource("/PLANT_PAGES/PlantDetails.fxml"));
        Parent root = plantLoader.load();
        PlantDetailsController plantController = plantLoader.getController();
        plantController.setUser(selectedUser);

        String jadePlantDetails = "Jade plants, scientifically known as Crassula ovata, are popular succulents with a variety of fun facts and cultural significance. They are known for their thick, jade-green, oval-shaped leaves and can live for decades, sometimes even becoming cherished family heirlooms. They are also associated with good luck, prosperity, and friendship, often given as gifts to symbolize these qualities. ";
        selectedPlant = new Plant("Jade Plant", "/PICTURES/jadePlant.png", jadePlantDetails);
        plantController.setPlant(selectedPlant);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1800, 1000);
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();

    }



    //ALOE VERA
    public void switchToAloeVera(ActionEvent event) throws IOException {
        FXMLLoader plantLoader = new FXMLLoader(getClass().getResource("/PLANT_PAGES/PlantDetails.fxml"));
        Parent root = plantLoader.load();
        PlantDetailsController plantController = plantLoader.getController();
        plantController.setUser(selectedUser);

        String aloeVeraDetails = "Aloe vera, a succulent plant with a long history, is known for its medicinal and skincare benefits. It contains a gel that's 99% water and is rich in vitamins, minerals, and amino acids. The plant has been used for over 6,000 years, with records from ancient Egypt and Cleopatra using it in her beauty rituals. ";
        selectedPlant = new Plant("Aloe Vera", "/PICTURES/aloeVera.PNG", aloeVeraDetails);
        plantController.setPlant(selectedPlant);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1800, 1000);
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();

    }

    //PEACE LILY
    public void switchToPeaceLily(ActionEvent event) throws IOException {
        FXMLLoader plantLoader = new FXMLLoader(getClass().getResource("/PLANT_PAGES/PlantDetails.fxml"));
        Parent root = plantLoader.load();
        PlantDetailsController plantController = plantLoader.getController();
        plantController.setUser(selectedUser);

        String peaceLilyDetails = "Peace lilies are popular houseplants known for their elegant white flowers and air-purifying qualities. They are native to tropical regions and belong to the Araceae family, not the lily family. Symbolically, they represent peace, purity, and sympathy, and are often given as gifts to express condolences or offer comfort. ";
        selectedPlant = new Plant("Peace Lily", "/PICTURES/peaceLily.PNG", peaceLilyDetails);
        plantController.setPlant(selectedPlant);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1800, 1000);
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();
    }


    //ZZ PLANT
    public void switchToZZPlant(ActionEvent event) throws IOException {
        FXMLLoader plantLoader = new FXMLLoader(getClass().getResource("/PLANT_PAGES/PlantDetails.fxml"));
        Parent root = plantLoader.load();
        PlantDetailsController plantController = plantLoader.getController();
        plantController.setUser(selectedUser);


        String zzPlantDetails = "ZZ plants, scientifically known as Zamioculcas zamiifolia, are popular houseplants known for their resilience and low-maintenance nature. Native to eastern Africa, they are surprisingly drought-tolerant due to their potato-like rhizomes that store water. They can thrive in low-light conditions and are relatively slow-growing, making them ideal for indoor spaces. ";
        selectedPlant = new Plant("ZZ Plant", "/PICTURES/ZZ.PNG", zzPlantDetails);
        plantController.setPlant(selectedPlant);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1800, 1000);
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();
    }


    //CUSTOM PLANT
    public void customPlantSwitch(ActionEvent event) throws IOException {
        FXMLLoader customPlantLoader = new FXMLLoader(getClass().getResource("/PLANT_PAGES/CustomPlantPage.fxml"));
        Parent root = customPlantLoader.load();
        CustomPlantPageController customPlantPageController = customPlantLoader.getController();

        customPlantPageController.setUser(selectedUser);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1800, 1000);
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();
    }
}
