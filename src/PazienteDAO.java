import javax.swing.table.DefaultTableModel;

public interface PazienteDAO {
	
	boolean inserisci(Paziente p) throws PersonalException;
	
	void popolaTabella(DefaultTableModel model, String cognome) throws PersonalException;
	
//	private void popolaTabellaFirstMode(DefaultTableModel model) throws PersonalException;
//	private void popolaTabellaSecondMode(DefaultTableModel model, String cognome) throws PersonalException;
	
	int prendiIdPaziente(String codiceFiscale) throws PersonalException;
	
	boolean elimina(int idPaziente) throws PersonalException;
}
