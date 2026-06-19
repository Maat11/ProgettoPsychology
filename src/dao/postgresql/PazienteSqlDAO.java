package dao.postgresql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Base64;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.IvDAO;
import dao.PazienteDAO;
import dto.Paziente;
import exception.PersonalException;
import properties.DataBaseConnection;

public class PazienteSqlDAO implements PazienteDAO{
	private IvDAO ivDAO = new IvSqlDAO();
//METHODS:
	//SERVE PER L'INSRIMENTO DEL PAZIENTE NEL DB:
	@Override
	public boolean inserisci(Paziente p) throws PersonalException {
		String sql = "INSERT INTO prgzia.Paziente(nome, cognome, codice_fiscale, data_nascita, telefono, prezzo) "
				+ "VALUES(?, ?, ?, ?, ?, ?) ";
		
		try (Connection conn = DataBaseConnection.getConnection(); 
    			PreparedStatement psmt = conn.prepareStatement(sql)) {
			
                psmt.setString(1, upperCaseFirstChar(p.getNome()).trim());
                psmt.setString(2, upperCaseFirstChar(p.getCognome()).trim());
                psmt.setString(3, p.getCodiceFiscale().trim());
                psmt.setDate(4, p.getDataNascita());
                psmt.setString(5, p.getTelefono().trim());
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
	
	private void popolaTabellaFirstMode(DefaultTableModel model) throws PersonalException {
		String sql = "SELECT * "
				+ "FROM prgzia.Paziente AS P "
				+ "ORDER BY cognome ASC ";
		
		try (Connection conn = DataBaseConnection.getConnection(); 
    			PreparedStatement psmt = conn.prepareStatement(sql)) {
            
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
                ResultSet rs = psmt.executeQuery();
                
            while(rs.next()) {
            	String dataNacitaFormattata =  sdf.format(rs.getDate("data_nascita"));
            	String telefonoDecrypt = ivDAO.decryptPrendiIvTelefono(rs.getInt("id_paziente"));
            	String codiceFiscaleDecriptato = ivDAO.decrypPrendiIVCodiceFiscale(rs.getInt("id_paziente"));
            	
            	String emailDecrypt = "";
	            
	            if(rs.getString("email") != null) {
	            	emailDecrypt = ivDAO.decryptPrendiIvEmail(rs.getInt("id_paziente"));
	            }
            	
            	model.addRow(new Object[]{rs.getInt("id_paziente"), rs.getString("Nome"), rs.getString("Cognome"), codiceFiscaleDecriptato, dataNacitaFormattata, telefonoDecrypt, emailDecrypt,  rs.getDouble("prezzo"), rs.getDouble("credito")});
            }
    	}catch(SQLException e) {
    		throw new PersonalException("Impossibile popolare la tabella con i pazienti a causa di un errore tecnico.");
    	}	
	}
	
	private void popolaTabellaSecondMode(DefaultTableModel model, String cognome) throws PersonalException {
	    //USA LIKE PER TROVARE COGNOME CHE INIZIANO CON LA STRINGA INSERITA:
	    String sql = "SELECT * FROM prgzia.Paziente WHERE cognome LIKE ? ORDER BY Nome ASC";

	    try (Connection conn = DataBaseConnection.getConnection();
	         PreparedStatement psmt = conn.prepareStatement(sql)) {

	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	        //AGGIUNGI IL WILDCARD % PER CERCARE COGNOMI CHE INZIANO CON LA STRINGA INSERITA:
	        psmt.setString(1, cognome + "%");

	        ResultSet rs = psmt.executeQuery();

	        while (rs.next()) {
	        	//FORMATTER:
	            String dataNascitaFormattata = sdf.format(rs.getDate("data_nascita"));
	            
	            //DECRYPT:
	            String codiceFiscaleDecryptato = ivDAO.decrypPrendiIVCodiceFiscale(rs.getInt("id_paziente"));
	            String telefonoDecrypt = ivDAO.decryptPrendiIvTelefono(rs.getInt("id_paziente"));
	            String emailDecrypt = "";
	            
	            if(rs.getString("email") != null) {
	            	emailDecrypt = ivDAO.decryptPrendiIvEmail(rs.getInt("id_paziente"));
	            }
	            
	            model.addRow(new Object[]{rs.getInt("id_paziente"), rs.getString("Nome"), rs.getString("Cognome"), codiceFiscaleDecryptato, dataNascitaFormattata, telefonoDecrypt, emailDecrypt, rs.getDouble("prezzo"), rs.getDouble("credito")});
	        }
	    } catch (SQLException e) {
	        throw new PersonalException("Impossibile popolare la tabella con i pazienti a causa di un errore tecnico.");
	    }
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
		
		try (Connection conn = DataBaseConnection.getConnection(); 
    			PreparedStatement psmt = conn.prepareStatement(sql)) {
            
				psmt.setString(1, codFiscCript);
				
                ResultSet rs = psmt.executeQuery();
                
            if(rs.next()) {
            	return rs.getInt("id_paziente");
            }
    	}catch(SQLException e) {
    		throw new PersonalException("Impossibile trovare il paziente a causa di un errore tecnico.");
    	}
		return 0;
	}

	@Override
	public boolean elimina(int idPaziente) throws PersonalException {
		String sql = "DELETE "
				+ "FROM prgzia.Paziente AS P "
				+ "WHERE P.id_paziente = ? ";
		
		try (Connection conn = DataBaseConnection.getConnection(); 
    			PreparedStatement psmt = conn.prepareStatement(sql)) {
    		
                psmt.setInt(1, idPaziente);
                
            int fine = psmt.executeUpdate();
            return fine > 0;
    	}catch(Exception e) {
    		throw new PersonalException("Impossibile eliminare il paziente a causa di un errore tecnico.");
    	}		
	}
	
	//SERVE A TROVARE UN PAZIENTE TRAMITE id:
	@Override
	public Paziente trova(int idPaziente) throws PersonalException {
		String sql = "SELECT * "
				+ "FROM prgzia.Paziente AS P "
				+ "WHERE P.id_paziente = ? "; 
		
		try (Connection conn = DataBaseConnection.getConnection(); 
    			PreparedStatement psmt = conn.prepareStatement(sql)) {
            
				psmt.setInt(1, idPaziente);
				
                ResultSet rs = psmt.executeQuery();
                
            if(rs.next()) {
            	Paziente paziente = new Paziente(rs.getString("nome"), rs.getString("cognome"), rs.getString("codice_fiscale"), rs.getDate("data_nascita"), ivDAO.decryptPrendiIvTelefono(rs.getInt("id_paziente")), rs.getDouble("prezzo"));
            	paziente.setId(Integer.valueOf(rs.getInt("id_paziente")));
            	if(rs.getString("email") != null) {
            		paziente.setEmail(ivDAO.decryptPrendiIvEmail(rs.getInt("id_paziente")));
            	}else {
            		paziente.setEmail(rs.getString("email"));
            	}
            	return paziente;
            }
    	}catch(SQLException e) {
    		throw new PersonalException("Impossibile trovare il paziente a causa di un errore tecnico.");
    	}
		return null;
	}

	//SERVE PER LA MODIFICA DI UN PAZIENTE:
	@Override
	public boolean modificaDatiNonSensibili(Paziente p) throws PersonalException {
		String sql = "UPDATE prgzia.Paziente "
				+ "SET Nome = ?, "
				+ "Cognome = ?, "
				+ "data_nascita = ?, "
				+ "prezzo = ? "
				+ "WHERE id_paziente = ? ";
		
		try (Connection conn = DataBaseConnection.getConnection(); 
    			PreparedStatement psmt = conn.prepareStatement(sql)) {
    		
                psmt.setString(1, upperCaseFirstChar(p.getNome()).trim());
                psmt.setString(2, upperCaseFirstChar(p.getCognome().trim()));
                psmt.setDate(3, p.getDataNascita());
                psmt.setDouble(4, p.getPrezzo());
                psmt.setInt(5, p.getId());
                
            int fine = psmt.executeUpdate();
            
            return fine > 0;
    	}catch(SQLException e) {
    		throw new PersonalException("Impossibile modificare i dati del paziente a causa di un errore tecnico.");
    	}   
	}

	//SERVE PER AGGIORNARE IL NUMERO DI TELEFONO/CELLULARE:
	@Override
	public boolean aggiornaTelefono(int idPaz, String telefonoCrittografato) throws PersonalException {
		String sql = "UPDATE prgzia.Paziente SET telefono = ? WHERE id_paziente = ?";
		
	    try (Connection conn = DataBaseConnection.getConnection();
	         PreparedStatement psmt = conn.prepareStatement(sql)) {

	        psmt.setString(1, telefonoCrittografato);
	        psmt.setInt(2, idPaz);
	        
	        return psmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        throw new PersonalException("Errore aggiornamento telefono: " + e.getMessage());
	    }
	}
	
	//SERVE PER AGGIORNARE L'EMAIL:
	@Override
	public boolean aggiornaEmail(int idPaz, String emailCrittografata) throws PersonalException{
		String sql = "UPDATE prgzia.Paziente "
				+ "SET email = ? "
				+ "WHERE id_paziente = ? ";
		
		try (Connection conn = DataBaseConnection.getConnection();
		         PreparedStatement psmt = conn.prepareStatement(sql)) {

	        psmt.setString(1, emailCrittografata);
	        psmt.setInt(2, idPaz);
	        
	        return psmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        throw new PersonalException("Errore aggiornamento dell'email: " + e.getMessage());
	    }
	}
	
}
