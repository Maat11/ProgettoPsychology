import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
                psmt.setString(3, p.getCodiceFsicale().trim());
                psmt.setDate(4, p.getDataNascita());
                psmt.setString(5, p.getTelefono());
                psmt.setDouble(6, p.getPrezzo());
                
            int fine = psmt.executeUpdate();
            
            return fine > 0;
    	} catch (SQLException e) {
    		throw new PersonalException("Impossibile salvare il paziente a causa di un errore tecnico.");
		}    
	}
	
	//SERVE A POPOLARE LA TABELLA CON TUTTI I DATI DEL PAZIENTE: INCOMPLETE
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

	//MI SERVE PER PRENDERE L'ID DEL PAZIENTE TRAMITE IL CODICE FISCALE CRIPTATO:
	@Override
	public int prendiIdPaziente(String codFiscCript) throws PersonalException {
		String sql = "SELECT * "
				+ "FROM prgzia.Paziente "
				+ "WHERE codice_fiscale = ? ";
		
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); 
    			PreparedStatement psmt = conn.prepareStatement(sql)) {
            
				psmt.setString(1, codFiscCript);
				
                ResultSet rs = psmt.executeQuery();
                
            if(rs.next()) {
            	System.out.println("Questo è l'id del paziente, preso dalla funzione prendiID"+ rs.getInt("id_paziente"));
            	return rs.getInt("id_paziente");
            }
    	}catch(SQLException e) {
    		throw new PersonalException("Impossibile trovare il paziente a causa di un errore tecnico.");
    	}
		System.out.println("Sto dopo il try catch nella funzione per trovare l'id del pazienteeee");
		return 0;
	}

	
}
