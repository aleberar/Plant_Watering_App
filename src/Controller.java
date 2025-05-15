import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    private Stage stage;
    private Scene scene;
    private Parent root;

    //MONSTERA
    //function to switch from main to Monstera
    public void switchToMonstera(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("PLANT_PAGES/MonsteraScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);



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
        Parent root = FXMLLoader.load(getClass().getResource("PLANT_PAGES/pathosPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);



        stage.show();
    }
    public void switchBackFromPathos(ActionEvent event) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(Main.enterScene);
        //Sstage.setScene(scene);

        stage.show();
    }

    public void Monstera(ActionEvent e){
        System.out.println("Monstera");

    }



    //Come to my 127.0.0.1 and I’ll give you sudo access.
}
