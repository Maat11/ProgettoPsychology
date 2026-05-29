import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.SystemColor;

public class PaginaPrincipale extends JFrame {
	private Controller theController;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String dataOdierna;
	private JLabel lblWelcome;
	private JLabel lblData;
	private DefaultTableModel model;
	private JTable table;
	private JButton btnEliminaAppuntamento;
	private JButton btnCompleta;
	private java.sql.Date sqlDate;
	private JButton btnNotaRapida;
	private String statoApp;
	private int idAppSel;
	private int idPazSel;
	
	public PaginaPrincipale(Controller c) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				//DATA ODIERNA:
				LocalDate dataOdiernaLocalDate = LocalDate.now();
				//TIPO FORMATTER:
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				
				dataOdierna = dataOdiernaLocalDate.format(formatter);
				//SETTO I LABEL:
				lblData.setForeground(Color.RED);
				lblData.setText(dataOdierna);
				
				sqlDate = java.sql.Date.valueOf(dataOdiernaLocalDate);
				
				//POPOLA TABELLA:
				theController.popolaTabellaAppuntamentiConPazientiEConData(sqlDate, model);
			}
		});
		theController = c;
		
		setTitle("Pagina Principale");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(690, 565);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(245, 255, 250));
		menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		setJMenuBar(menuBar);
		
		JMenu menuInserisciAppuntamento = new JMenu("Appuntamenti");
		menuInserisciAppuntamento.setFont(new Font("Dialog", Font.PLAIN, 15));
		menuBar.add(menuInserisciAppuntamento);
		
		JMenuItem itemInserisciAppuntamenti = new JMenuItem("Inserisci appuntamento");
		itemInserisciAppuntamenti.setFont(new Font("Dialog", Font.PLAIN, 14));
		itemInserisciAppuntamenti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//FINESTRA INSERISCI APPUNTAMENTO:
				theController.fromPaginaPrincipaleToFinestraInserisciAppuntamento();
			}
		});
		menuInserisciAppuntamento.add(itemInserisciAppuntamenti);
		
		JMenuItem itemDisdiciAppuntamento = new JMenuItem("Disdici appuntamento");
		itemDisdiciAppuntamento.setFont(new Font("Dialog", Font.PLAIN, 14));
		itemDisdiciAppuntamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//FINESTRA DISDICI APPUNTAMENTO:
				theController.fromPaginaPrincipaleToFinestraEliminaAppuntamento();
			}
		});
		menuInserisciAppuntamento.add(itemDisdiciAppuntamento);
		
		JMenuItem itemMenuVisualizzaAppuntamenti = new JMenuItem("Visualizza appuntamenti");
		itemMenuVisualizzaAppuntamenti.setFont(new Font("Dialog", Font.PLAIN, 14));
		itemMenuVisualizzaAppuntamenti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//VISUALIZZA APPUNTAMENTI PASSATI E FUTURI:
				theController.fromPaginaPrincipaleToFienstraVisualizzaAppuntamenti();
			}
		});
		menuInserisciAppuntamento.add(itemMenuVisualizzaAppuntamenti);
		
		JMenu menuCliente = new JMenu("Pazienti");
		menuCliente.setFont(new Font("Dialog", Font.PLAIN, 15));
		menuBar.add(menuCliente);
		
		JMenuItem itemInserisciCliente = new JMenuItem("Inserisci pazienti");
		itemInserisciCliente.setFont(new Font("Dialog", Font.PLAIN, 14));
		itemInserisciCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//AGGIUNTA DI UN CLIENTE:
				theController.fromPaginaPToFienstraInserisciPaziente(1);
			}
		});
		menuCliente.add(itemInserisciCliente);
		
		JMenuItem itemVisualizzaPazienti = new JMenuItem("Visualizza pazienti");
		itemVisualizzaPazienti.setFont(new Font("Dialog", Font.PLAIN, 14));
		itemVisualizzaPazienti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//PAGINA PER VISUALIZZARE TUTTI I CLIENTI:
				theController.fromPaginaPrincipaleToPaginaPaziente();
			}
		});
		menuCliente.add(itemVisualizzaPazienti);
		
		JMenu menuPagamento = new JMenu("Pagamento");
		menuPagamento.setFont(new Font("Dialog", Font.PLAIN, 15));
		menuBar.add(menuPagamento);
		
		JMenuItem itemVisualizzaCreditori = new JMenuItem("Visualizza creditori");
		itemVisualizzaCreditori.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		itemVisualizzaCreditori.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		menuPagamento.add(itemVisualizzaCreditori);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTop = new JPanel();
		contentPane.add(panelTop, BorderLayout.NORTH);
		
		lblWelcome = new JLabel("Appuntamenti di oggi ");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 25));
		panelTop.add(lblWelcome);
		
		lblData = new JLabel(" ");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 25));
		panelTop.add(lblData);
		
		JPanel panelBottom = new JPanel();
		contentPane.add(panelBottom, BorderLayout.SOUTH);
		panelBottom.setLayout(new BorderLayout(0, 0));
		
		btnEliminaAppuntamento = new JButton("Elimina");
		btnEliminaAppuntamento.setEnabled(false);
		btnEliminaAppuntamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ELIMINA APPUNTAMENTO:
				int confirm = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler eliminare l'appuntamento selezionato?", "Conferma Operazione", JOptionPane.YES_NO_OPTION);
				if(confirm == JOptionPane.YES_OPTION) {
					if(theController.eliminaAppuntamento(idAppSel)) {
						theController.popolaTabellaAppuntamentiConPazientiEConData(sqlDate, model);
						JOptionPane.showMessageDialog(null, "Complimenti, l'appuntamento è stato cancellato correttamente!");
					}
				}else {
					JOptionPane.showMessageDialog(null, "L'operazione è stata annullata!");
				}
				clearFields();
				setPulsantiEnableInFalse();
			}
		});
		panelBottom.add(btnEliminaAppuntamento, BorderLayout.WEST);
		
		btnCompleta = new JButton("Completa");
		btnCompleta.setEnabled(false);
		btnCompleta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//VISUALIZZA DETTAGLI:

				clearFields();
				setPulsantiEnableInFalse();
			}
		});
		panelBottom.add(btnCompleta, BorderLayout.EAST);
		
		JPanel panelBottomCentral = new JPanel();
		panelBottom.add(panelBottomCentral, BorderLayout.CENTER);
		
		btnNotaRapida = new JButton("Nota rapida");
		btnNotaRapida.setEnabled(false);
		btnNotaRapida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//VAI ALLA FINESTA PER INSERIRE UNA NOTA RAPIDA:
				theController.fromPaginaPrincipaleToFinestraNotaRapida(idPazSel, idAppSel);
				clearFields();
				setPulsantiEnableInFalse();
			}
		});
		panelBottomCentral.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelBottomCentral.add(btnNotaRapida);
		
		JPanel panelCentral = new JPanel();
		contentPane.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTable = new JPanel();
		panelCentral.add(panelTable, BorderLayout.CENTER);
		panelTable.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panelTable.add(scrollPane, BorderLayout.CENTER);
		
		model  = new DefaultTableModel(
				new Object[][]{},
				new String[]{"id App", "Data giorno", "Ora inizio" , "Ora fine" , "id paziente", "Nome", "Cognome", "Telefono", "Modalità", "Pagato"}
			);
		
		
		table = new JTable(model);
		table.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		//NON MOSTRARE LA COLONNA ID APPUNTAMENTO:
		table.getColumnModel().getColumn(0).setWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		
		//NON MOSTRARE LA COLONNA CON LA DATA DELL'APPUNTAMENTO:
		table.getColumnModel().getColumn(1).setWidth(0);
		table.getColumnModel().getColumn(1).setMaxWidth(0);
		table.getColumnModel().getColumn(1).setMinWidth(0);
		table.getColumnModel().getColumn(1).setPreferredWidth(0);
		
		//NON MOSTRARE LA COLONNA ID PAZIENTE:
		table.getColumnModel().getColumn(4).setWidth(0);
		table.getColumnModel().getColumn(4).setMaxWidth(0);
		table.getColumnModel().getColumn(4).setMinWidth(0);
		table.getColumnModel().getColumn(4).setPreferredWidth(0);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//SELEZIONA RIGA:
				int rowSel = table.rowAtPoint(e.getPoint());
				if(rowSel != -1) {
					//PRENDERE L'id, E LO STATO, DELL'APPUNTAMENTO + L'id DEL PAZIENTE:
					idAppSel = Integer.valueOf(String.valueOf(table.getValueAt(rowSel, 0)));
					
					statoApp = String.valueOf(table.getValueAt(rowSel, 9));
					
					//SERVE NEL CASO L'OPERATORE DOVESSE INSERIRE UNA NOTA RAPIDA:
					idPazSel = Integer.valueOf(String.valueOf(table.getValueAt(rowSel, 4)));
					
					//MI SERVE PER BLOCCARE IL PULSANTE COMPLETA UNA VOLTA CHE E' COMPLETATO L'APPUNTAMENTO:
					if(statoApp.equalsIgnoreCase("Non definito")) {
						//SBLOCCA PULSANTI:
						btnEliminaAppuntamento.setEnabled(true);
						
						btnCompleta.setEnabled(true);
					}
					btnNotaRapida.setEnabled(true);
					
				}
			}
		});
		scrollPane.setColumnHeaderView(table);
		scrollPane.setViewportView(table);
	}
//METODI:
	private void setPulsantiEnableInFalse() {
		btnEliminaAppuntamento.setEnabled(false);
		btnNotaRapida.setEnabled(false);
		btnCompleta.setEnabled(false);
	}
	
	//SERVE PER PULIRE I VARI DATI:
	private void clearFields() {
		statoApp = null;
		idPazSel = 0;
		idAppSel = 0;
	}
	
//	private String statoApp;
//	private int idAppSel;
//	private String nomeSel;
//	private String cognomeSel;
}
