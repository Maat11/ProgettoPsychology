import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public  class NotaSqlDAO implements NotaDAO{

	private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
	private static final String USER = "postgres";
	private static final String PASSWORD = "Informatica1";
	
	@Override
	public boolean inserisciNotaRapida(Nota nota) throws PersonalException {
		if(nota.getParolaChiave() == null || nota.getParolaChiave().isBlank()) {
			return inserisciMod1(nota);
		}else {
			return inserisciMod2(nota);
		}
	}

	//SERVE NEL CASO NON CI FOSSE LA PAROLA CHIAVE NELLA NOTA RAPIDA:
	private boolean inserisciMod1(Nota notaRap) throws PersonalException {
	String sql = "INSERT INTO prgzia.Nota(id_appuntamento, id_paziente, nota) VALUES (?, ?, ?)";
		
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); 
				PreparedStatement psmt = conn.prepareStatement(sql)) {
	        
	            psmt.setInt(1, notaRap.getIdAppuntamento());
	            psmt.setInt(2, notaRap.getIdPaziente());
	            psmt.setString(3, notaRap.getNota());
	            
	        int result = psmt.executeUpdate();
	        
	        return result > 0;
		}catch (SQLException e) {
			throw new PersonalException("Impossibile inserire una nota rapida al paziente selezionato a causa di un errore tecnico.");
		}	
	}
	
	//SERVE NEL CASO CI FOSSE LA PAROLA CHIAVE NELLA NOTA RAPIDA:
	private boolean inserisciMod2(Nota nota) throws PersonalException {
		String sql = "INSERT INTO prgzia.Nota(id_appuntamento, id_paziente, parola_chiave, nota) VALUES (?, ?, ?, ?)";
		
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); 
				PreparedStatement psmt = conn.prepareStatement(sql)) {
	        
	            psmt.setInt(1, nota.getIdAppuntamento());
	            psmt.setInt(2, nota.getIdPaziente());
	            psmt.setString(3, nota.getParolaChiave());
	            psmt.setString(4, nota.getNota());
	            
	        int result = psmt.executeUpdate();
	        
	        return result > 0;
		}catch (SQLException e) {
			throw new PersonalException("Impossibile inserire una nota rapida al paziente selezionato a causa di un errore tecnico.");
		}	
	}

	@Override
	public boolean inserisciNota(Nota nota) throws PersonalException {
		String sql = "INSERT INTO prgzia.Nota(id_paziente, parola_chiave, nota) VALUES (?, ?, ?)";
		
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); 
				PreparedStatement psmt = conn.prepareStatement(sql)) {
	        
	            psmt.setInt(1, nota.getIdPaziente());
	            psmt.setString(2, nota.getParolaChiave());
	            psmt.setString(3, nota.getNota());
	            
	        int result = psmt.executeUpdate();
	        
	        return result > 0;
		}catch (SQLException e) {
			throw new PersonalException("Impossibile inserire una nota al paziente selezionato a causa di un errore tecnico.");
		}
	}

	
}
