import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public  class NotaRapidaSqlDAO implements NotaRapidaDAO{

	private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
	private static final String USER = "postgres";
	private static final String PASSWORD = "Informatica1";
	
	@Override
	public boolean inserisci(NotaRapida notaRap) throws PersonalException {
		if(notaRap.getParolaChiave() == null || notaRap.getParolaChiave().isBlank()) {
			return inserisciMod1(notaRap);
		}else {
			return inserisciMod2(notaRap);
		}
	}

	//SERVE NEL CASO NON CI FOSSE LA PAROLA CHIAVE NELLA NOTA RAPIDA:
	private boolean inserisciMod1(NotaRapida notaRap) throws PersonalException {
	String sql = "INSERT INTO prgzia.Nota(id_appuntamento, id_paziente, nota) VALUES (?, ?, ?)";
		
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); 
				PreparedStatement psmt = conn.prepareStatement(sql)) {
	        
	            psmt.setInt(1, notaRap.getIdAppuntamento());
	            psmt.setInt(2, notaRap.getIdPaziente());
	            psmt.setString(3, notaRap.getNota());
	            
	        int result = psmt.executeUpdate();
	        
	        return result > 0;
		}catch (SQLException e) {
			throw new PersonalException("Impossibile salvare il paziente a causa di un errore tecnico.");
		}	
	}
	
	//SERVE NEL CASO CI FOSSE LA PAROLA CHIAVE NELLA NOTA RAPIDA:
	private boolean inserisciMod2(NotaRapida notaRap) throws PersonalException {
		String sql = "INSERT INTO prgzia.Nota(id_appuntamento, id_paziente, parola_chiave, nota) VALUES (?, ?, ?, ?)";
		
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); 
				PreparedStatement psmt = conn.prepareStatement(sql)) {
	        
	            psmt.setInt(1, notaRap.getIdAppuntamento());
	            psmt.setInt(2, notaRap.getIdPaziente());
	            psmt.setString(3, notaRap.getParolaChiave());
	            psmt.setString(4, notaRap.getNota());
	            
	        int result = psmt.executeUpdate();
	        
	        return result > 0;
		}catch (SQLException e) {
			throw new PersonalException("Impossibile salvare il paziente a causa di un errore tecnico.");
		}	
	}
	
	
}
