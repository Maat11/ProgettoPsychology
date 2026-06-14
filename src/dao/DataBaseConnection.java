package dao;
import java.sql.Connection; // <-- QUESTO È L'IMPORT CORRETTO PER I DATABASE!
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
	
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Informatica1";
	
    // COSTRUTTORE PRIVATO PER EVITARE L'INIZIALIZZAZIONE:
    private DataBaseConnection() {
    	
    }
	
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}