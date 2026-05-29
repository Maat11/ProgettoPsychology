import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Font;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.SwingConstants;

public class PaginaPaziente extends JFrame {
	private Controller theController;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnDettagli;
	private JButton btnBack;
	private JTextField txtCognome;
	private JTable table;
	private DefaultTableModel model;
	private JLabel lblWelcome;
	private int idPazSel;
	private JLabel lblPazienteSelezionato;
	
	public PaginaPaziente(Controller c) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				theController.popolaTabellaConPazienti(model, "");
			}
			@Override
			public void windowClosing(WindowEvent e) {
				btnBack.doClick();
			}
		});
		theController = c;
		
		setTitle("Pagina dedicata ai pazienti");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		menuBar.setBackground(SystemColor.inactiveCaption);
		setJMenuBar(menuBar);
		
		JMenu menuPaziente = new JMenu("Paziente");
		menuPaziente.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		menuBar.add(menuPaziente);
		
		JMenuItem mntmInserisciPaziente = new JMenuItem("Inserisci");
		mntmInserisciPaziente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//INSERISCI PAZIENTE: 
				theController.fromPaginaPToFienstraInserisciPaziente(2);
			}
		});
		menuPaziente.add(mntmInserisciPaziente);
		
		JMenuItem mntmModificaPaziente = new JMenuItem("Modifica");
		mntmModificaPaziente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//MODIFICA PAZIENTE:
				
			}
		});
		menuPaziente.add(mntmModificaPaziente);
		
		JMenuItem mntmEliminaPaziente = new JMenuItem("Elimina");
		mntmEliminaPaziente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ELIMINA PAZIENTE:
				theController.fromPaginaPazienteToFinestraEliminaPaziente();
			}
		});
		menuPaziente.add(mntmEliminaPaziente);
		
		JMenu menuNote = new JMenu("Note");
		menuNote.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		menuBar.add(menuNote);
		
		JMenuItem mntmInserisciNota = new JMenuItem("Inserisci");
		mntmInserisciNota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//VAI ALLA FINESTRA PER INSERIRE UNA NOTA:
			}
		});
		menuNote.add(mntmInserisciNota);
		
		JMenuItem mntmVisualizzaNota = new JMenuItem("Visualizza");
		mntmVisualizzaNota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//VAI ALLA FINESTRA PER VISUALIZZARE LE NOTE, INIZ. NOTE RAPIE MA ANCHE NOTE IN BASE AL PAZIENTE:
				
			}
		});
		menuNote.add(mntmVisualizzaNota);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTop = new JPanel();
		panelTop.setBackground(new Color(255, 255, 240));
		contentPane.add(panelTop, BorderLayout.NORTH);
		
		lblWelcome = new JLabel("In questa pagina puoi visualizzare i tuoi pazienti");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 17));
		panelTop.add(lblWelcome);
		
		JPanel panelCentral = new JPanel();
		contentPane.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCentralTop = new JPanel();
		panelCentral.add(panelCentralTop, BorderLayout.NORTH);
		panelCentralTop.setLayout(new BorderLayout(0, 0));
		
		JLabel lblCercaCognome = new JLabel("Cerca per cognome");
		lblCercaCognome.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panelCentralTop.add(lblCercaCognome, BorderLayout.WEST);
		
		txtCognome = new JTextField();
		txtCognome.setToolTipText("Inserisci il cognome completo. Attenzione alle maiuscole");
		panelCentralTop.add(txtCognome, BorderLayout.EAST);
		txtCognome.setColumns(10);
		
		txtCognome.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				theController.popolaTabellaConPazienti(model, txtCognome.getText().trim());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				theController.popolaTabellaConPazienti(model, txtCognome.getText().trim());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				theController.popolaTabellaConPazienti(model, txtCognome.getText().trim());
			}
			
		});
		
		JPanel panelCentral2 = new JPanel();
		panelCentral.add(panelCentral2, BorderLayout.CENTER);
		panelCentral2.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panelCentral2.add(scrollPane, BorderLayout.CENTER);
		
		model  = new DefaultTableModel(
				new Object[][]{},
				new String[]{"id paziente", "Nome", "Cognome", "C.F.", "Nascita", "Telefono", "Email",  "Prezzo", "Credito"}
			);
		
		table = new JTable(model);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//POSSIBILITA' DI SELEZIONARE UN PAZIENTE:
				int rowSel = table.rowAtPoint(e.getPoint());
				idPazSel = Integer.valueOf(String.valueOf(table.getValueAt(rowSel, 0)));
				
				lblPazienteSelezionato.setVisible(false);
				lblPazienteSelezionato.setText("Il paziente selezionato è " + String.valueOf(table.getValueAt(rowSel, 1)) + " " + String.valueOf(table.getValueAt(rowSel, 2) + " " + "con codice fiscale " + String.valueOf(table.getValueAt(rowSel, 3))));
				
				btnBack.setEnabled(true);
				btnDettagli.setEnabled(true);
			}
		});
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.getColumnModel().getColumn(0).setWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		
		scrollPane.setColumnHeaderView(table);
		scrollPane.setViewportView(table);
		
		JPanel panelBottom = new JPanel();
		contentPane.add(panelBottom, BorderLayout.SOUTH);
		panelBottom.setLayout(new BorderLayout(0, 0));
		
		btnDettagli = new JButton("Dettagli");
		btnDettagli.setToolTipText("premi per mostrare i dettagli del paziente");
		btnDettagli.setEnabled(false);
		btnDettagli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//MOSTRARE I DETTAGLI DEL PAZIENTE, CAPIRE COSA PERO':
				
			}
		});
		panelBottom.add(btnDettagli, BorderLayout.EAST);
		
		btnBack = new JButton("Back");
		btnBack.setToolTipText("premi per tornare nella pagina precedente");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TORNA INDIETRO:
				setVisible(false);
				theController.paginaPrincipale.setVisible(true);
//				int risp = JOptionPane.showConfirmDialog(null, "Vuoi completare l'operazione di eliminazione del paziente scelto? ", "Finestra di avviso", JOptionPane.YES_NO_CANCEL_OPTION);
//				if(risp == JOptionPane.YES_OPTION) {
//					if(theController.eliminaPaziente(idPazSel)) {
//						JOptionPane.showMessageDialog(null, "Il paziente è stato eliminato correttamente!");
//						btnElimina.setEnabled(false);
//						btnDettagli.setEnabled(false);
//						idPazSel = 0;
//					}
//				}else {
//					JOptionPane.showMessageDialog(null, "L'operazione è stata annullata!");
//				}
			}
		});
		btnBack.setForeground(Color.BLACK);
		panelBottom.add(btnBack, BorderLayout.WEST);
		
		lblPazienteSelezionato = new JLabel("Il paziente selezionato è");
		lblPazienteSelezionato.setVisible(false);
		lblPazienteSelezionato.setHorizontalAlignment(SwingConstants.CENTER);
		lblPazienteSelezionato.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panelBottom.add(lblPazienteSelezionato, BorderLayout.NORTH);

	}

}
