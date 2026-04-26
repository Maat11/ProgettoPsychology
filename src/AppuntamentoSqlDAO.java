import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

public class AppuntamentoSqlDAO implements AppuntamentoDAO{
	
	private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
	private static final String USER = "postgres";
	private static final String PASSWORD = "Informatica1";

	@Override
	public boolean inserisci(Appuntamento app) throws PersonalException {
		String sql = "INSERT INTO prgzia.Appuntamento(id_paziente, data_giorno, ora_Inizio, ora_fine, modalità) "
				+ "VALUES(?, ?, ?, ?, ?) ";
		
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); 
    			PreparedStatement psmt = conn.prepareStatement(sql)) {
			
			//CAST ORA INIZIO:
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime oraIn = LocalTime.parse(oraInizio, formatter);
            Time sqlTimeIn = Time.valueOf(oraIn);
            
            //CAST ORA FINE:
            LocalTime oraFin = LocalTime.parse(oraFine, formatter);
            Time sqlTimeFin = Time.valueOf(oraFin);
            
            long minutiDifferenza = java.time.Duration.between(oraIn, oraFin).toMinutes();
			
//            if(minutiDifferenza >= 60) {
//                psmt.setInt(1, idPaz);
//                psmt.setDate(2, data);
//                psmt.setTime(3, sqlTimeIn);
//                psmt.setTime(4, sqlTimeFin);
//                psmt.setString(5, mod);
                
            int result = psmt.executeUpdate();
            
            return result > 0;
//            }else {
//            	JOptionPane.showMessageDialog(null, "L'ora di inizio e l'ora di fine di una seduta devono distare almeno di un'ora (60 min) tra loro");
//            	return false;
//            }
    	}catch(PersonalException e) {
    		return false;
    	} 		
	}

}
