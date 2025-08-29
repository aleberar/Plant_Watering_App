package MAIN_APP;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class Main extends Application {

    public static Scene enterScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage enterStage) throws Exception {


        try{

            //link fxml page to the main scene
            Parent root = FXMLLoader.load(getClass().getResource("/MAIN_APP/mainPage.fxml"));

            enterScene = new Scene(root, 1800, 1000,Color.rgb(129,186,62));


            Image logo = new Image("PICTURES/logo3.png");
            enterStage.getIcons().add(logo);
            enterStage.setTitle("ThirstyRoots");

            //connected the CSS file
            String css = this.getClass().getResource("/MAIN_APP/MainPageDesign.css").toExternalForm();
            enterScene.getStylesheets().add(css);

            //editing stage

            //making it not resizable
            enterStage.setResizable(false);
            //components of stage
            enterStage.setScene(enterScene);
            //show stage
            enterStage.show();

            ///1:07:28

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
