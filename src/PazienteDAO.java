import java.sql.Connection;

import javax.swing.table.DefaultTableModel;

public interface PazienteDAO {
	
	boolean inserisci(Paziente p) throws PersonalException;
	
	void popolaTabella(DefaultTableModel model, String cognome) throws PersonalException;
	
	int prendiIdPaziente(String codiceFiscale) throws PersonalException;
	
	boolean elimina(int idPaziente) throws PersonalException;
	
	Paziente trova(int idPaziente) throws PersonalException;
	
	boolean modificaDatiNonSensibili(Paziente paziente) throws PersonalException;
	
	boolean aggiornaTelefono(int idPaz, String telefonoCrittografatoù) throws PersonalException;
	
	boolean insertOrAggiornaEmail(int idPaz, String emailCrittografata, Connection conn) throws PersonalException;
}
