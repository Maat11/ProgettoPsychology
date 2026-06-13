import java.sql.Connection;

public interface IvDAO {
	
	public byte[] getArrayRandom() throws PersonalException;
	
	public String decrypPrendiIVCodiceFiscale(int idPaz) throws PersonalException;
	
	public String decryptPrendiIvTelefono(int idPaz) throws PersonalException;
	
	public String decryptPrendiIvEmail(int idPaz) throws PersonalException;
	
	public boolean inserisciInTabellaIV(Iv iv) throws PersonalException;
	
	public boolean aggiornaIVTelefono(int idPaz, String strIv) throws PersonalException;
	
	public boolean aggiornaIVEmail(int idPaz, String ivEmail, Connection conn) throws PersonalException;
	
}
