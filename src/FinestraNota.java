import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FinestraNota extends JDialog {
	private Controller theController;
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel panelCentralTop;
	private JTextField txtTitolo;
	private JTable table;
	private DefaultTableModel model;
	private JButton btnInserisci;
	private JButton btnBack;
	private JTextField txtCognome;
	private JTextArea textArea;
	private int idPazSel;
	private JLabel lblPazienteSel;
	
	public FinestraNota(Controller c) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				theController.popolaTabellaConPazienti(model, "");
			}
			@Override
			public void windowClosing(WindowEvent e) {
				//TORNA INDIETRO:
				btnBack.doClick();
			}
		});
		theController = c;
		
		setTitle("Finesra per l'inserimento di una nota");
		setSize(850, 630);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panelTop = new JPanel();
			panelTop.setBackground(SystemColor.info);
			contentPanel.add(panelTop, BorderLayout.NORTH);
			panelTop.setLayout(new BorderLayout(0, 0));
			{
				JLabel lblWelcome = new JLabel("Selziona il paziente");
				lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 18));
				lblWelcome.setForeground(Color.BLUE);
				lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
				panelTop.add(lblWelcome, BorderLayout.CENTER);
			}
		}
		{
			JPanel panelCentral = new JPanel();
			panelCentral.setBackground(new Color(176, 224, 230));
			contentPanel.add(panelCentral, BorderLayout.CENTER);
			{
				panelCentralTop = new JPanel();
				panelCentralTop.setLayout(new BorderLayout(0, 0));
				{
					JScrollPane scrollPane = new JScrollPane();
					panelCentralTop.add(scrollPane);
					
					model  = new DefaultTableModel(
							new Object[][]{},
							new String[]{"id paziente", "Nome", "Cognome", "C.F.", "Nascita", "Telefono", "Email"}
						);
					
					table = new JTable(model);
					table.setBorder(null);
					table.setBackground(new Color(255, 240, 245));
					
					//NON MOSTRO L'ID DEL PAZIENTE:
					table.getColumnModel().getColumn(0).setWidth(0);
					table.getColumnModel().getColumn(0).setMaxWidth(0);
					table.getColumnModel().getColumn(0).setMinWidth(0);
					table.getColumnModel().getColumn(0).setPreferredWidth(0);
					
					table.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							int selRow = table.rowAtPoint(e.getPoint());
							
							idPazSel = Integer.valueOf(String.valueOf(table.getValueAt(selRow, 0)));
							
							lblPazienteSel.setText("Il paziente selezionato è: "+String.valueOf(table.getValueAt(selRow, 1)) +" "+ String.valueOf(table.getValueAt(selRow, 2)));
							
						}
					});
					table.setFont(new Font("Tahoma", Font.PLAIN, 14));
					scrollPane.setViewportView(table);
					scrollPane.setViewportView(table);
				}
			}
			
			JLabel lblTitolo = new JLabel("Titolo");
			lblTitolo.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblTitolo.setHorizontalAlignment(SwingConstants.CENTER);
			
			txtTitolo = new JTextField();
			txtTitolo.setColumns(10);
			
			JLabel lblNota = new JLabel("Nota");
			lblNota.setHorizontalAlignment(SwingConstants.CENTER);
			lblNota.setFont(new Font("Tahoma", Font.PLAIN, 16));
			
			textArea = new JTextArea();
			textArea.setWrapStyleWord(true);
			textArea.setLineWrap(true);
			textArea.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					btnInserisci.setEnabled(true);
				}
			});
			lblPazienteSel = new JLabel("Il paziente selezioanto è");
			lblPazienteSel.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblPazienteSel.setHorizontalAlignment(SwingConstants.CENTER);
			GroupLayout gl_panelCentral = new GroupLayout(panelCentral);
			gl_panelCentral.setHorizontalGroup(
				gl_panelCentral.createParallelGroup(Alignment.LEADING)
					.addComponent(panelCentralTop, GroupLayout.DEFAULT_SIZE, 853, Short.MAX_VALUE)
					.addGroup(gl_panelCentral.createSequentialGroup()
						.addGap(209)
						.addComponent(lblTitolo, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
						.addGap(18)
						.addComponent(txtTitolo, GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
						.addGap(243))
					.addGroup(gl_panelCentral.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblNota, GroupLayout.DEFAULT_SIZE, 833, Short.MAX_VALUE)
						.addContainerGap())
					.addGroup(gl_panelCentral.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblPazienteSel, GroupLayout.DEFAULT_SIZE, 833, Short.MAX_VALUE)
						.addContainerGap())
					.addGroup(gl_panelCentral.createSequentialGroup()
						.addContainerGap()
						.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 833, Short.MAX_VALUE)
						.addContainerGap())
			);
			gl_panelCentral.setVerticalGroup(
				gl_panelCentral.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelCentral.createSequentialGroup()
						.addComponent(panelCentralTop, GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(lblPazienteSel, GroupLayout.DEFAULT_SIZE, 17, Short.MAX_VALUE)
						.addGap(19)
						.addGroup(gl_panelCentral.createParallelGroup(Alignment.BASELINE)
							.addGroup(gl_panelCentral.createSequentialGroup()
								.addGap(2)
								.addComponent(lblTitolo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addComponent(txtTitolo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(26)
						.addComponent(lblNota, GroupLayout.PREFERRED_SIZE, 19, Short.MAX_VALUE)
						.addGap(18)
						.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
						.addContainerGap())
			);
			{
				JPanel panelCentralTop2 = new JPanel();
				panelCentralTop2.setBackground(new Color(176, 224, 230));
				panelCentralTop.add(panelCentralTop2, BorderLayout.NORTH);
				panelCentralTop2.setLayout(new BorderLayout(0, 0));
				{
					JLabel lblCerca = new JLabel("Cerca paziente");
					lblCerca.setFont(new Font("Tahoma", Font.PLAIN, 13));
					panelCentralTop2.add(lblCerca, BorderLayout.WEST);
				}
				{
					txtCognome = new JTextField();
					txtCognome.setFont(new Font("Tahoma", Font.PLAIN, 13));
					panelCentralTop2.add(txtCognome, BorderLayout.EAST);
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
					
				}
			}
			panelCentral.setLayout(gl_panelCentral);
		}
		{
			JPanel panelBottom = new JPanel();
			panelBottom.setBackground(new Color(176, 224, 230));
			contentPanel.add(panelBottom, BorderLayout.SOUTH);
			panelBottom.setLayout(new BorderLayout(0, 0));
			{
				btnBack = new JButton("Back");
				btnBack.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						
						theController.paginaPaziente.setVisible(true);
						theController.paginaPaziente.setEnabled(true);
					}
				});
				panelBottom.add(btnBack, BorderLayout.WEST);
			}
			{
				btnInserisci = new JButton("Inserisci");
				btnInserisci.setEnabled(false);
				btnInserisci.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//INSERISCI LA NOTA:
						if (ctrlFields()) {
							Nota nota = new Nota(idPazSel, txtTitolo.getText().trim(), textArea.getText().trim());
							if(theController.inserisciNota(nota)) {
								JOptionPane.showMessageDialog(null, "L'operazione è andata a buon fine");
								clearFields();
							}
						}
					}
				});
				panelBottom.add(btnInserisci, BorderLayout.EAST);
			}
		}
	}
	
//METHODS:
	//SERVE A PULIRE I VARI CAMPI DI TESTO:
	private void clearFields() {
		txtTitolo.setText(null);
		textArea.setText(null);
	}
	
	//SERVE PER CONTROLLARE I VARI CAMPI SE HANNO DEL TESTO ALL'INTERNO PERCHE' E' OBBLIGATORIO COMPILARE TUTTI I CAMPI:
	private boolean ctrlFields() {
		if (txtTitolo.getText().isBlank()) {
			JOptionPane.showMessageDialog(null, "Errore, il campo titolo non può essere vuoto!");
			return false;
		}
		if(textArea.getText().trim().isBlank()) {
			JOptionPane.showMessageDialog(null, "Erorre, il campo nota non può essere vuoto!");
			return false;
		}
		return true;
	}
}
