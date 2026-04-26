
public class AppuntamentoSqlDAO implements AppuntamentoDAO{

	@Override
	public boolean inserisci(Appuntamento app) throws PersonalException {
		String sql = "INSERT INTO prgzia.Appuntamento(id_paziente, data_giorno, ora_Inizio, ora_fine, modalità) "
				+ "VALUES(?, ?, ?, ?, ?) ";
		
		return false;
	}

}
