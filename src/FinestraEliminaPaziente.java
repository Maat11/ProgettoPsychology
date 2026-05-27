import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

public class FinestraEliminaPaziente extends JDialog {
	private Controller theController;
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton btnBack;
	private JButton btnElimina;
	private JTextField txtCognomeInserito;
	private JTable table;
	private DefaultTableModel model;
	private int idPaziente;
	private int rowSel;
	
	public FinestraEliminaPaziente(Controller c) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				//POPOLA LA TABELLA CON TUTTI I PAZIENTI:
				theController.popolaTabellaConPazienti(model, "");
			}
			@Override
			public void windowClosing(WindowEvent e) {
				btnBack.doClick();
			}
		});
		theController = c;
		
		setTitle("Finesta per l'eliminzione di un paziente");
		setSize(800, 450);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTop = new JPanel();
		contentPanel.add(panelTop, BorderLayout.NORTH);
		
		JLabel lblWelcome = new JLabel("Seleziona il paziente per eliminarlo");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 17));
		panelTop.add(lblWelcome);
		
		JPanel panelCentral = new JPanel();
		contentPanel.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCentralTop = new JPanel();
		panelCentral.add(panelCentralTop, BorderLayout.NORTH);
		panelCentralTop.setLayout(new BorderLayout(0, 0));
		
		JLabel lblWelcomeDigitaCognome = new JLabel("Digita il cognome del paziente");
		lblWelcomeDigitaCognome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelCentralTop.add(lblWelcomeDigitaCognome);
		
		txtCognomeInserito = new JTextField();
		
		txtCognomeInserito.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				theController.popolaTabellaConPazienti(model, txtCognomeInserito.getText().trim());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				theController.popolaTabellaConPazienti(model, txtCognomeInserito.getText().trim());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				theController.popolaTabellaConPazienti(model, txtCognomeInserito.getText().trim());
			}
			
		});
		
		txtCognomeInserito.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtCognomeInserito.setToolTipText("⚠ Attenzione alle maisucole e minuscole");
		txtCognomeInserito.setHorizontalAlignment(SwingConstants.CENTER);
		panelCentralTop.add(txtCognomeInserito, BorderLayout.EAST);
		txtCognomeInserito.setColumns(10);
		
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
		
		//NASCONDIAMO LA PRIMA COLONNA ALL'UTENTE:
		table.getColumnModel().getColumn(0).setWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rowSel = table.rowAtPoint(e.getPoint());
				if(rowSel != 0) {
					//SE IL PULSANTE HA SELEZIONATO UNA TUPLA ALLORA:
					idPaziente = Integer.valueOf(String.valueOf(table.getValueAt(rowSel, 0)));
					
					btnElimina.setEnabled(true);
				}
			}
		});
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setColumnHeaderView(table);
		scrollPane.setViewportView(table);
		
		JPanel panelBottom = new JPanel();
		contentPanel.add(panelBottom, BorderLayout.SOUTH);
		panelBottom.setLayout(new BorderLayout(0, 0));
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TORNA INDIETRO:
				setVisible(false);
				
				theController.paginaPaziente.setVisible(true);
				theController.paginaPaziente.setEnabled(true);
			}
		});
		panelBottom.add(btnBack, BorderLayout.WEST);
		
		btnElimina = new JButton("Elimina");
		btnElimina.setEnabled(false);
		btnElimina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ELIMINA IL PAZIENTE SELEZIONATO:
				int confirm = JOptionPane.showConfirmDialog(null, "Il paziente selezionato," + String.valueOf(table.getValueAt(rowSel, 1)) +" "+ String.valueOf(table.getValueAt(rowSel, 2)) +" "+ "sarà eliminato definitivamente, confermare l'operazione?", "Messaggio di conferma operazione", JOptionPane.YES_NO_CANCEL_OPTION);
				if(confirm == JOptionPane.YES_OPTION) {
					if(theController.eliminaPaziente(idPaziente)) {
						//AGGIORNA LA TABELLA:
						theController.popolaTabellaConPazienti(model, "");
						
						//MESSAGGIO DI AVVENUTA ELIMINAZIONE DEL PAZIENTE:
						JOptionPane.showMessageDialog(null, "Il paziente selezionato è stato eliminato correttamente!");
						
						//SET VAR = 0
						idPaziente = 0;
						btnElimina.setEnabled(false);
						rowSel = 0;
					}
				}else {
					JOptionPane.showMessageDialog(null, "L'operazione è stata annullata!");
				}
			}
		});
		panelBottom.add(btnElimina, BorderLayout.EAST);
	}

}
