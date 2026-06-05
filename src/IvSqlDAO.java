import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class IvSqlDAO implements IvDAO{

	//MI SERVE PER LA DECRIPTAZIONE:
	public String decrypPrendiIV(int idPaz) {
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
            	String ivString = rs.getString("Iv");
            	
            	//DECRIPTA E RESTITUISCI IL CODICE FISCALE DEECRIPTATO:
            	return CryptoUtilsDAO.decrypt(rs.getString("codice_fiscale"), ivString);
            }
    	}catch(Exception e) {
    		JOptionPane.showMessageDialog(null, "Errore nella funzione: decryptPrendiIv nella classe CryptoUtilsDAO" + e);
    	} 		
		return "";
	}
		
	//MI SERVE PER INSERIRLO NELLA TABELLA IV:
	public boolean inserisciInTabellaIV(int idPaz, String iv) throws PersonalException {
		String sql = "INSERT INTO prgzia.Iv(id_paziente, Iv) "
				+ "VALUES(?, ?)";
		
		try (Connection conn = DataBaseConnection.getConnection(); 
    			PreparedStatement psmt = conn.prepareStatement(sql)) {
    		
                psmt.setInt(1, idPaz);
                psmt.setString(2, iv);
                
            int fine = psmt.executeUpdate();
            
            return fine > 0;
    	}catch(Exception e) {
    		throw new PersonalException("Impossibile inserire l'iv paziente a causa di un errore tecnico.");
    	}
	}
	
}
