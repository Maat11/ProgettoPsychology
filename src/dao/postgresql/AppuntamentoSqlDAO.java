package dao.postgresql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.AppuntamentoDAO;
import dao.IvDAO;
import dto.Appuntamento;
import exception.PersonalException;

public class AppuntamentoSqlDAO implements AppuntamentoDAO{
	private IvDAO ivDAO = new IvSqlDAO();
	
	//MI SERVE PER INSERIRE UN APPUNTAMENTO:
	@Override
	public boolean inserisci(Appuntamento app) throws PersonalException{
		String sql = "INSERT INTO prgzia.Appuntamento(id_paziente, data_giorno, ora_Inizio, ora_fine, modalità) "
				+ "VALUES(?, ?, ?, ?, ?) ";
		
		try (Connection conn = DataBaseConnection.getConnection(); 
    			PreparedStatement psmt = conn.prepareStatement(sql)) {
			
			//CAST ORA INIZIO:
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime oraIn = LocalTime.parse(app.getOraInizio(), formatter);
            Time sqlTimeIn = Time.valueOf(oraIn);
            
            //CAST ORA FINE:
            LocalTime oraFin = LocalTime.parse(app.getOraFine(), formatter);
            Time sqlTimeFin = Time.valueOf(oraFin);
            
                psmt.setInt(1, app.getIdPaz());
                psmt.setDate(2, app.getDataGiorno());
                psmt.setTime(3, sqlTimeIn);
                psmt.setTime(4, sqlTimeFin);
                psmt.setString(5, app.getModalità());
                
            int result = psmt.executeUpdate();
            
            return result > 0;
    	}catch (SQLException e) {
    		throw new PersonalException("Impossibile salvare il paziente a causa di un errore tecnico.");
		}		
	}
	
	//MI SERVE PER ELIMINARE UN APPUNTAMENTO:
	@Override
	public boolean elimina(int idApp) throws PersonalException {
		String sql = "DELETE FROM prgzia.Appuntamento "
				+ "WHERE id_appuntamento = ?";
		
		try (Connection conn = DataBaseConnection.getConnection(); 
    			PreparedStatement psmt = conn.prepareStatement(sql)) {
						
                psmt.setInt(1, idApp);
                
            int result = psmt.executeUpdate();
            
            return result > 0;
    	}catch(SQLException e) {
    		throw new PersonalException("Impossibile eliminare l'appuntamento selezionato a causa di un errore tecnico.");
    	}
	}

	//MI SERVE PER POPOLARE LA TABELLA CON GLI APPUNTAMENTI ED I PAZIENTI ASSOCIATI:
	@Override
	public void popolaTabellaConData(java.sql.Date data, DefaultTableModel model) throws PersonalException {
		String sql = "SELECT * "
				+ "FROM prgzia.Appuntamento AS A "
				+ "JOIN prgzia.Paziente AS P ON A.id_paziente = P.id_paziente "
				+ "WHERE A.data_giorno = ? "
				+ "ORDER BY ora_inizio ASC ";
		
		try (Connection conn = DataBaseConnection.getConnection(); 
    			PreparedStatement psmt = conn.prepareStatement(sql)) {
						
                psmt.setDate(1, data);
                
            ResultSet rs = psmt.executeQuery();
            
            //FORMATTER:
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdfOra = new SimpleDateFormat("HH:mm");
            
            while(rs.next()) {
            	String telefonoDecrypt = ivDAO.decryptPrendiIvTelefono(rs.getInt("id_paziente"));
				model.addRow(new Object[]{rs.getInt("id_appuntamento"), sdf.format(rs.getDate("data_giorno")), sdfOra.format(rs.getTime("ora_inizio")), sdfOra.format(rs.getTime("ora_fine")), rs.getInt("id_paziente"), rs.getString("Nome"), rs.getString("Cognome"), telefonoDecrypt, rs.getString("modalità"), rs.getString("pagato")});
            }
    	}catch(SQLException e) {
    		throw new PersonalException("Impossibile popolare la tabella a causa di un errore tecnico.");
    	} 
	}

	
}
