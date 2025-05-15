import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    public static Scene enterScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage enterStage) throws Exception {


        try{
            Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));

            enterScene = new Scene(root, 900, 700,Color.rgb(129,186,62));


            Image logo = new Image("logo.png");
            enterStage.getIcons().add(logo);
            enterStage.setTitle("ThirstyRoots");

            //connected the CSS file
            //enterScene.getStylesheets().add(getClass().getResource("MainPageDesign.css").toExternalForm());

            String css = this.getClass().getResource("MainPageDesign.css").toExternalForm();
            enterScene.getStylesheets().add(css);

            //editing stage
            //enterStage.setFullScreen(true);
            //enterStage.setFullScreenExitHint("ESC to leave....");
            //components of stage
            enterStage.setScene(enterScene);
            //show stage
            enterStage.show();

            ///1:07:28

        }catch(Exception e){
            e.printStackTrace();
        }

        //root node
        //Group root = new Group();

        //editing stage
        //upper part


        //parts of scene
        //text
        /*
        Text chooseYourPlant = new Text("Choose Your Plant...");
        chooseYourPlant.setX(50);
        chooseYourPlant.setY(50);
        chooseYourPlant.setFont(Font.font("Book Antiqua", 45));
        chooseYourPlant.setFill(Color.rgb(38, 94, 16));

        //adding buttons -> plants
        Image aloeVera = new Image("aloeVera.png");
        ImageView aloeVeraImageView = new ImageView(aloeVera);
        aloeVeraImageView.setX(50);
        aloeVeraImageView.setY(50);
        */
        //ImageView aloeImageView = new ImageView(aloeImage);
        //Button aloeVera = new Button("Aloe Vera");
        //aloeVera.setGraphic(aloeImageView);
        // i need to change the size of the image to fit the page correctly






        //adding parts to scene
        //root.getChildren().add(chooseYourPlant);
       // root.getChildren().add(aloeVera);
        //root.getChildren().add(aloeVeraImageView);


    }
}
