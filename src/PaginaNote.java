import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class PaginaNote extends JFrame {
	private Controller theController;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablePazienti;
	private JTable tableNote;
	private DefaultTableModel modelPazienti;
	private DefaultTableModel modelNote;
	private JButton btnBack;
	private JButton btnDettagli;
	private int idPaz = 0;
	private int countClickPazienti;
	private JLabel lblInfo;
	private JLabel lblInfo2;
	private int idNota;
	private DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer(); //SERVE PER CENTRALIZZARE IL TESTO NELLE CELLE DELLA TABELLA
	private JButton btnElimina;
	
	public PaginaNote(Controller c) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				//POPOLA TABELLA PAZIENTE:
				theController.popolaTabellaConPazienti(modelPazienti, "");
				
				//POPOLA TABELLA CON TUTTE LE NOTE:
				theController.popolaTabellaNote(modelNote);
			}
			@Override
			public void windowClosing(WindowEvent e) {
				//TORNA INDIETRO:
				btnBack.doClick();
			}
		});
		theController = c;
		
		setTitle("Pagina note");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(900, 900);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(173, 216, 230));
		setJMenuBar(menuBar);
		
		JMenu menuNota = new JMenu("Nota");
		menuNota.setFont(new Font("Tahoma", Font.PLAIN, 15));
		menuBar.add(menuNota);
		
		JMenuItem mntmInserisci = new JMenuItem("Inserisci");
		mntmInserisci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//VAI ALLA FINESTRA PER INSERIRE UNA NOTA:
				theController.fromPaginaNoteToFinestraNota();
			}
		});
		mntmInserisci.setFont(new Font("Tahoma", Font.PLAIN, 13));
		mntmInserisci.setHorizontalAlignment(SwingConstants.CENTER);
		menuNota.add(mntmInserisci);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTop = new JPanel();
		panelTop.setBackground(SystemColor.info);
		contentPane.add(panelTop, BorderLayout.NORTH);
		
		lblInfo = new JLabel("Seleziona un paziente");
		lblInfo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelTop.add(lblInfo);
		
		JPanel panelCentral = new JPanel();
		contentPane.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCentralTop = new JPanel();
		panelCentral.add(panelCentralTop, BorderLayout.NORTH);
		panelCentralTop.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTablePazienti = new JPanel();
		panelCentralTop.add(panelTablePazienti, BorderLayout.CENTER);
		panelTablePazienti.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPanePazienti = new JScrollPane();
		
		panelTablePazienti.add(scrollPanePazienti, BorderLayout.CENTER);
		
		modelPazienti  = new DefaultTableModel(
				new Object[][]{},
				new String[]{"id paziente", "Nome", "Cognome", "Codice fiscale", "Nascita", "Telefono", "Email",  "Prezzo", "Credito"}
			);
		
		tablePazienti = new JTable(modelPazienti);
		tablePazienti.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		//SERVE PER CENTRALIZZARE I DATI NELLE CELLE DELLA TABELLA:
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		//APPLICALO A TUTTE LE COLONNE:
		for (int i = 0; i < tablePazienti.getColumnCount(); i++) {
		    tablePazienti.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
		tablePazienti.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int rowSel = tablePazienti.rowAtPoint(e.getPoint());
				
				if(rowSel != -1) {
					idPaz = Integer.valueOf(String.valueOf(tablePazienti.getValueAt(rowSel, 0)));
					lblInfo2.setText("Stai visualizzando le note del paziente " + String.valueOf(tablePazienti.getValueAt(rowSel, 1)) + " " + String.valueOf(tablePazienti.getValueAt(rowSel, 2)) );
					if(idPaz != 0) {
						//POPOLA TABELLA CON LE NOTE DEL PAZIENTE SELEZIONATO:
						theController.popolaTabellaNotePerPaziente(idPaz, modelNote);
					}
				}
				
				//COUNTER DEL CLICK:
				countClickPazienti++;
				
				if(countClickPazienti == 2) { 
					//DESELEZIONA TUPLA:
					tablePazienti.clearSelection();
					tablePazienti.getSelectionModel().setAnchorSelectionIndex(-1);
					tablePazienti.getSelectionModel().setLeadSelectionIndex(-1);

					//POPOLA TABELLA:
					theController.popolaTabellaNote(modelNote);
					
					//CAMBIA TEXT:
					lblInfo2.setText("Note");
					
					//AGGIORNA IL COUNTER DEL CLICK:
					countClickPazienti = 0;
				}
			}
		});
		
		//NASCONDO L'id DEL PAZIENTE:
		tablePazienti.getColumnModel().getColumn(0).setWidth(0);
		tablePazienti.getColumnModel().getColumn(0).setMaxWidth(0);
		tablePazienti.getColumnModel().getColumn(0).setMinWidth(0);
		tablePazienti.getColumnModel().getColumn(0).setPreferredWidth(0);
		
		//NASCONDO L'EMAIL:
		tablePazienti.getColumnModel().getColumn(6).setWidth(0);
		tablePazienti.getColumnModel().getColumn(6).setMaxWidth(0);
		tablePazienti.getColumnModel().getColumn(6).setMinWidth(0);
		tablePazienti.getColumnModel().getColumn(6).setPreferredWidth(0);
		
		//NASCONDO IL PREZZO:
		tablePazienti.getColumnModel().getColumn(7).setWidth(0);
		tablePazienti.getColumnModel().getColumn(7).setMaxWidth(0);
		tablePazienti.getColumnModel().getColumn(7).setMinWidth(0);
		tablePazienti.getColumnModel().getColumn(7).setPreferredWidth(0);
		
		//NASCONDO IL CREDITO:
		tablePazienti.getColumnModel().getColumn(8).setWidth(0);
		tablePazienti.getColumnModel().getColumn(8).setMaxWidth(0);
		tablePazienti.getColumnModel().getColumn(8).setMinWidth(0);
		tablePazienti.getColumnModel().getColumn(8).setPreferredWidth(0);
		
		scrollPanePazienti.setColumnHeaderView(tablePazienti);
		scrollPanePazienti.setViewportView(tablePazienti);
		
		JPanel panelCentralCentral = new JPanel();
		panelCentralCentral.setBackground(SystemColor.info);
		panelCentral.add(panelCentralCentral, BorderLayout.CENTER);
		panelCentralCentral.setLayout(new BorderLayout(0, 0));
		
		lblInfo2 = new JLabel("Note");
		lblInfo2.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelCentralCentral.add(lblInfo2, BorderLayout.NORTH);
		
		JPanel panelTableNote = new JPanel();
		panelCentralCentral.add(panelTableNote, BorderLayout.CENTER);
		panelTableNote.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneNote = new JScrollPane();
		panelTableNote.add(scrollPaneNote, BorderLayout.CENTER);
		
		modelNote  = new DefaultTableModel(
				new Object[][]{},
				new String[]{"id nota", "id appuntamento", "id paziente", "Parola chaive", "Nota", "Data"}
			);
		
		tableNote = new JTable(modelNote);
		tableNote.setBackground(new Color(255, 255, 255));
		tableNote.setFont(new Font("Tahoma", Font.PLAIN, 14));

		//CENTRALLIZZO IL TESTO NELLE CELLE NUMERO 3 e 5:
		tableNote.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		tableNote.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

		tableNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selRow = tableNote.rowAtPoint(e.getPoint());
				
				if(selRow != -1) {
					
					idNota = Integer.valueOf(String.valueOf(tableNote.getValueAt(selRow, 0)));
					
					//RENDO DISPONIBILI I PULSANTI:
					btnDettagli.setEnabled(true);
					btnElimina.setEnabled(true);
				}
			}
		});
		
		//NASCONDO L'ID DELLA NOTA:
		tableNote.getColumnModel().getColumn(0).setWidth(0);
		tableNote.getColumnModel().getColumn(0).setMaxWidth(0);
		tableNote.getColumnModel().getColumn(0).setMinWidth(0);
		tableNote.getColumnModel().getColumn(0).setPreferredWidth(0);
		
		//NASCONDO L'ID DELL'APPUNTAMENTO:
		tableNote.getColumnModel().getColumn(1).setWidth(0);
		tableNote.getColumnModel().getColumn(1).setMaxWidth(0);
		tableNote.getColumnModel().getColumn(1).setMinWidth(0);
		tableNote.getColumnModel().getColumn(1).setPreferredWidth(0);
		
		//NASCONDO L'ID DEL PAZIENTE:
		tableNote.getColumnModel().getColumn(2).setWidth(0);
		tableNote.getColumnModel().getColumn(2).setMaxWidth(0);
		tableNote.getColumnModel().getColumn(2).setMinWidth(0);
		tableNote.getColumnModel().getColumn(2).setPreferredWidth(0);
	
		
		scrollPaneNote.setColumnHeaderView(tableNote);
		scrollPaneNote.setViewportView(tableNote);
		
		JPanel panelBottom = new JPanel();
		contentPane.add(panelBottom, BorderLayout.SOUTH);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TORNA INDIETRO:
				setVisible(false);
				
				theController.paginaPaziente.setVisible(true);
				theController.paginaPaziente.setEnabled(true);
			}
		});
		panelBottom.setLayout(new BorderLayout(0, 0));
		panelBottom.add(btnBack, BorderLayout.WEST);
		
		btnDettagli = new JButton("Dettagli");
		btnDettagli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//DETTAGLI DEL PAZIENTE:
				theController.fromPaginaNoteToFinestraDettagliNota(idNota);
			}
		});
		btnDettagli.setEnabled(false);
		panelBottom.add(btnDettagli, BorderLayout.EAST);
		
		JPanel panelBottomCentral = new JPanel();
		panelBottom.add(panelBottomCentral, BorderLayout.CENTER);
		
		btnElimina = new JButton("Elimina");
		btnElimina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ELIMINA NOTA:
				int confirm = JOptionPane.showConfirmDialog(null, "Confermare l'operazione di eliminazione della nota selezionata?", "Messaggio", JOptionPane.YES_NO_OPTION);
				if(confirm == JOptionPane.YES_OPTION) {
					if(theController.eliminaNota(idNota)) {
						JOptionPane.showMessageDialog(null, "L'operazione è andata a buon fine!");
						
						//SET ENABLE FALSE DEL PULSANTE:
						btnElimina.setEnabled(false);
						
						//POPOLA TABELLA CON TUTTE LE NOTE:
						theController.popolaTabellaNote(modelNote);
					}
				}else {
					//SET ENABLE FALSE DEL PULSANTE:
					btnElimina.setEnabled(false);
					JOptionPane.showMessageDialog(null, "L'operazione è stata annullata!");
				}
			}
		});
		btnElimina.setEnabled(false);
		btnElimina.setForeground(Color.RED);
		panelBottomCentral.add(btnElimina);

	}

}
