package sample;

import java.sql.*;

public class ConnexionMySQL {
    String dbName = "gestiondesetudiants";
    String userName = "root";
    String password = "1995";
    PreparedStatement prepared;
    ResultSet resultat;

    public void setPrepared(PreparedStatement prepared) {
        this.prepared = prepared;
    }

    public static Connection cnx(){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/gestiondesetudiants?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "1995");
            System.out.println("Done");
            return conn;
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("error"+e);
            return null;
        }
    }
}
