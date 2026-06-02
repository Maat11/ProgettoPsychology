import java.security.SecureRandom;
import java.util.Base64;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Controller {

//CLASSI:
	private Paziente paziente;
	private PazienteDAO pazienteDAO = new PazienteSqlDAO();
	private Appuntamento appuntamento;
	private AppuntamentoDAO appuntamentoDAO = new AppuntamentoSqlDAO();
	private CryptoUtilsDAO cryptoUtilsDAO;
	private Nota nota;
	private NotaDAO notaDAO = new NotaSqlDAO();
		
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
	public FinestraNotaRapida finestraNotaRapida;
	public FinestraModificaDatiPaziente finestraModificaDatiPaziente;
	public FinestraNota finestraNota;
	
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
		cryptoUtilsDAO = new CryptoUtilsDAO();
		
		byte[] iv = getArrayRandom();
		
		try {
			p.setCodiceFsicale(cryptoUtilsDAO.encrypt(p.getCodiceFiscale().toUpperCase(), iv));
			
			if(pazienteDAO.inserisci(p)) {
				p.setId(pazienteDAO.prendiIdPaziente(p.getCodiceFiscale()));

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
		try {
			return appuntamentoDAO.inserisci(app);
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
		model.setRowCount(0);
 
		try {
			pazienteDAO.popolaTabella(model, cognome);
		} catch (PersonalException e) {
			JOptionPane.showMessageDialog(null, "Attenzione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	//MI SERVE PER POPOLARE LA TABELLA DEGLI APPUNTAMENTI CERCANDO CON UNA DATA SPECIFICA:
	public void popolaTabellaAppuntamentiConPazientiEConData(java.sql.Date data, DefaultTableModel model) {
		model.setRowCount(0);
		
		try {
			appuntamentoDAO.popolaTabellaConData(data, model);
		} catch (PersonalException e) {
			JOptionPane.showMessageDialog(null, "Attenzione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	public boolean eliminaAppuntamento(int idApp) {
		try {
			return appuntamentoDAO.elimina(idApp);
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
	
	//SERVE AD ANDARE DALLA PAGINA PRINCIPALE ALLA FINESTRA PER INSERIRE UNA NOTA RAPIDA:
	public void fromPaginaPrincipaleToFinestraNotaRapida(int idPaziente, int idAppuntamento) {
		paginaPrincipale.setEnabled(false);
		
		finestraNotaRapida = new FinestraNotaRapida(this, idPaziente, idAppuntamento);
		finestraNotaRapida.setVisible(true);
	}
	
	//SERVE PER ELIMIANARE UN PAZIENTE:
	public boolean eliminaPaziente(int idPaziente) {
		try {
			return pazienteDAO.elimina(idPaziente);
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
	
	//SERVE PER TROVARE UN PAZIENTE TRAMITE IL SUO id:
	public Paziente trovaPaziente(int idPaziente) {
		try {
			return pazienteDAO.trova(idPaziente);
		} catch (PersonalException e) {
			JOptionPane.showMessageDialog(null, "Attenzione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	 //SERVE PER ANDARE DALLA PAGINA PRINCIPALE ALLA FINESTRA PER LA MODIFICA DEI DATI DEL PAZIENTE:
	 public void fromPaginaPrincipaleToFinestraModificaDatiPaziente() {
		 paginaPaziente.setEnabled(false);
		 
		 finestraModificaDatiPaziente = new FinestraModificaDatiPaziente(this);
		 finestraModificaDatiPaziente.setVisible(true);
	 }
	 
	 //SERVE PER LA MODIFICA DEL PAZIENTE:
	 public boolean modificaPaziente(Paziente p) {
		 try {
			 return pazienteDAO.modifica(p);
		 } catch (PersonalException e) {
			 JOptionPane.showMessageDialog(null, "Attenzione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			 return false;
		 }
	 }
	
	//SERVE PER INSERIRE LA NOTA RAPIDA:
	 public boolean inserisciNotaRapida(Nota notaRap) {
		 try {
			return notaDAO.inserisciNotaRapida(notaRap);
		 } catch (PersonalException e) {
			 JOptionPane.showMessageDialog(null, "Attenzione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			 return false;
		 }
	 }
	 
	 //SERVE PER ANDARE DALLA PAGINA PAZIENTE ALLA FINESTRA PER INSERIRE UNA NOTA:
	 public void fromPaginaPazienteToFinestraInserisciNota() {
		 paginaPaziente.setEnabled(false);
		 
		 finestraNota = new FinestraNota(this);
		 finestraNota.setVisible(true);
	 }
	 
	 //SERVE PER INSERIRE UNA NOTA:
	 public boolean inserisciNota(Nota nota) {
		 try {
			return notaDAO.inserisciNota(nota);
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
