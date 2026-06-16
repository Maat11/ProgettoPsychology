package util;
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
	            System.err.println("ERRORE: La chiave deve essere di 32 caratteri!");
	            return "";
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
		// 1. Definiamo l'oggetto Properties e la stringa per la chiave
	    Properties prop = new Properties();
	    String masterKey = "";

	    // 2. Cerchiamo di caricare il file direttamente
	    try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
	        
	        if (input == null) {
	            System.out.println("Spiacente, non trovo 'config.properties' nella cartella src.");
	            return ""; // Esce dal programma se il file manca
	        }

	        // Carica i dati dal file
	        prop.load(input);

	        // Estrae la chiave e pulisce eventuali spazi extra
	        masterKey = prop.getProperty("chiave");

	        if (masterKey != null) {
	            masterKey = masterKey.trim();
	            
	            // Verifica rapida della compatibilità AES
	            if (masterKey.length() == 44) {
	                return masterKey;
	            } else {
	                JOptionPane.showMessageDialog(null, "Attenzione: Lunghezza non standard per AES!");
	                return "";
	            }
	        } else {
	        	JOptionPane.showMessageDialog(null, "Il file esiste ma la riga 'secret.key=' è vuota o scritta male.");
	            return "";
	        }

	    } catch (Exception ex) {
	    	JOptionPane.showMessageDialog(null, "Si è verificato un errore durante la lettura:"+ex);
	    	return "";
	    }
	}
	
}
