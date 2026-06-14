package dao;
import javax.swing.table.DefaultTableModel;

import dto.Appuntamento;
import exception.PersonalException;

public interface AppuntamentoDAO {

	boolean inserisci(Appuntamento app) throws PersonalException;
	
	boolean elimina(int idApp) throws PersonalException;
	
	void popolaTabellaConData(java.sql.Date data, DefaultTableModel model) throws PersonalException;
}
