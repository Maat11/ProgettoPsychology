package controller;

import gui.*;
import dto.*;
import dao.*;
import dao.postgresql.AppuntamentoSqlDAO;
import dao.postgresql.IvSqlDAO;
import dao.postgresql.NotaSqlDAO;
import dao.postgresql.PazienteSqlDAO;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Base64;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import exception.PersonalException;
import gui.FinestraDettagliNota;
import gui.FinestraEliminaAppuntamento;
import gui.FinestraEliminaPaziente;
import gui.FinestraInserisciAppuntamento;
import gui.FinestraInserisciPaziente;
import gui.FinestraModificaDatiPaziente;
import gui.FinestraModificaDatiSensibili;
import gui.FinestraNota;
import gui.FinestraNotaRapida;
import gui.FinestraSceltaPazientePerAppuntamento;
import gui.FinestraVisualizzaAppuntamenti;
import gui.PaginaNote;
import gui.PaginaPaziente;
import gui.PaginaPrincipale;
import util.CryptoUtils;

public class Controller {

//CLASSI:
	private PazienteDAO pazienteDAO = new PazienteSqlDAO();
	private AppuntamentoDAO appuntamentoDAO = new AppuntamentoSqlDAO();
	private NotaDAO notaDAO = new NotaSqlDAO();
	private IvDAO ivDAO = new IvSqlDAO();
		
//PAGINE
	public PaginaPrincipale paginaPrincipale;
	public PaginaPaziente paginaPaziente;
	public PaginaNote paginaNote;
	
//FINESTRE
	public FinestraInserisciAppuntamento finestraInserisciAppuntamento;
	public FinestraInserisciPaziente finestraInserisciPaziente;
	public FinestraSceltaPazientePerAppuntamento finestraSceltaPazientePerAppuntamento;
	public FinestraEliminaAppuntamento finestraEliminaAppuntamento;
	public FinestraVisualizzaAppuntamenti finestraVisualizzaAppuntamenti;
	public FinestraEliminaPaziente finestraEliminaPaziente;
	public FinestraNotaRapida finestraNotaRapida;
	public FinestraModificaDatiPaziente finestraModificaDatiPaziente;
	public FinestraModificaDatiSensibili finestraModificaDatiSensibili;
	public FinestraNota finestraNota;
	public FinestraDettagliNota finestraDettagliNota;
	
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
		byte[] ivCodiceFiscale = null;
		byte[] ivTel = null; 
		
		try {
			ivCodiceFiscale = ivDAO.getArrayRandom();
			ivTel = ivDAO.getArrayRandom();
		} catch (PersonalException e) {
			JOptionPane.showMessageDialog(null, "Attenzione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
		
		if(ivCodiceFiscale != null && ivTel != null) {
			try {
				p.setCodiceFiscale(CryptoUtils.encrypt(p.getCodiceFiscale().toUpperCase().trim(), ivCodiceFiscale));
				p.setTelefono(CryptoUtils.encrypt(p.getTelefono().trim(), ivTel));

				if(pazienteDAO.inserisci(p)) {
					p.setId(pazienteDAO.prendiIdPaziente(p.getCodiceFiscale()));

					if(p.getId() != 0) {
						Iv iv = new Iv(p.getId(), Base64.getEncoder().encodeToString(ivCodiceFiscale), Base64.getEncoder().encodeToString(ivTel));
						return ivDAO.inserisciInTabellaIV(iv);
					}else {
						return false;
					}
				}
			} catch (PersonalException e) {
				JOptionPane.showMessageDialog(null, "Attenzione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			}
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
	 public boolean modificaPazienteDatiNonSensibili(Paziente p) {
		 try {
			 return pazienteDAO.modificaDatiNonSensibili(p);
		 } catch (PersonalException e) {
			 JOptionPane.showMessageDialog(null, "Attenzione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			 return false;
		 }
	 }

//SERVE PER LA MODIFICA DEI DATI SENSIBILI:	 
	 //SERVE PER LA MODIFICA DEL NUMERO DI TELEFONO:
	 public boolean modificaTelefono(int idPaz, String newNum) {
    	 try {
             // GENERA UN NUOVO IV:
             byte[] iv = ivDAO.getArrayRandom();
             String ivString = Base64.getEncoder().encodeToString(iv);

             // 2. Aggiorna IV e telefono (delegato al Model)
             // Il Model gestirà la transazione e le SQLException internamente
             if (! ivDAO.aggiornaIVTelefono(idPaz, ivString)) {
                 throw new PersonalException("Fallito aggiornamento IV");
             }

             if (! pazienteDAO.aggiornaTelefono(idPaz, CryptoUtils.encrypt(newNum, iv))) {
                 throw new PersonalException("Fallito aggiornamento telefono");
             }

             return true;
         } catch (PersonalException e) {
             JOptionPane.showMessageDialog(null, "Errore: " + e.getMessage());
             return false;
         }
	}
	 
	 //SERVE PER MODIFICARE L'EMAIL:
	 public boolean modificaEmail(int idPaz, String email) {
		 try {
             // GENERA UN NUOVO IV:
             byte[] iv = ivDAO.getArrayRandom();
             String ivString = Base64.getEncoder().encodeToString(iv);

             // 2. Aggiorna IV e telefono (delegato al Model)
             // Il Model gestirà la transazione e le SQLException internamente
             if (! ivDAO.aggiornaIVEmail(idPaz, ivString)) {
                 throw new PersonalException("Fallito aggiornamento IV");
             }

             if (! pazienteDAO.aggiornaEmail(idPaz, CryptoUtils.encrypt(email.toLowerCase().trim(), iv))) {
                 throw new PersonalException("Fallito aggiornamento email");
             }

             return true;
         } catch (PersonalException e) {
             JOptionPane.showMessageDialog(null, "Errore: " + e.getMessage());
             return false;
         }
	 }
	 
	 //SERVE PER ANDARE DALLA FINESTRA PER LA MODIFICA DEI DATI NON SENSIBILI A QUELLA PER I DATI SENSIBILI:
	 public void fromFinestraModificaDatiNonSensibiliToModificaDatiSensibili() {
		 finestraModificaDatiPaziente.setVisible(false);
		 
		 finestraModificaDatiSensibili = new FinestraModificaDatiSensibili(this);
		 finestraModificaDatiSensibili.setVisible(true);
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
	 public void fromPaginaPazienteToFinestraNota() {
		 paginaPaziente.setEnabled(false);
		 
		 finestraNota = new FinestraNota(this, 1);
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
	 
	 //SERVE PER ANDARE DALLA PAGINA PAZIENTE ALLA PAGINA NOTE:
	 public void fromPaginaPazienteToPaginaNote() {
		 paginaPaziente.setVisible(false);
		 
		 paginaNote = new PaginaNote(this);
		 paginaNote.setVisible(true);
	 }
	 
	 //SERVE PER POPOLARE LA TABELLA CON LE NOTE, DA QUELLA PIù RECENTE, LIMITE 20:
	 public void popolaTabellaNote( DefaultTableModel model) {
		model.setRowCount(0);
		 
		try {
			notaDAO.popolaTabellaConTutteLeNote(model);
		 } catch (PersonalException e) {
			JOptionPane.showMessageDialog(null, "Attenzione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		 }
	 }
	 
	 //SERVE PER POPOLARE LA TABELLE CON LE NOTE IN BASE AL PAZIENTE SELEZIONATO:
	 public void popolaTabellaNotePerPaziente(int idPaz, DefaultTableModel model) {
		 model.setRowCount(0);
		 
		 try {
			notaDAO.popolaTabellaNotePerPaziente(idPaz, model);
		 } catch (PersonalException e) {
			 JOptionPane.showMessageDialog(null, "Attenzione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		 }
	 }
	 
	 //SERVE PER ANDARE DALLA PAGINA NOTA ALLA FINESTRA PER I DETTAGLI DI UNA NOTA:
	 public void fromPaginaNoteToFinestraDettagliNota(int idNota) {
		 paginaNote.setEnabled(false);
		 
		 finestraDettagliNota = new FinestraDettagliNota(idNota, this);
		 finestraDettagliNota.setVisible(true);
	 }
	 
	 //SERVE A PRENDERE LA NOTA CHE SI HA SELEZIONATO:
	 public Nota prendiNotaSelezionata(int idNota) {
		try {
			return  notaDAO.prendiNota(idNota);
		} catch (PersonalException e) {
			JOptionPane.showMessageDialog(null, "Attenzione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	 }
	 
	 //SERVE PER MODIFICARE, NEL CASO, LA NOTA DI CUI SI STA GUARDANDO I DETTAGLI:
	 public boolean modificaNota(Nota nota) {
		 try {
			return notaDAO.modifica(nota);
		 } catch (PersonalException e) {
			 JOptionPane.showMessageDialog(null, "Attenzione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		 }
		 return false;
	 }
	 
	 //SERVE PER ANDARE DALLA PAGINA NOTE ALLA FINESTRA PER LE NOTE:
	 public void fromPaginaNoteToFinestraNota() {
		 paginaNote.setEnabled(false);
		 
		 finestraNota = new FinestraNota(this, 2);
		 finestraNota.setVisible(true);
	 }
	 
	 //SERVE PER ELIMINARE LA NOTA:
	 public boolean eliminaNota(int idNota) {
		 try {
			return notaDAO.elimina(idNota);
		 } catch (PersonalException e) {
			 JOptionPane.showMessageDialog(null, "Attenzione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		 }
		 return false;
	 }

	 
}
