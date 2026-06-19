package properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection; // <-- QUESTO È L'IMPORT CORRETTO PER I DATABASE!
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {
	
	private static Properties prop = new Properties();
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
    public static void caricareConfigurazione() {
        try {
            // 1. Prova prima a cercare il file NELLA STESSA CARTELLA DEL JAR (esterno)
            File fileEsterno = new File("db.properties");
            InputStream input;
            
            if (fileEsterno.exists()) {
                input = new FileInputStream(fileEsterno);
//                System.out.println("Configurazione caricata dal file esterno.");
            } else {
                // 2. Se non c'è, usa quello dentro il JAR (interno al package)
                input = DataBaseConnection.class.getClassLoader().getResourceAsStream("properties/db.properties");
//                System.out.println("Configurazione caricata dall'interno del JAR.");
            }

            if (input == null) {
                throw new FileNotFoundException("Impossibile trovare db.properties da nessuna parte.");
            }

            prop.load(input);
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Errore nel caricamento della configurazione", e);
        }
    }

	
    public static Connection getConnection() throws SQLException {
    	return DriverManager.getConnection(
    	        prop.getProperty("db.url"), 
    	        prop.getProperty("db.user"), 
    	        prop.getProperty("db.password")
    	    );
    }
}