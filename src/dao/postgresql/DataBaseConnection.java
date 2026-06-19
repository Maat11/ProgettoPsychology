package dao.postgresql;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection; // <-- QUESTO È L'IMPORT CORRETTO PER I DATABASE!
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {
	
	 private static String URL;
	    private static String USER;
	    private static String PASSWORD;

	    // Blocco statico per caricare la configurazione all'avvio della classe
	    static {
	        caricareConfigurazione();
	    }

	    // Costruttore privato per impedire l'istanziazione
	    private DataBaseConnection() {
	    	
	    }

	    // Metodo per caricare la configurazione dal file
	    private static void caricareConfigurazione() {
	        Properties props = new Properties();
	        try (FileInputStream input = new FileInputStream("config.properties")) {
	            props.load(input);
	            URL = props.getProperty("db.url");
	            USER = props.getProperty("db.user");
	            PASSWORD = props.getProperty("db.password");

	            // Validazione: se manca un valore, lancia un'eccezione
	            if (URL == null || USER == null || PASSWORD == null) {
	                throw new IllegalStateException("Configurazione del database incompleta in config.properties");
	            }
	        } catch (IOException e) {
	            throw new IllegalStateException("Impossibile caricare config.properties: " + e.getMessage(), e);
	        }
	    }

	
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}