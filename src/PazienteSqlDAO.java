import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PazienteSqlDAO implements PazienteDAO{
	
	private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
	private static final String USER = "postgres";
	private static final String PASSWORD = "Informatica1";

	
//METHODS:
	
	//SERVE PER L'INSRIMENTO DEL PAZIENTE NEL DB:
	@Override
	public boolean inserisci(Paziente p) throws PersonalException {
		String sql = "INSERT INTO prgzia.Paziente(nome, cognome, codice_fiscale, data_nascita, telefono, prezzo) "
				+ "VALUES(?, ?, ?, ?, ?, ?) ";
		
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); 
    			PreparedStatement psmt = conn.prepareStatement(sql)) {
			
                psmt.setString(1, upperCaseFirstChar(p.getNome()));
                psmt.setString(2, upperCaseFirstChar(p.getCognome()));
                psmt.setString(3, p.getCodiceFsicale().toUpperCase());
                psmt.setDate(4, p.getDataNascita());
                psmt.setString(5, p.getTelefono());
                psmt.setDouble(6, p.getPrezzo());
                
            int fine = psmt.executeUpdate();
            
            return fine > 0;
    	} catch (SQLException e) {
    		throw new PersonalException("Impossibile salvare il paziente a causa di un errore tecnico.");
		}    
	}
	
	//SERVE A POPOLARE LA TABELLA CON TUTTI I DATI DEL PAZIENTE:
	@Override
	public void popolaTabella(DefaultTableModel model, String cognome) throws PersonalException {
		if(cognome.isBlank()) {
			popolaTabellaFirstMode(model);
		}else {
			popolaTabellaSecondMode(model, cognome);
		}
	}
	
	private void popolaTabellaFirstMode(DefaultTableModel model) {
		
	}
	
	private void popolaTabellaSecondMode(DefaultTableModel model, String cognome) {
		
	}
	
	//SERVE PER RENDERE LA PRIMA LETTERA MAIUSCOLA:
	private String upperCaseFirstChar(String str) {
		//String DA MODIFICARE:
		String primaCharStr = str.substring(0, 1);
		
		//AGGIUNGI IL RESTO DEL NOME:
		String restNome = str.substring(1);
		
		//INSERISCI NELLA VARIBILE:
		return str = primaCharStr.toUpperCase()+restNome;
	}

}
