package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main extends Application {
    private int count = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Tour-Planner");

        VBox tours = (VBox) root.lookup("#tours");

        Button addTour = (Button) root.lookup("#addTour");


        Button deleteTourBtn = (Button) root.lookup("#deleteTour");


        Connection c = PostgreSQLJDBC.getConnection();
        Statement stmt = c.createStatement();

        ResultSet rs = stmt.executeQuery( "SELECT * FROM tourdata;" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  name = rs.getString("name");
                String age  = rs.getString("description");
                String  address = rs.getString("info");
                float salary = rs.getFloat("distance");
                System.out.println( "ID = " + id );
                System.out.println( "NAME = " + name );
                System.out.println( "Description = " + age );
                System.out.println( "info = " + address );
                System.out.println( "distance = " + salary );
                System.out.println();

                this.count ++;

                tours.getChildren().add(new Button(name));
            }

            tours.setSpacing(5);

        tours.setPadding(new Insets(10, 10, 10, 10));

        //Creating the mouse event handler
        EventHandler<MouseEvent> addTourHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Button temp = new Button("Tour "+(++count));
                temp.setId("Tour"+count);
                tours.getChildren().add(temp);

            }
        };

        //Creating the mouse event handler
        EventHandler<MouseEvent> deleteTourHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Button deleteTour = (Button) root.lookup("#Tour"+count--);
                deleteTour.setVisible(false);

            }
        };

        addTour.setOnMouseClicked(addTourHandler);
        deleteTourBtn.setOnMouseClicked(deleteTourHandler);

        rs.close();
            stmt.close();
            c.close();







        primaryStage.setScene(new Scene(root, 462, 325));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
