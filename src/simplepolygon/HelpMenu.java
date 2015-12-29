
package simplepolygon;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class HelpMenu extends Application {

    char c;

    double angle[];
    int noOfLines;
    boolean constructed;

    @Override

    public void start(Stage primaryStage) {

        final Group root = new Group();
        primaryStage.setResizable(false);

        Scene scene = new Scene(root, 600, 450);

        scene.setFill(Color.valueOf("b6cee7"));
        primaryStage.setTitle("HELP");
        primaryStage.getIcons().add(new Image("file:help.png"));

        Label add = new Label("Click to add a Point");
        add.setFont(Font.font("Comic Sans MS"));
        add.setTextFill(Color.BLACK);
        add.setScaleX(2.5);
        add.setScaleY(2.5);
        add.setLayoutX(260);
        add.setLayoutY(100);

        Label drag = new Label("Drag to move a Point");
        drag.setFont(Font.font("Comic Sans MS"));
        drag.setTextFill(Color.BLACK);
        drag.setScaleX(2.5);
        drag.setScaleY(2.5);
        drag.setLayoutX(260);
        drag.setLayoutY(220);

        Label del = new Label("Double Click to delete a Point");
        del.setFont(Font.font("Comic Sans MS"));
        del.setTextFill(Color.BLACK);
        del.setScaleX(2.5);
        del.setScaleY(2.5);
        del.setLayoutX(300);
        del.setLayoutY(340);
        del.setWrapText(true);
        Image x2img=new Image("file:x2.png");
        ImageView x2=new ImageView(x2img);
        x2.setLayoutX(0);
        x2.setLayoutY(230);
        x2.setScaleX(0.35);
        x2.setScaleY(0.35);
        
        
        Button ok=new Button("OK");
        ok.setLayoutX(300);
        ok.setLayoutY(410);
        ok.setScaleX(2);
        ok.setScaleY(2);
        
        ok.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });



        Image click = new Image("file:click.png");
        Image dragimg = new Image("file:drag.png");

        ImageView clickView = new ImageView(click);
        clickView.setScaleX(0.4);
        clickView.setScaleY(0.4);
        clickView.setLayoutX(-80);
        clickView.setLayoutY(-30);

        ImageView click2View = new ImageView(click);
        click2View.setScaleX(0.4);
        click2View.setScaleY(0.4);
        click2View.setLayoutX(-80);
        click2View.setLayoutY(210);

        ImageView dragView = new ImageView(dragimg);

        dragView.setScaleX(0.35);
        dragView.setScaleY(0.35);
        dragView.setLayoutX(-80);
        dragView.setLayoutY(90);



        root.getChildren().addAll(add, clickView, drag, dragView, del, click2View, x2,ok);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
