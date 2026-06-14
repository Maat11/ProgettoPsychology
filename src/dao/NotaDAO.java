package dao;
import javax.swing.table.DefaultTableModel;

import dto.Nota;
import exception.PersonalException;

public interface NotaDAO {
	
	boolean inserisciNotaRapida(Nota notaRapida) throws PersonalException;
	
	boolean inserisciNota(Nota nota) throws PersonalException;
	
	void popolaTabellaConTutteLeNote(DefaultTableModel model) throws PersonalException;
	
	void popolaTabellaNotePerPaziente(int idPaz, DefaultTableModel model) throws PersonalException;
	
	Nota prendiNota(int idNota) throws PersonalException;
	
	boolean modifica(Nota nota) throws PersonalException;
	
	boolean elimina(int idNota) throws PersonalException;
	
}
