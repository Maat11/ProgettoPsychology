import java.sql.Connection;

public interface IvDAO {
	
	public byte[] getArrayRandom() throws PersonalException;
	
	public String decrypPrendiIVCodiceFiscale(int idPaz) throws PersonalException;
	
	public String decryptPrendiIvTelefono(int idPaz) throws PersonalException;
	
	public String decryptPrendiIvEmail(int idPaz) throws PersonalException;
	
	public boolean inserisciInTabellaIV(Iv iv) throws PersonalException;
	
	//MI SERVONO PER LA MODIFICA DELL'IV DEL TELEFONO:
	public boolean aggiornaIV(int idPaz, String strIv, Connection conn) throws PersonalException;
	
}
