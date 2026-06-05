
public interface IvDAO {
	
	public String decrypPrendiIV(int idPaz) throws PersonalException;
	
	public boolean inserisciInTabellaIV(int idPaz, String iv) throws PersonalException;
	
}
