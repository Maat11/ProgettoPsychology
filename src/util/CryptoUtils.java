package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

import com.sun.tools.javac.Main;

public class CryptoUtils {
	
	public static String encrypt(String str, byte[] iv) {
	    try {
	        if (getKey() == null || getKey().length() != 44) {
	           throw new Exception ("la chiave deve essere di 32 caratteri!");
	        }
	        
	        byte[] chiaveBytes = Base64.getDecoder().decode(getKey());
	        
	        // 2. Setup Chiave (usando la tua stringa da 32 direttamente)
	        SecretKeySpec keySpec = new SecretKeySpec(chiaveBytes, "AES");
	        Cipher scatola = Cipher.getInstance("AES/GCM/NoPadding");
	        GCMParameterSpec parametri = new GCMParameterSpec(128, iv);
	        
	        scatola.init(Cipher.ENCRYPT_MODE, keySpec, parametri);
	        byte[] testoCriptatoInByte = scatola.doFinal(str.getBytes(StandardCharsets.UTF_8));
	        return Base64.getEncoder().encodeToString(testoCriptatoInByte);
	      
	    } catch(Exception xxx) {
	        xxx.printStackTrace();
	        return "";
	    }
	}
	
	//MI SERVE PER LA DECRIPTAZIONE:
	public static String decrypt(String str, String ivString) {
		try {
	        // 1. Decodifica l'IV, la chiave e il codice fiscale crittografato da Base64
	        byte[] iv = Base64.getDecoder().decode(ivString);
	        byte[] chiaveByt = Base64.getDecoder().decode(getKey()); // Usa questi byte per la chiave
	        byte[] strCriptBytes = Base64.getDecoder().decode(str);
	        
	        // 2. Crea la SecretKeySpec usando i byte decodificati della chiave
	        SecretKeySpec keySpec = new SecretKeySpec(chiaveByt, "AES"); // Usa chiaveByt

	        // 3. Configura i parametri GCM
	        GCMParameterSpec parametri = new GCMParameterSpec(128, iv);
	        
	        // 4. Inizializza il Cipher per la decrittografia
	        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
	        cipher.init(Cipher.DECRYPT_MODE, keySpec, parametri);

	        // 5. Esegui la decrittografia
	        byte[] datoDecrittografato = cipher.doFinal(strCriptBytes);
	        
	        // 6. Restituisci il risultato come stringa
	        return new String(datoDecrittografato, StandardCharsets.UTF_8);

	    } catch (Exception x) {
	        JOptionPane.showMessageDialog(null, "Errore nella decrittografia: " + x.getMessage());
	        return "";
	    }
	}
	
	//MI SERVE PER LA CHIAVE SEGRETA PER LA CRIPTAZIONE E DECRIPTAZIONE DEI DATI SENSIBILI:
	private static String getKey() {
	    Properties prop = new Properties();
	    String masterKey = "";
	    InputStream input = null;

	    try {
	        // 1. Cerca prima il file esterno (nella stessa cartella del JAR)
	        File fileEsterno = new File("config.properties");
	        
	        if (fileEsterno.exists()) {
	            input = new FileInputStream(fileEsterno);
//	            System.out.println("Chiave di crittografia caricata dal file esterno.");
	        } else {
	            // 2. Se non c'è all'esterno, lo cerca nel package 'properties' dentro il JAR
	            input = Main.class.getClassLoader().getResourceAsStream("properties/config.properties");
//	            System.out.println("Chiave di crittografia caricata dall'interno del JAR.");
	        }

	        // Se non viene trovato da nessuna parte
	        if (input == null) {
	            JOptionPane.showMessageDialog(null, "Spiacente, non trovo 'config.properties' da nessuna parte.");
	            return ""; 
	        }

	        // 3. Carica i dati dal file
	        prop.load(input);
	        input.close(); // Buona norma chiudere lo stream dopo il load

	        // Estrae la chiave usando il nome esatto presente nel file (nel tuo codice cerchi "chiave")
	        masterKey = prop.getProperty("chiave");

	        if (masterKey != null) {
	            masterKey = masterKey.trim();
	            
	            // Verifica rapida della compatibilità AES (44 caratteri per Base64)
	            if (masterKey.length() == 44) {
	                return masterKey;
	            } else {
	                JOptionPane.showMessageDialog(null, "Attenzione: Lunghezza non standard per AES (attesi 44 caratteri)!");
	                return "";
	            }
	        } else {
	            // Nota: nel tuo messaggio parlavi di 'secret.key=', ma nel getProperty cerchi 'chiave'. 
	            // Ho corretto il messaggio per evitare confusione.
	            JOptionPane.showMessageDialog(null, "Il file esiste ma la riga 'chiave=' è vuota o scritta male.");
	            return "";
	        }

	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(null, "Si è verificato un errore durante la lettura: " + ex.getMessage());
	        return "";
	    }
	}
	
}
