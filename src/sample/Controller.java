package sample;
import javax.swing.plaf.nimbus.State;
import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class Controller {
    public static Connection getConnection() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/tour-planner",
                            "postgres", "subhan");
            System.out.println("Opened database successfully");


            // stmt = c.createStatement();
//            String sql = "CREATE TABLE tourData " +
//                    "(ID INT PRIMARY KEY     NOT NULL," +
//                    " NAME           TEXT   , " +
//                    " DESCRIPTION            TEXT     , " +
//                    " INFO        CHAR(50), " +
//                    " DISTANCE         REAL)";


//            String sql = "INSERT INTO tourdata (ID,NAME,DESCRIPTION,INFO,DISTANCE) "
//                    + "VALUES (10, 'Tour 1', 'This is tour 1', 'path', 150.6 );";
//            stmt.executeUpdate(sql);
//
//
//            stmt.executeUpdate(sql);
//            stmt.close();
//            c.close();


            //SELECT QUERY

//            ResultSet rs = stmt.executeQuery( "SELECT * FROM tourdata;" );
//            while ( rs.next() ) {
//                int id = rs.getInt("id");
//                String  name = rs.getString("name");
//                String age  = rs.getString("description");
//                String  address = rs.getString("info");
//                float salary = rs.getFloat("distance");
//                System.out.println( "ID = " + id );
//                System.out.println( "NAME = " + name );
//                System.out.println( "Description = " + age );
//                System.out.println( "info = " + address );
//                System.out.println( "distance = " + salary );
//                System.out.println();
//            }
//            rs.close();
//            stmt.close();
//            c.close();



//            String sql = "DELETE from tourdata where name = 'Tour 3';";
//            stmt.executeUpdate(sql);
//            c.commit();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return c;
        // System.out.println("Table updated successfully");
    }

    public static void insertData(String sql) throws SQLException {
        Connection c = getConnection();
        Statement stmt = c.createStatement();



            stmt.executeUpdate(sql);

            stmt.close();
            c.close();
    }

    public static ResultSet getData(String sql) throws SQLException {
        Connection c = getConnection();
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( sql);

        return rs;

    }

}