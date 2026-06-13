
public interface IvDAO {
	
	public String decrypPrendiIVCodiceFiscale(int idPaz) throws PersonalException;
	
	public String decryptPrendiIvTelefono(int idPaz) throws PersonalException;
	
	public String decryptPrendiIvEmail(int idPaz) throws PersonalException;
	
	public boolean inserisciInTabellaIV(Iv iv) throws PersonalException;
	
}
