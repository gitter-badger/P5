package Sampler.DatabaseCom;

import Communication.SensorState;
import Sampler.StateScope;

import java.sql.*;

/**
 * Created by heider on 19/11/15.
 */
public class DBFuncs {

    private static Connection openConnection() {
        String url = "jdbc:sqlite:StateScope.db";
        try {
            Connection c = DriverManager.getConnection(url);
            return c;
        } catch (Exception ex) {
        System.out.println("SQLException: " + ex.getMessage());
        }
        return null;
    }

    public static int createDB(){ // Refactor to Autocheck/make table if not existing
        Connection c = openConnection();
        try {
            Statement st = c.createStatement();
            st.executeQuery("CREATE TABLE StateScope(" +
                    //"id int," +
                    "state1 int," +
                    "state2 int," +
                    "state3 int," +
                    "state4 int," +
                    "state5 int," +
                    "state6 int" +
                    ");");
            return 1;
        } catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return 0;
    }

    public static StateScope getStateHistoryFromDBByIndex(int index){
        Connection c = openConnection();
        try {

        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM StateScope WHERE id = " + index + " ");

        StateScope stHis = ((StateScope) rs.getObject(0));
        return stHis;
        } catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return null;
    }

    public static StateScope getStateHistoryFromDBByDate(java.util.Date date){ // Refactor to date
        Connection c = openConnection();
        try {
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM StateScope WHERE date = " + date + " ");

        StateScope stHis = ((StateScope) rs.getObject(0));
        return stHis;
        } catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return null;
    }

    public static void printDB(){
        Connection c = openConnection();
        try (Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery( "SELECT * FROM StateScope" )
        ) {
            while ( rs.next() ) {
                int numColumns = rs.getMetaData().getColumnCount();
                for ( int i = 1 ; i <= numColumns ; i++ ) {
                    // Column numbers start at 1.
                    // Also there are many methods on the result set to return
                    //  the column as a particular type. Refer to the Sun documentation
                    //  for the list of valid conversions.
                    System.out.println( "COLUMN " + i + " = " + rs.getObject(i) );
                }
            }
        } catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    /**
     * @return the row index of the StateScope in the table
     */
    public static int putStateScopeIntoDB(StateScope sc){
        Connection c = openConnection();
        try {
          Statement st = c.createStatement();
          System.out.print(sc.toString());
          ResultSet rs = st.executeQuery("INSERT INTO StateScope VALUES (" + sc.toString() + ")");
        return rs.getRow(); // Refactor to succes/status variable
        } catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return 0;
    }

    /**
     * @return the row index of the SensorState in the table
     */
    public static int putSensorStateIntoDB(SensorState state){
        Connection c = openConnection();
        try {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("INSERT INTO SensorState VALUES (" + state.toString() + ");");
            return rs.getRow(); // Refactor to succes/status variable
        } catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return 0;
    }
}
