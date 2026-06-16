package dao.postgresql;

import exception.PersonalException;
import util.CryptoUtils;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import javax.swing.JOptionPane;

import dao.IvDAO;
import dto.Iv;

public class IvSqlDAO implements IvDAO{
	
	//ARRAY DI BYTE RANDOM:
	public byte[] getArrayRandom() {
		byte[] iv = new byte[12];
		new SecureRandom().nextBytes(iv);
		return iv;
	}

	//MI SERVE PER LA DECRIPTAZIONE:
	public String decrypPrendiIVCodiceFiscale(int idPaz) throws PersonalException {
		String sql = "SELECT * "
				+ "FROM prgzia.Iv AS I "
				+ "JOIN prgzia.Paziente AS P ON I.id_paziente = P.id_paziente "
				+ "WHERE I.id_paziente = ? ";
		
		try (Connection conn = DataBaseConnection.getConnection(); 
    			PreparedStatement psmt = conn.prepareStatement(sql)) {
						
                psmt.setInt(1, idPaz);
                
            ResultSet rs = psmt.executeQuery();
            
            if(rs.next()) {
            	//PRENDI L'IV:
            	String ivString = rs.getString("Iv_codice_fiscale");
            	
            	//DECRIPTA E RESTITUISCI IL CODICE FISCALE DEECRIPTATO:
            	return CryptoUtils.decrypt(rs.getString("codice_fiscale"), ivString);
            }
    	}catch(SQLException e) {
    		throw new PersonalException("Impossibile restituire il codice fiscale del paziente a causa di un errore tecnico.");
    	} 		
		return "";
	}
		
	//MI SERVE PER INSERIRLO NELLA TABELLA IV:
	public boolean inserisciInTabellaIV(Iv iv) throws PersonalException {
		String sql = "INSERT INTO prgzia.Iv(id_paziente, iv_codice_fiscale, iv_telefono) "
				+ "VALUES(?, ?, ?)";
		
		try (Connection conn = DataBaseConnection.getConnection(); 
    			PreparedStatement psmt = conn.prepareStatement(sql)) {
    		
                psmt.setInt(1, iv.getIdPaz());
                psmt.setString(2, iv.getIv_codice_fiscale());
                psmt.setString(3, iv.getIv_telefono());
                
            int fine = psmt.executeUpdate();
            
            return fine > 0;
    	}catch(Exception e) {
    		throw new PersonalException("Impossibile inserire l'iv a causa di un errore tecnico.");
    	}
	}

	//SERVE PER DECRYPTARE IL NUMERO DI TELEFONO:
	@Override
	public String decryptPrendiIvTelefono(int idPaz) throws PersonalException {
		String sql = "SELECT * "
				+ "FROM prgzia.Iv AS I "
				+ "JOIN prgzia.Paziente AS P ON I.id_paziente = P.id_paziente "
				+ "WHERE I.id_paziente = ? ";
		
		try (Connection conn = DataBaseConnection.getConnection(); 
    			PreparedStatement psmt = conn.prepareStatement(sql)) {
						
                psmt.setInt(1, idPaz);
                
            ResultSet rs = psmt.executeQuery();
            
            if(rs.next()) {
            	//PRENDI L'IV:
            	String ivStringTel = rs.getString("iv_telefono");
            	
            	//DECRIPTA E RESTITUISCI IL TELEFONO DEECRIPTATO:
            	return CryptoUtils.decrypt(rs.getString("telefono"), ivStringTel);
            }
    	}catch(SQLException e) {
    		throw new PersonalException("Impossibile restituire il numero di telefono del paziente a causa di un errore tecnico.");
    	} 		
		return "";
	}

	//SERVE PER DECRIPTARE L'EMAIL:
	@Override
	public String decryptPrendiIvEmail(int idPaz) throws PersonalException {
		String sql = "SELECT * "
				+ "FROM prgzia.Iv AS I "
				+ "JOIN prgzia.Paziente AS P ON I.id_paziente = P.id_paziente "
				+ "WHERE I.id_paziente = ? ";
		
		try (Connection conn = DataBaseConnection.getConnection(); 
    			PreparedStatement psmt = conn.prepareStatement(sql)) {
						
                psmt.setInt(1, idPaz);
                
            ResultSet rs = psmt.executeQuery();
            
            if(rs.next()) {
            	//PRENDI L'IV, DECRIPTA E RESTITUISCI L'EMAIL DEECRIPTATA:
            	return CryptoUtils.decrypt(rs.getString("email"), rs.getString("iv_email"));
            }
    	}catch(SQLException e) {
    		throw new PersonalException("Impossibile restituire l'email del paziente a causa di un errore tecnico.");
    	} 				
		return null;
	}

	//SERVE AD AGGIORNARE L'IV DEL TELEFONO/CELLULARE QUANDO SI MODIFICA IL NUMERO:
	@Override
	public boolean aggiornaIVTelefono(int idPaz, String ivTel) throws PersonalException {
		String sql = "UPDATE prgzia.Iv SET iv_telefono = ? WHERE id_paziente = ?";
	    
		try (Connection conn = DataBaseConnection.getConnection();
	         PreparedStatement psmt = conn.prepareStatement(sql)) {

	        psmt.setString(1, ivTel);
	        psmt.setInt(2, idPaz);
	        
	        return psmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        throw new PersonalException("Errore aggiornamento IV: " + e.getMessage());
	    }
	}

	//SERVE AD AGGIORNARE L'IV DELL'EMAIL QUANDO SI MODIFICA IL NUMERO:
	@Override
	public boolean aggiornaIVEmail(int idPaz, String ivEmail) throws PersonalException {
		String sql = "UPDATE prgzia.Iv "
				+ "SET iv_email = ? "
				+ "WHERE id_paziente = ? ";
	    
		try (Connection conn = DataBaseConnection.getConnection();
		         PreparedStatement psmt = conn.prepareStatement(sql)) {
	    	
	        psmt.setString(1, ivEmail);
	        psmt.setInt(2, idPaz);
	        
	        return psmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        throw new PersonalException("Errore aggiornamento IV dell'email: " + e.getMessage());
	    }
	}
	
	
}
