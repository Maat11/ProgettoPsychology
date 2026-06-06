import javax.swing.table.DefaultTableModel;

public interface NotaDAO {
	
	boolean inserisciNotaRapida(Nota notaRapida) throws PersonalException;
	
	boolean inserisciNota(Nota nota) throws PersonalException;
	
	void popola(int idPaz, DefaultTableModel model) throws PersonalException;
	
}
