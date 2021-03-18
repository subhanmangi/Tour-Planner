package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.control.Button;

import javax.swing.*;
import java.sql.*;

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

        //Creating the mouse event handler
        EventHandler<MouseEvent> tourClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                Button calling = (Button) e.getSource();

                TextArea desc = (TextArea) root.lookup("#desc");

                Label title = (Label) root.lookup("#title");

                Connection c = PostgreSQLJDBC.getConnection();

                try {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery( "SELECT * FROM tourdata where name = '"+calling.getText()+"';" );
                    if(rs.next()){
                        String  name = rs.getString("name");
                        String description  = rs.getString("description");
                        String  info = rs.getString("info");
                        float distance = rs.getFloat("distance");

                        desc.setText(description);
                        title.setText("Title: "+name);
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                TableView logs = (TableView) root.lookup("#logs");

                ObservableList<Log> data = FXCollections.observableArrayList();

                try {
                    ResultSet rs = PostgreSQLJDBC.getData("SELECT * from logs where name = '"+calling.getText()+"';" );

                    while (rs.next()){
                        String  name = rs.getString("name");
                        String date  = rs.getString("date");
                        String  duration = rs.getString("duration");
                        String distance = rs.getString("distance");

                        System.out.println("Name: "+name+" date: "+date+" duration: "+duration+" distance: "+distance);

                        data.add(new Log(date,duration,distance));




                    }

                    logs.setItems(data);


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


                System.out.println("I am called by "+calling.getText());

            }
        };


            while ( rs.next() ) {

                String  name = rs.getString("name");
                String description  = rs.getString("description");
                String  info = rs.getString("info");
                float distance = rs.getFloat("distance");

                System.out.println( "NAME = " + name );
                System.out.println( "Description = " + description );
                System.out.println( "info = " + info );
                System.out.println( "distance = " + distance );
                System.out.println();

                this.count++;

                Button temp = new Button(name);
                temp.setId("Tour"+count);
                //System.out.println(temp.getId());

                temp.setOnMouseClicked(tourClicked);

                tours.getChildren().add(temp);


            }

            tours.setSpacing(5);

        tours.setPadding(new Insets(10, 10, 10, 10));





        //Creating the mouse event handler
        EventHandler<MouseEvent> addTourHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Button temp = new Button("Tour "+(++count));
                temp.setOnMouseClicked(tourClicked);
                temp.setId("Tour"+count);

                Connection c = PostgreSQLJDBC.getConnection();
                try {
                    Statement stmt = c.createStatement();
                                String sql = "INSERT INTO tourdata (NAME,DESCRIPTION,INFO,DISTANCE) "
                    + "VALUES ( 'Tour "+(count)+"', 'This is description of Tour "+count+"', '', 150.6 );";
            stmt.executeUpdate(sql);


            stmt.close();
            c.close();



                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                tours.getChildren().add(temp);

            }
        };

        //Creating the mouse event handler
        EventHandler<MouseEvent> deleteTourHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            if(count>0){
                Button deleteTour = (Button) root.lookup("#Tour" + count--);
                System.out.println(deleteTour.getId());
                System.out.println(deleteTour.getText());

                Connection c = PostgreSQLJDBC.getConnection();
                try {
                    Statement stmt = c.createStatement();
                    String sql = "DELETE from tourdata where name = '" + deleteTour.getText() + "';";
                    stmt.executeUpdate(sql);


                    stmt.close();
                    c.close();


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                deleteTour.setVisible(false);



            }
                else{

                JOptionPane.showMessageDialog(null,"No more tours");

                }

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
