package Sampler.DatabaseCom;

import Sampler.StateHistory;

import java.sql.*;

/**
 * Created by heider on 19/11/15.
 */
public class DBFuncs {

    private static Connection openConnection() {
        String url = "jdbc:sqlite:~/github/P5/AI/StateHistories.db";
        try {
            Connection c = DriverManager.getConnection(url);
            return c;
        } catch (Exception ex) {
        System.out.println("SQLException: " + ex.getMessage());
        }
        return null;
    }

    public static StateHistory getStateHistoryFromDBByIndex(int index){
        Connection c = openConnection();
        try {

        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM StateHistory WHERE id = " + index + " ");

        StateHistory stHis = ((StateHistory) rs.getObject(0));
        return stHis;
        } catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return null;
    }

    public static StateHistory getStateHistoryFromDBByDate(Date date){ // Refactor to date
        Connection c = openConnection();
        try {
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM StateHistory WHERE date = " + date + " ");

        StateHistory stHis = ((StateHistory) rs.getObject(0));
        return stHis;
        } catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return null;
    }

    public static int putStateHistoryIntoDB(StateHistory stHis){
        Connection c = openConnection();
        try {
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("INSERT INTO StateHistory VALUES" + stHis);
        return 1; // Refactor to succes/status variable
        } catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return 0;
    }
}
