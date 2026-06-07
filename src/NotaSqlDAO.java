import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.table.DefaultTableModel;

public  class NotaSqlDAO implements NotaDAO{

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
		
		try (Connection conn = DataBaseConnection.getConnection(); 
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
	
	//SERVE NEL METODO PRINCIPALE, NEL CASO CI FOSSE LA PAROLA CHIAVE NELLA NOTA RAPIDA:
	private boolean inserisciMod2(Nota nota) throws PersonalException {
		String sql = "INSERT INTO prgzia.Nota(id_appuntamento, id_paziente, parola_chiave, nota) VALUES (?, ?, ?, ?)";
		
		try (Connection conn = DataBaseConnection.getConnection(); 
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
	
	//SERVE PER INSERIRE UNA NOTA (NON RAPIDA):
	@Override
	public boolean inserisciNota(Nota nota) throws PersonalException {
		String sql = "INSERT INTO prgzia.Nota(id_paziente, parola_chiave, nota) VALUES (?, ?, ?)";
		
		try (Connection conn = DataBaseConnection.getConnection(); 
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
	
	//SERVE PER POPOLARE LA TABELLA CON TUTTE LE NOTE LIMITE 20 DA QUELLA PIù RECENTE:
	@Override
	public void popolaTabellaConTutteLeNote(DefaultTableModel model) throws PersonalException {
		String sql = "SELECT * "
				+ "FROM prgzia.Nota as N "
				+ "ORDER BY N.data DESC "
				+ "LIMIT 20 ";

		try (Connection conn = DataBaseConnection.getConnection(); 
    			PreparedStatement psmt = conn.prepareStatement(sql)) {

            ResultSet rs = psmt.executeQuery();
           
            //FORMATTER:
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            while(rs.next()) {
            	
				model.addRow(new Object[]{rs.getInt("id_nota"), rs.getInt("id_appuntamento"), rs.getInt("id_paziente"), rs.getString("parola_chiave"), rs.getString("nota"), sdf.format(rs.getDate("data"))});
            }
    	}catch(SQLException e) {
    		throw new PersonalException("Impossibile popolare la tabella con le note a causa di un errore tecnico.");
    	} 
	}
	
	//SERVE PER POPOLARE LA TABELLA CON TUTTE LE NOTE DI UN DETERMINATO PAZIENTE:
	@Override
	public void popolaTabellaNotePerPaziente(int idPaz, DefaultTableModel model) throws PersonalException {
		String sql = "SELECT * "
				+ "FROM prgzia.Nota AS N "
				+ "WHERE N.id_paziente = ?";

		try (Connection conn = DataBaseConnection.getConnection(); 
    			PreparedStatement psmt = conn.prepareStatement(sql)) {

			psmt.setInt(1, idPaz);
                
            ResultSet rs = psmt.executeQuery();

            //FORMATTER:
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            while(rs.next()) {
				model.addRow(new Object[]{rs.getInt("id_nota"), rs.getInt("id_appuntamento"), rs.getInt("id_paziente"), rs.getString("parola_chiave"), rs.getString("nota"), sdf.format(rs.getDate("data"))});
            }
    	}catch(SQLException e) {
    		throw new PersonalException("Impossibile popolare la tabella con le note del paziente selezionato a causa di un errore tecnico.");
    	} 
	}

	//SERVE PER PRENDERE LA NOTA SELEZIONATA E VEDERE I TESTI IN MANIERA COMPLETA:
	@Override
	public Nota prendiNota(int idNota) throws PersonalException {
		String sql = "SELECT * "
				+ "FROM prgzia.Nota AS N "
				+ "WHERE N.id_nota = ? "; 
		
		try (Connection conn = DataBaseConnection.getConnection(); 
    			PreparedStatement psmt = conn.prepareStatement(sql)) {

			psmt.setInt(1, idNota);
                
            ResultSet rs = psmt.executeQuery();

            if(rs.next()) {
				Nota nota = new Nota(rs.getInt("id_paziente"), rs.getString("parola_chiave"), rs.getString("nota"));
				nota.setDate(rs.getDate("data"));
				nota.setIdNota(rs.getInt("id_nota"));
				nota.setIdAppuntamento(rs.getInt("id_appuntamento"));
				return nota;
            }
    	}catch(SQLException e) {
    		throw new PersonalException("Impossibile trovare la nota selezionata a causa di un errore tecnico.");
    	}
		return null; 
	}

	//SERVE PER MODIFICARE LA NOTA:
	@Override
	public boolean modifica(Nota nota) throws PersonalException {
		String sql = "UPDATE prgzia.Nota "
				+ "SET parola_chiave = ?, "
				+ "nota = ? "
				+ "WHERE id_nota = ? ";
		
		try (Connection conn = DataBaseConnection.getConnection(); 
    			PreparedStatement psmt = conn.prepareStatement(sql)) {
    		
                psmt.setString(1,nota.getParolaChiave());
                psmt.setString(2, nota.getNota());
                psmt.setInt(3, nota.getIdNota());
                
            int fine = psmt.executeUpdate();
            
            return fine > 0;
    	}catch(SQLException e) {
    		throw new PersonalException("Impossibile modificare la nota a causa di un errore tecnico.");
    	}
	}

	//SERVE AD ELIMINARE UNA NOTA SELEZIONATA:
	@Override
	public boolean elimina(int idNota) throws PersonalException {
		String sql = "DELETE FROM prgzia.Nota AS N WHERE N.id_nota = ? ";
		
		try (Connection conn = DataBaseConnection.getConnection(); 
    			PreparedStatement psmt = conn.prepareStatement(sql)) {
						
                psmt.setInt(1, idNota);
                
            int result = psmt.executeUpdate();
            
            return result > 0;
    	}catch(SQLException e) {
    		throw new PersonalException("Impossibile eliminare la nota selezionata a causa di un errore tecnico.");
    	}
	}

	
}
