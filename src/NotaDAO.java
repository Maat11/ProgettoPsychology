
public interface NotaDAO {
	
	boolean inserisciNotaRapida(Nota notaRapida) throws PersonalException;
	
	boolean inserisciNota(Nota nota) throws PersonalException;
	
	void popola(Nota nota) throws PersonalException;
	
}
