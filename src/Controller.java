import java.security.SecureRandom;
import java.util.Base64;

import javax.swing.JOptionPane;

public class Controller {

//CLASSI:
	private Paziente paziente;
	private PazienteSqlDAO pazienteSqlDAO;
	private Appuntamento appuntamento;
	private AppuntamentoSqlDAO appuntamentoSqlDAO;
	private CryptoUtilsDAO cryptoUtilsDAO;
		
//PAGINE
	public PaginaPrincipale paginaPrincipale;
		
//FINESTRE
	public FinestraInserisciAppuntamento finestrainserisciAppuntamento;
	public FinestraInserisciPaziente finestraInserisciPaziente;
	
//COSTRUTTORE:	
	Controller(){
		paginaPrincipale = new PaginaPrincipale(this);
		paginaPrincipale.setVisible(true);
	}
	
//MAIN	
	public static void main(String[] args) {
		Controller theController = new Controller();
	}
	
//METHODS:
	//SERVE AD INSERIRE IL PAZIENTE: INCOMPLETE!!!!!
	public boolean inserisciPaziente(Paziente p) {
		pazienteSqlDAO = new PazienteSqlDAO();
		byte[] iv = getArrayRandom();
		//CRIPTO IL CODICE FISCALE:
		cryptoUtilsDAO = new CryptoUtilsDAO();
		try {
			p.setCodiceFsicale(cryptoUtilsDAO.encrypt(p.getCodiceFsicale(), iv));
			if(pazienteSqlDAO.inserisci(p)) {
				int idPaz = pazienteSqlDAO.prendiIdPaziente(p.getCodiceFsicale());
				return insertIvInDB(idPaz, iv);
			}
		} catch (PersonalException e) {
			JOptionPane.showMessageDialog(null, "Attenzione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}
	
	//MI SERVE PER INSERIRE l'IV NEL DB:
	private boolean insertIvInDB(int idPaz, byte[] iv){
		cryptoUtilsDAO = new CryptoUtilsDAO();
		return cryptoUtilsDAO.inserisciInTabellaIV(idPaz, Base64.getEncoder().encodeToString(iv));
	}
	
	//SERVE AD INSERIRE UN APPUNTAMENTO:
	public boolean inserisciAppuntamento(Appuntamento app) {
		appuntamentoSqlDAO = new AppuntamentoSqlDAO();
		try {
			return appuntamentoSqlDAO.inserisci(app);
		} catch (PersonalException e) {
			JOptionPane.showMessageDialog(null, "Attenzione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	//SERVE PER ANDARE DALLA PAGINA PRINCIPALE ALLA FINESTRA PER LA CREAZIONE DI UN CLIENTE:
	public void fromPaginaPrincipaleToFienstraInserisciPaziente() {
		paginaPrincipale.setEnabled(false);
		
		finestraInserisciPaziente = new FinestraInserisciPaziente(this);
		finestraInserisciPaziente.setVisible(true);		
	}
	
	
	
	
	
	//ARRAY DI BYTE RANDOM:
	private  byte[] getArrayRandom() {
		byte[] iv = new byte[12];
		new SecureRandom().nextBytes(iv);
		return iv;
	}
}
