
public class AppuntamentoSqlDAO implements AppuntamentoDAO{
	
	private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
	private static final String USER = "postgres";
	private static final String PASSWORD = "Informatica1";

	@Override
	public boolean inserisci(Appuntamento app) throws PersonalException {
		String sql = "INSERT INTO prgzia.Appuntamento(id_paziente, data_giorno, ora_Inizio, ora_fine, modalità) "
				+ "VALUES(?, ?, ?, ?, ?) ";
		
		return false;
	}

}
