package sample;

import java.sql.*;


public class DatabaseAddingNotes extends Configs {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:mysql://" +
                dbHost + ":" +
                dbPort + "/" +
                dbName + "?" + "autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";


        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public void addNewNote(Note note){
        String insert = "INSERT INTO " +
                "reminds" + "(" +
                Const.REMINDS_USERNAME + "," +
                Const.REMINDS_TEXT + "," +
                Const.REMINDS_DATA + ")" +
                "VALUES(?,?,?)";


        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, note.getUsername());
            prSt.setString(2, note.getText());
            prSt.setString(3, note.getData_time());

            prSt.executeUpdate();
            System.out.println(note.getUsername() + note.getData_time() + note.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getNote (Note note, int mode, String text, int por_num) {
        ResultSet resSet = null;
        String select = null;


        if (mode == 3){

            select =
                    "SELECT * FROM " +
                            Const.REMIND_TABLE +
                            " WHERE " +
                            Const.REMINDS_USERNAME +
                            "=? AND " +
                            Const.REMINDS_TEXT +
                            "=?";


        }
        if (mode == 2){

            select =
                    "DELETE FROM " +
                            Const.REMIND_TABLE +
                            " WHERE " +
                            Const.REMINDS_USERNAME +
                            "=? AND " +
                            Const.REMINDS_ID +
                            "=?";


        }
        if (mode == 1){

            select =
                    "UPDATE " +
                            Const.REMIND_TABLE +
                            " SET " +
                            Const.REMINDS_TEXT +
                            "=?" +
                            " WHERE " +
                            Const.REMINDS_USERNAME +
                            "=? AND " +
                            Const.REMINDS_ID +
                            "=?";


        }
        if(mode == 0){
            select =
                    "SELECT * FROM " +
                            Const.REMIND_TABLE +
                            " WHERE " +
                            Const.REMINDS_USERNAME +
                            "=?" + " ORDER BY " + Const.REMINDS_ID + " DESC";
        }


        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, note.getUsername());

            if (mode == 3){
                prSt.setString(1, note.getUsername());
                prSt.setString(2, text);

                System.out.println("Text:" + text);
            }
            if (mode == 2){
                prSt.setString(1, note.getUsername());
                prSt.setString(2, String.valueOf(por_num));

                System.out.println("Text:" + note.getText());
            }
            if (mode == 1){
                prSt.setString(1, text);
                prSt.setString(2, note.getUsername());
                prSt.setString(3, String.valueOf(por_num));

                System.out.println("Text:" + note.getText());
            }

            if (mode == 3) {
                resSet = prSt.executeQuery();
            }
            if (mode == 2){
                prSt.executeUpdate();
            }
            if (mode == 1){
                prSt.executeUpdate();
            }
            if (mode == 0){
                resSet = prSt.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resSet;
    }
}
