import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

public class AppuntamentoSqlDAO implements AppuntamentoDAO{
	
	private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
	private static final String USER = "postgres";
	private static final String PASSWORD = "Informatica1";

	@Override
	public boolean inserisci(Appuntamento app) throws PersonalException{
		String sql = "INSERT INTO prgzia.Appuntamento(id_paziente, data_giorno, ora_Inizio, ora_fine, modalità) "
				+ "VALUES(?, ?, ?, ?, ?) ";
		
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); 
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
    	}catch(SQLException e) {
    		return false;
    	} 		
	}

}
