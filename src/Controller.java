import java.security.SecureRandom;
import java.util.Base64;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

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
	public FinestraInserisciAppuntamento finestraInserisciAppuntamento;
	public FinestraInserisciPaziente finestraInserisciPaziente;
	public FinestraSceltaPazientePerAppuntamento finestraSceltaPazientePerAppuntamento;
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
		cryptoUtilsDAO = new CryptoUtilsDAO();
		
		byte[] iv = getArrayRandom();
		
		try {
			p.setCodiceFsicale(cryptoUtilsDAO.encrypt(p.getCodiceFsicale().toUpperCase(), iv));
			
			if(pazienteSqlDAO.inserisci(p)) {
				p.setId(pazienteSqlDAO.prendiIdPaziente(p.getCodiceFsicale()));

				if(p.getId() != 0) {
					return cryptoUtilsDAO.inserisciInTabellaIV(p.getId(), Base64.getEncoder().encodeToString(iv));
				}else {
					return false;
				}
			}
		} catch (PersonalException e) {
			JOptionPane.showMessageDialog(null, "Attenzione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
		return false;
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
	
	//SERVE PER ANDARE ALLA FINESTRA PER INSERIRE UN APPUNTAMENTO:
	public void fromPaginaPrincipaleToFinestraInserisciAppuntamento() {
		paginaPrincipale.setEnabled(false);
		
		finestraInserisciAppuntamento = new FinestraInserisciAppuntamento(this);
		finestraInserisciAppuntamento.setVisible(true);
	}
	
	//SERVE PER ANDARE DALLA FINESTRA PER CREARE UN APPUNTAMENTO ALLA FINESTRA PER LA SCELTA DEL CODICE FISCALE DEL PAZIENTE CHE ANDRA' ALL'APPUNTAMENTO:
	public void fromFinestraInserisciAppuntamentoToFinestraSceltaPazientePerAppuntamento(JTextField txtCodiceFiscale) {
		finestraInserisciAppuntamento.setEnabled(false);
		
		finestraSceltaPazientePerAppuntamento = new FinestraSceltaPazientePerAppuntamento(txtCodiceFiscale, this);
	}
	
	//ARRAY DI BYTE RANDOM:
	private  byte[] getArrayRandom() {
		byte[] iv = new byte[12];
		new SecureRandom().nextBytes(iv);
		return iv;
	}
}
