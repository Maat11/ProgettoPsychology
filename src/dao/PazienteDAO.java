package dao;
import java.sql.Connection;

import javax.swing.table.DefaultTableModel;

import dto.Paziente;
import exception.PersonalException;

public interface PazienteDAO {
	
	boolean inserisci(Paziente p) throws PersonalException;
	
	void popolaTabella(DefaultTableModel model, String cognome) throws PersonalException;
	
	int prendiIdPaziente(String codiceFiscale) throws PersonalException;
	
	boolean elimina(int idPaziente) throws PersonalException;
	
	Paziente trova(int idPaziente) throws PersonalException;
	
	boolean modificaDatiNonSensibili(Paziente paziente) throws PersonalException;
	
	boolean aggiornaTelefono(int idPaz, String telefonoCrittografato) throws PersonalException;
	
// 	inserisciEmail(int idPaz, String emailCrittografata) throws PersonalException;
//	
	boolean aggiornaEmail(int idPaz, String emailCrittografata) throws PersonalException;
//	
//	ctrlEmailEsiste(int idPaz) throws PersonalException;
	
//	boolean inserisciOModificaEmail(int idPaz, String emailCrittografata) throws PersonalException;

	
}
