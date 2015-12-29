
package simplepolygon;

import com.sun.media.jfxmedia.events.MarkerEvent;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.sound.sampled.LineEvent;


public class SimplePolygon extends Application {

    char c;

    double angle[];
    int noOfLines;
    boolean constructed;

    @Override

    public void start(Stage primaryStage) {

        LinkedList xc = new LinkedList();
        LinkedList yc = new LinkedList();
        LinkedList xc1 = new LinkedList();
        LinkedList yc1 = new LinkedList();
        LinkedList labels = new LinkedList();
        noOfLines = 0;
        constructed=false;
        final Group root = new Group();
        primaryStage.setResizable(false);

        Scene scene = new Scene(root, 1024, 600);

        scene.setFill(Color.valueOf("51649a"));
        primaryStage.setTitle("Simple Polygon");

        Rectangle drawable = new Rectangle(0, 0, 800, 550);
        drawable.setFill(Color.valueOf("90abf8"));
        drawable.setCursor(Cursor.HAND);

        Label points = new Label("Points");
        points.setLayoutX(900);
        points.setLayoutY(20);

        Label org = new Label("0");
        org.setLayoutX(10);
        org.setLayoutY(555);
        org.setTextFill(Color.WHITE);
        org.setScaleX(2);
        org.setScaleY(2);

        Label xmax = new Label("800");
        xmax.setLayoutX(780);
        xmax.setLayoutY(555);
        xmax.setTextFill(Color.BLACK);
        xmax.setScaleX(2);
        xmax.setScaleY(2);

        Label ymax = new Label("550");
        ymax.setLayoutX(10);
        ymax.setLayoutY(5);
        ymax.setTextFill(Color.BLACK);
        ymax.setScaleX(2);
        ymax.setScaleY(2);

        points.setTextFill(Color.WHITE);
        points.setScaleX(3);
        points.setScaleY(3);
        Image imageOk = new Image("file:ok.png");
        Button create = new Button("CONSTRUCT", new ImageView(imageOk));
        create.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        create.setLayoutX(40);
        create.setLayoutY(558);
        create.setScaleX(1.4);
        create.setScaleY(1.4);
        Image imageNot = new Image("file:not.png");
        Button clear = new Button("     CLEAR     ", new ImageView(imageNot));
        clear.setStyle("-fx-font: 22 arial; -fx-base: #e7b6c0;");
        clear.setLayoutX(800);
        clear.setLayoutY(558);

        clear.setScaleX(1.4);
        clear.setScaleY(1.4);
        Image imageHelp = new Image("file:help.png");
        Button help = new Button("     HELP     ", new ImageView(imageHelp));
        help.setStyle("-fx-font: 22 arial; -fx-base: #b6cee7;");
        help.setLayoutX(425);
        help.setLayoutY(558);

        help.setScaleX(1.4);
        help.setScaleY(1.4);

        Image marker = new Image("file:marker.png");

        TableView<Cord> table = new TableView<Cord>();
        final ObservableList<Cord> data
                = FXCollections.observableArrayList();
        table.setLayoutX(800);
        table.setLayoutY(50);
        table.setPrefHeight(500);
        root.getChildren().addAll(drawable, points, table, create, clear, help);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("file:icon.png"));
        primaryStage.show();

        table.setEditable(true);

        TableColumn x = new TableColumn("X");
        x.setCellValueFactory(new PropertyValueFactory<Cord, String>("x"));

        TableColumn y = new TableColumn("Y");
        y.setCellValueFactory(
                new PropertyValueFactory<Cord, String>("y")
        );

        TableColumn lab = new TableColumn("Label");
        lab.setCellValueFactory(
                new PropertyValueFactory<Cord, String>("lab")
        );

        table.setItems(data);
        table.getColumns().addAll(lab, x, y);

        table.setPlaceholder(new Label("No points have been added"));

        c = 'A';

        drawable.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                ImageView markerView = new ImageView(marker);
                markerView.setScaleX(0.2);
                markerView.setScaleY(0.2);
                markerView.setLayoutX(event.getX() - 63);
                markerView.setLayoutY(event.getY() - 76);
                markerView.setCursor(Cursor.CLOSED_HAND);
                Label markerLabel = new Label("" + c);

                markerLabel.setLayoutX(event.getX() - 20);
                markerLabel.setLayoutY(event.getY() - 15);
                markerLabel.setCursor(Cursor.HAND);

                root.getChildren().add(markerView);

                root.getChildren().add(markerLabel);

                xc.add(event.getX());
                yc.add(event.getY());
                labels.add(c);
                char loc = c;
                data.add(new Cord("" + c, "" + (int) event.getX(), "" + (int) Math.abs(event.getY() - 550)));
                if (constructed) {
                            xc1.clear();
                            yc1.clear();

                            int noOfChildren = root.getChildren().size();
                            int noOfNewPoints = 2 * (xc.size() - noOfLines);
                            root.getChildren().remove(noOfChildren - noOfLines - noOfNewPoints, noOfChildren - noOfNewPoints);
                            noOfLines=xc.size();
                            if(noOfLines<=2)
                                    noOfLines=0;
                            sortPoints(xc, yc, xc1, yc1);
                            drawLineNoAnim(root, xc1, yc1);
                        }

                markerView.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event1) {
                        //  System.out.println(event1);
                        if(event1.getSceneX()>800||event1.getSceneY()>550||event1.getSceneX()<0||event1.getSceneY()<0)
                            return;
                        markerView.setLayoutX(event1.getSceneX() - 63);
                        markerView.setLayoutY(event1.getSceneY() - 73);
                        markerLabel.setLayoutX(event1.getSceneX() - 20);
                        markerLabel.setLayoutY(event1.getSceneY() - 15);
                        int index = labels.indexOf(loc);
                        labels.remove(index);
                        xc.remove(index);
                        yc.remove(index);
                        data.remove(index);
                        xc.add(index, event1.getSceneX());
                        yc.add(index, event1.getSceneY());
                        labels.add(index, loc);

                        data.add(index, new Cord("" + loc, "" + (int) event1.getSceneX(), "" + (int) Math.abs(event1.getSceneY() - 550)));

                        if (constructed) {
                            xc1.clear();
                            yc1.clear();

                            int noOfChildren = root.getChildren().size();
                            int noOfNewPoints = 2 * (xc.size() - noOfLines);

                            root.getChildren().remove(noOfChildren - noOfLines - noOfNewPoints, noOfChildren - noOfNewPoints);
                            sortPoints(xc, yc, xc1, yc1);
                            drawLineNoAnim(root, xc1, yc1);
                        }
                    }
                });

                markerView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event1) {
                        if (event1.getClickCount() == 2) {
                            int index = labels.indexOf(loc);
                            labels.remove(index);
                            xc.remove(index);
                            yc.remove(index);
                            data.remove(index);
                            root.getChildren().remove(markerView);
                            root.getChildren().remove(markerLabel);
                            if (constructed) {
                                xc1.clear();
                                yc1.clear();

                                int noOfChildren = root.getChildren().size();
                                root.getChildren().remove(noOfChildren - noOfLines , noOfChildren );
                                noOfLines = xc.size();
                                if(noOfLines<=2)
                                    noOfLines=0;
                                sortPoints(xc, yc, xc1, yc1);
                                drawLineNoAnim(root, xc1, yc1);
                            }
                        }
                    }
                });

                c = (char) (c + 1);
            }
        });

        create.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                xc1.clear();
                yc1.clear();
                int noOfChildren = root.getChildren().size();
                int noOfNewPoints = 2 * (xc.size() - noOfLines);
                root.getChildren().remove(noOfChildren - noOfLines - noOfNewPoints, noOfChildren - noOfNewPoints);
                noOfLines = xc.size();
                constructed=true;
                sortPoints(xc, yc, xc1, yc1);
                drawLine(root, xc1, yc1);
            }
        });

        clear.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                noOfLines = 0;
                constructed=false;
                clearLine(root, xc, yc, labels, data);
                root.getChildren().addAll(drawable, points, table, create, clear, help);
            }
        });
          help.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                
                HelpMenu h=new HelpMenu();
                Stage st=new Stage();
                h.start(st);
             
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public void sortPoints(LinkedList xc, LinkedList yc, LinkedList xc1, LinkedList yc1) {
        int size;
        if (xc.size() < 3) {
            return;
        }
        size = xc.size();
        double cx, cy;
        int min = 0;
        for (int i = 1; i < size; i++) {
            if ((double) yc.get(i) > (double) yc.get(min)) {
                min = i;
            }

        }
        cx = (double) xc.get(min);
        cy = (double) yc.get(min);
        Object[] xcord = xc.toArray(new Object[size]);
        Object[] ycord = yc.toArray(new Object[size]);
        angle = new double[size];

        for (int i = 0; i < size; i++) {
            double xd = ((double) xcord[i] - cx);
            double yd = ((double) ycord[i] - cy);
            if (xd != 0 && yd != 0) {
                angle[i] = Math.toDegrees(Math.atan((yd / xd)));
                if (angle[i] < 0) {
                    angle[i] += 180;
                }
               // angle[i]=roundUp(angle[i]);

            } else if (yd == 0) {
                angle[i] = 0;
            } else {
                angle[i] = 90;
            }
        }

        for (int i = 0; i < size; i++) {
            int small = i;
            for (int j = i; j < size; j++) {
                if (angle[j] < angle[small]) {
                    small = j;
                } else if (angle[j] == angle[small]) {
                    double d1, d2, dx, dy;
                    dx = (double) xcord[min] - (double) xcord[small];
                    dy = (double) ycord[min] - (double) ycord[small];

                    d1 = Math.sqrt(dx * dx - dy * dy);

                    dx = (double) xcord[min] - (double) xcord[j];
                    dy = (double) ycord[min] - (double) ycord[j];

                    d2 = Math.sqrt(dx * dx - dy * dy);

                    if (d2 < d1) {
                        small = j;
                    }

                }
            }
            double temp = angle[i];
            angle[i] = angle[small];
            angle[small] = temp;

            xc1.add(xcord[small]);
            yc1.add(ycord[small]);
            xcord[small] = xcord[i];

            ycord[small] = ycord[i];

        }

    }


    public void clearLine(Group root, LinkedList xc, LinkedList yc, LinkedList labels, ObservableList data) {
        data.clear();
        root.getChildren().clear();
        xc.clear();
        yc.clear();
        labels.clear();
        c = 'A';
    }

    public void drawLine(Group root, LinkedList xc, LinkedList yc) {
        int size = xc.size();
        ScaleTransition scaleTransition;
        TranslateTransition translateTransition;
        SequentialTransition scaleSeq = new SequentialTransition();
        SequentialTransition transSeq = new SequentialTransition();

        if (size == 2) {
            Line l = new Line();
            l.setStartX((double) xc.get(0));
            l.setStartY((double) yc.get(0));
            l.setEndX((double) xc.get(1));
            l.setEndY((double) yc.get(1));
            root.getChildren().add(l);

        } else if (size >= 3) {
            for (int i = size - 1; i >= 0; i--) {

                Line l = new Line();
                l.setStartX((double) xc.get(i));
                l.setStartY((double) yc.get(i));
                l.setEndX((double) xc.get((i + 1) % size));
                l.setEndY((double) yc.get((i + 1) % size));
                double ang1;
                if (i == size - 1) {
                    ang1 = 180;
                } else {
                    ang1 = angle[i + 1];
                }

                scaleTransition
                        = new ScaleTransition(Duration.millis(Math.abs(ang1 - angle[i]) * 30), l);
                scaleTransition.setToX(1f);
                scaleTransition.setToY(1f);
                translateTransition
                        = new TranslateTransition(Duration.millis(Math.abs(ang1 - angle[i]) * 30), l);
                translateTransition.setFromX(l.getLayoutX() + ((l.getEndX() - l.getStartX()) / 2));
                translateTransition.setFromY(l.getLayoutY() + ((l.getEndY() - l.getStartY()) / 2));

                translateTransition.setToX(l.getLayoutX());
                translateTransition.setToY(l.getLayoutY());
                l.setScaleX(0.0001);
                l.setScaleY(0.0001);

                transSeq.getChildren().add(translateTransition);
                scaleSeq.getChildren().add(scaleTransition);
                root.getChildren().add(l);

            }
            
            transSeq.setDelay(Duration.millis(Math.abs(angle[size-1]-180)*30));

            scaleSeq.setDelay(Duration.millis(Math.abs(angle[size-1]-180)*30));
            Image scanLine = new Image("file:line.png");
            ImageView lineView = new ImageView(scanLine);
            lineView.setLayoutX((double) xc.get(0) - 800);
            lineView.setLayoutY((double) yc.get(0) - 37);

            root.getChildren().add(1, lineView);
            RotateTransition rt = new RotateTransition(Duration.millis(1800 * 3), lineView);
            rt.setByAngle(-180);
            rt.play();
            rt.setOnFinished(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    lineView.setVisible(false);
                }
            });

            transSeq.play();
            scaleSeq.play();

        }
    }

    public void drawLineNoAnim(Group root, LinkedList xc, LinkedList yc) {
        int size = xc.size();

        if (size == 2) {
            Line l = new Line();
            l.setStartX((double) xc.get(0));
            l.setStartY((double) yc.get(0));
            l.setEndX((double) xc.get(1));
            l.setEndY((double) yc.get(1));
            root.getChildren().add(l);

        } else if (size >= 3) {
            for (int i = size - 1; i >= 0; i--) {

                Line l = new Line();
                l.setStartX((double) xc.get(i));
                l.setStartY((double) yc.get(i));
                l.setEndX((double) xc.get((i + 1) % size));
                l.setEndY((double) yc.get((i + 1) % size));
                double ang1;
                if (i == size - 1) {
                    ang1 = 180;
                } else {
                    ang1 = angle[i + 1];
                }

                root.getChildren().add(l);

            }

        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class Cord {

        private final SimpleStringProperty x;
        private final SimpleStringProperty y;
        private final SimpleStringProperty lab;

        private Cord(String lab, String x, String y) {
            this.x = new SimpleStringProperty(x);
            this.y = new SimpleStringProperty(y);
            this.lab = new SimpleStringProperty(lab);
        }

        public String getX() {
            return x.get();
        }

        public String getY() {
            return y.get();
        }

        public String getLab() {
            return lab.get();
        }

    }

}
