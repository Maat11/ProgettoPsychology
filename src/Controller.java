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
	public PaginaPaziente paginaPaziente;
	
//FINESTRE
	public FinestraInserisciAppuntamento finestraInserisciAppuntamento;
	public FinestraInserisciPaziente finestraInserisciPaziente;
	public FinestraSceltaPazientePerAppuntamento finestraSceltaPazientePerAppuntamento;
	public FinestraEliminaAppuntamento finestraEliminaAppuntamento;
	public FinestraVisualizzaAppuntamenti finestraVisualizzaAppuntamenti;
	public FinestraEliminaPaziente finestraEliminaPaziente;
	
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
	public void fromPaginaPToFienstraInserisciPaziente(int typChiam) {
		if(typChiam == 1) {
			paginaPrincipale.setEnabled(false);
		}else if(typChiam == 2){
			paginaPaziente.setEnabled(false);
		}
		
		finestraInserisciPaziente = new FinestraInserisciPaziente(this, typChiam);
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
	
	//SERVE PER ANDARE DALLA PAGINA PRINCIPALE ALLA FINESTRA PER ELIMINA UN APPUNTAMENTO:
	public void fromPaginaPrincipaleToFinestraEliminaAppuntamento() {
		paginaPrincipale.setEnabled(false);
		
		finestraEliminaAppuntamento = new FinestraEliminaAppuntamento(this);
		finestraEliminaAppuntamento.setVisible(true);
	}
	
	//SERVE PER ANDARE DALLA PAGINA PRINCIPALE ALLA FINESTRA PER VISUALIZZARE GLI APPUNTAMENTI (TUTTI) IN BASE ALLA DATA:
	public void fromPaginaPrincipaleToFienstraVisualizzaAppuntamenti() {
		paginaPrincipale.setEnabled(false);
		
		finestraVisualizzaAppuntamenti = new FinestraVisualizzaAppuntamenti(this);
		finestraVisualizzaAppuntamenti.setVisible(true);		
	}
	
	//SERVE A POPOLARE LA TABELLA CON I PAZIENTI:
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
	
	public boolean eliminaAppuntamento(int idApp) {
		appuntamentoSqlDAO = new AppuntamentoSqlDAO();
		
		try {
			return appuntamentoSqlDAO.elimina(idApp);
		} catch (PersonalException e) {
			JOptionPane.showMessageDialog(null, "Attenzione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	//SERVE PER ANDARE DALLA PAGINA PRINCIPALE ALLA PAGINA PAZIENTI:
	public void fromPaginaPrincipaleToPaginaPaziente() {
		paginaPrincipale.setVisible(false);
		
		paginaPaziente = new PaginaPaziente(this);
		paginaPaziente.setVisible(true);
	}
	
	//SERVE PER ELIMIANARE UN PAZIENTE:
	public boolean eliminaPaziente(int idPaziente) {
		pazienteSqlDAO = new PazienteSqlDAO();
		
		try {
			return pazienteSqlDAO.elimina(idPaziente);
		} catch (PersonalException e) {
			JOptionPane.showMessageDialog(null, "Attenzione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	//SERVE PER ANDARE DALLA PAGINA PAZIENTE ALLA FINESTRA PER ELIMINARE IL PAZIENTE:
	public void fromPaginaPazienteToFinestraEliminaPaziente() {
		paginaPaziente.setEnabled(false);
		
		finestraEliminaPaziente = new FinestraEliminaPaziente(this);
		finestraEliminaPaziente.setVisible(true);
	}
	
	//ARRAY DI BYTE RANDOM:
	private  byte[] getArrayRandom() {
		byte[] iv = new byte[12];
		new SecureRandom().nextBytes(iv);
		return iv;
	}
}
