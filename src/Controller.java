import java.security.SecureRandom;
import java.util.Base64;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

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
	public FinestraEliminaAppuntamento finestraEliminaAppuntamento;
	
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
	public void fromFinestraInserisciAppuntamentoToFinestraSceltaPazientePerAppuntamento(JTextField txtCodiceFiscale, JTextField idPaziente) {
		finestraInserisciAppuntamento.setEnabled(false);
		
		finestraSceltaPazientePerAppuntamento = new FinestraSceltaPazientePerAppuntamento(txtCodiceFiscale, idPaziente, this);
		finestraSceltaPazientePerAppuntamento.setVisible(true);
	}
	
	public void fromPaginaPrincipaleToFinestraEliminaAppuntamento() {
		paginaPrincipale.setEnabled(false);
		
		finestraEliminaAppuntamento = new FinestraEliminaAppuntamento(this);
		finestraEliminaAppuntamento.setVisible(true);
	}
	
	//MI SERVE A POPOLARE LA TABELLA CON I PAZIENTI:
	public void popolaTabellaConPazienti(DefaultTableModel model, String cognome) {
		pazienteSqlDAO = new PazienteSqlDAO();
		model.setRowCount(0);
		
		try {
			pazienteSqlDAO.popolaTabella(model, cognome);
		} catch (PersonalException e) {
			JOptionPane.showMessageDialog(null, "Attenzione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	//MI SERVE PER POPOLARE LA TABELLA DEGLI APPUNTAMENTI CERCANDO CON UNA DATA SPECIFICA:
	public void popolaTabellaAppuntamentiConPazientiEConData(java.sql.Date data, DefaultTableModel model) {
		appuntamentoSqlDAO = new AppuntamentoSqlDAO();
		model.setRowCount(0);
		
		try {
			appuntamentoSqlDAO.popolaTabellaConData(data, model);
		} catch (PersonalException e) {
			JOptionPane.showMessageDialog(null, "Attenzione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	public boolean eliminaAppuntamento(java.sql.Date data, String oraInizio, String oraFine) {
		appuntamentoSqlDAO = new AppuntamentoSqlDAO();
		
		try {
			return appuntamentoSqlDAO.elimina(data, oraInizio, oraFine);
		} catch (PersonalException e) {
			JOptionPane.showMessageDialog(null, "Attenzione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	//ARRAY DI BYTE RANDOM:
	private  byte[] getArrayRandom() {
		byte[] iv = new byte[12];
		new SecureRandom().nextBytes(iv);
		return iv;
	}
}
