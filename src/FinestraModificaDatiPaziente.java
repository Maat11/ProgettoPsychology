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
import java.sql.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import com.toedter.calendar.JDateChooser;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FinestraModificaDatiPaziente extends JDialog {
	private Controller theController;
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton btnBack;
	private JButton btnModifica;
	private JTextField txtNome;
	private JLabel lblNome;
	private JTextField txtCognome;
	private DefaultTableModel model;
	private JTextField txtTelefono;
	private JTextField txtEmail;
	private JTextField txtPrezzo;
	private JTable table;
	private JTextField txtCerca;
	private JDateChooser dateChooser;
	private Paziente pazienteSel;
	
	public FinestraModificaDatiPaziente(Controller c) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				//POPOLA LA TABELLA + ALTRO:
				theController.popolaTabellaConPazienti(model, "");
			}
			@Override
			public void windowClosing(WindowEvent e) {
				//TORNA INDIETRO:
				btnBack.doClick();
			}
		});
		theController = c;
		
		setTitle("Finestra modifica dati paziente");
		setSize(1280, 565);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panelTop = new JPanel();
			panelTop.setBackground(SystemColor.info);
			contentPanel.add(panelTop, BorderLayout.NORTH);
			{
				JLabel lblWelcome = new JLabel("Riempi SOLO i campi che vuoi modificare");
				lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 17));
				panelTop.add(lblWelcome);
			}
		}
		{
			JPanel panelCentral = new JPanel();
			contentPanel.add(panelCentral, BorderLayout.CENTER);
			panelCentral.setLayout(new BorderLayout(0, 0));
			{
				JPanel panelCentralLeft = new JPanel();
				panelCentral.add(panelCentralLeft, BorderLayout.WEST);
				{
					lblNome = new JLabel("Nome");
					lblNome.setHorizontalAlignment(SwingConstants.CENTER);
					lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
				}
				{
					txtNome = new JTextField();
					txtNome.setEnabled(false);
					txtNome.setColumns(10);
				}
				
				JLabel lblCognome = new JLabel("Cognome");
				lblCognome.setHorizontalAlignment(SwingConstants.CENTER);
				lblCognome.setFont(new Font("Tahoma", Font.PLAIN, 14));
				
				txtCognome = new JTextField();
				txtCognome.setEnabled(false);
				txtCognome.setColumns(10);
				JLabel lblDataNascita = new JLabel("Nascita");
				lblDataNascita.setHorizontalAlignment(SwingConstants.CENTER);
				lblDataNascita.setFont(new Font("Tahoma", Font.PLAIN, 14));
				JLabel lblTelefono = new JLabel("Telefono");
				lblTelefono.setHorizontalAlignment(SwingConstants.CENTER);
				lblTelefono.setFont(new Font("Tahoma", Font.PLAIN, 14));
				JLabel lblEmail = new JLabel("Email");
				lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
				lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
				JLabel lblPrezzo = new JLabel("Prezzo");
				lblPrezzo.setHorizontalAlignment(SwingConstants.CENTER);
				lblPrezzo.setToolTipText("il credito non sarà aggiornato col nuovo credito");
				lblPrezzo.setFont(new Font("Tahoma", Font.PLAIN, 14));
				
				dateChooser = new JDateChooser();
				dateChooser.setEnabled(false);
				
				txtTelefono = new JTextField();
				txtTelefono.setEnabled(false);
				txtTelefono.setColumns(10);
				
				txtEmail = new JTextField();
				txtEmail.setEnabled(false);
				txtEmail.setColumns(10);
				
				txtPrezzo = new JTextField();
				txtPrezzo.setEnabled(false);
				txtPrezzo.setColumns(10);
				
				JLabel lblNewLabel = new JLabel("Campi di testo");
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
				GroupLayout gl_panelCentralLeft = new GroupLayout(panelCentralLeft);
				gl_panelCentralLeft.setHorizontalGroup(
					gl_panelCentralLeft.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelCentralLeft.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panelCentralLeft.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelCentralLeft.createSequentialGroup()
									.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
									.addContainerGap())
								.addGroup(gl_panelCentralLeft.createSequentialGroup()
									.addGroup(gl_panelCentralLeft.createParallelGroup(Alignment.LEADING, false)
										.addComponent(lblNome, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblPrezzo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblEmail, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblTelefono, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblCognome, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblDataNascita, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addGap(34)
									.addGroup(gl_panelCentralLeft.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panelCentralLeft.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_panelCentralLeft.createParallelGroup(Alignment.TRAILING)
												.addComponent(dateChooser, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
												.addComponent(txtTelefono, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
												.addGroup(gl_panelCentralLeft.createSequentialGroup()
													.addComponent(txtEmail, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
													.addPreferredGap(ComponentPlacement.RELATED))
												.addComponent(txtPrezzo, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)))
										.addComponent(txtCognome, 102, 102, 102)
										.addComponent(txtNome, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
									.addGap(60))))
				);
				gl_panelCentralLeft.setVerticalGroup(
					gl_panelCentralLeft.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panelCentralLeft.createSequentialGroup()
							.addGap(38)
							.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(57)
							.addGroup(gl_panelCentralLeft.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNome)
								.addComponent(txtNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_panelCentralLeft.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCognome)
								.addComponent(txtCognome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelCentralLeft.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDataNascita)
								.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(15)
							.addGroup(gl_panelCentralLeft.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblTelefono)
								.addComponent(txtTelefono, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_panelCentralLeft.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblEmail)
								.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_panelCentralLeft.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblPrezzo)
								.addComponent(txtPrezzo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(148))
				);
				panelCentralLeft.setLayout(gl_panelCentralLeft);
			}
			
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
			
			// SERVE A NASCONDERE L'ID DEL PAZIENTE, LA COLONNA:
			table.getColumnModel().getColumn(0).setWidth(0);
			table.getColumnModel().getColumn(0).setMaxWidth(0);
			table.getColumnModel().getColumn(0).setMinWidth(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(0);
			
			// SERVE A NASCONDERE IL CREDITO DEL PAZIENTE, LA COLONNA:
			table.getColumnModel().getColumn(8).setWidth(0);
			table.getColumnModel().getColumn(8).setMaxWidth(0);
			table.getColumnModel().getColumn(8).setMinWidth(0);
			table.getColumnModel().getColumn(8).setPreferredWidth(0);
			
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//SERVE PER CAPIRE QUALE PAZIENTE VUOLE MODIFICARE:
					int selRow = table.rowAtPoint(e.getPoint());
					
					//PRENDO L'ID E PRENDO IL PAZIENTE SELEZIONATO:
					pazienteSel = theController.trovaPaziente(Integer.valueOf(String.valueOf(table.getValueAt(selRow, 0))));
					
					//PRECOMPILO I VARI CAMPI:
					setFields(pazienteSel);
					
					//LI RENDO MODIFICABILI:
					setEnabledTureIntoFields();
					
					//RENDI DISPONIBILE IL PULSANTE:
					btnModifica.setEnabled(true);					
				}
			});
			table.setFont(new Font("Tahoma", Font.PLAIN, 15));
			scrollPane.setColumnHeaderView(table);
			scrollPane.setViewportView(table);
			
			JPanel panel = new JPanel();
			panelCentral2.add(panel, BorderLayout.NORTH);
			panel.setLayout(new BorderLayout(0, 0));
			
			JLabel lblCercaPerCognome = new JLabel("Cerca in base al cognome");
			lblCercaPerCognome.setForeground(SystemColor.textHighlight);
			lblCercaPerCognome.setFont(new Font("Tahoma", Font.PLAIN, 13));
			panel.add(lblCercaPerCognome);
			
			txtCerca = new JTextField();
			txtCerca.setToolTipText("Inserisci tutto");
			panel.add(txtCerca, BorderLayout.EAST);
			txtCerca.setColumns(10);
			
			txtCerca.getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void insertUpdate(DocumentEvent e) {
					theController.popolaTabellaConPazienti(model, txtCerca.getText().trim());
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					theController.popolaTabellaConPazienti(model, txtCerca.getText().trim());
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					theController.popolaTabellaConPazienti(model, txtCerca.getText().trim());
				}
				
			});
			
		}
		{
			JPanel panelBottom = new JPanel();
			contentPanel.add(panelBottom, BorderLayout.SOUTH);
			panelBottom.setLayout(new BorderLayout(0, 0));
			{
				btnModifica = new JButton("Modifica");
				btnModifica.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//MODIFICA IL PAZIENTE:
						int confirm = JOptionPane.showConfirmDialog(null, "Confermare l'operazione di modifica?", "Avviso di conferma", JOptionPane.YES_NO_CANCEL_OPTION);
						if(confirm == JOptionPane.YES_OPTION) {
							//SETTA TUTTI I NUOVI DATI DAI CAMPI DI TESTO:
							if(ctrlTxtFields()) {
								setPazienteWithNewFields(pazienteSel);
								if(theController.modificaPaziente(pazienteSel)) {
									JOptionPane.showMessageDialog(null, "L'operazione è andata a buo fine");
									setEnabledFalseIntoFields();
									clearTxtFIelds();
								}else {
									JOptionPane.showMessageDialog(null, "Errore");
								}
							}
						}else {
							JOptionPane.showMessageDialog(null, "L'operazione è stata annullata");
						}
					}
				});
				btnModifica.setEnabled(false);
				panelBottom.add(btnModifica, BorderLayout.EAST);
			}
			{
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
			}
		}
	}
	
//METHODS:
	//SERVE PER PRENDERE I VALORI DEI CAMPI DI TESTO E INSERIRLI NEL PAZIENTE SELEZIOANTO:
	private void setPazienteWithNewFields(Paziente p) {
		p.setNome(txtNome.getText().trim());
		p.setCognome(txtCognome.getText().trim());
		p.setDataNascita(getDateAndConvert(dateChooser));
		p.setEmail(txtEmail.getText().trim());
		p.setTelefono(txtTelefono.getText().trim());
		p.setPrezzo(Double.valueOf(txtPrezzo.getText().trim()));
	}
	//SERVE PER SETTARE I CAMPI CON I VALORI GIA' ESISTENTI:
	private void setFields(Paziente p) {
		txtNome.setText(p.getNome());
		txtCognome.setText(p.getCognome());
		dateChooser.setDate(p.getDataNascita());
		txtEmail.setText(p.getEmail());
		txtTelefono.setText(p.getTelefono());
		txtPrezzo.setText(String.valueOf(p.getPrezzo()));
	}
	
	//SERVE PER PULIRE I CAMPI:
	private void clearTxtFIelds(){
		txtNome.setText(null);
		txtCognome.setText(null);
		dateChooser.setDate(null);
		txtTelefono.setText(null);
		txtPrezzo.setText(null);
		txtEmail.setText(null);
	}
	
	//SERVE PER SETTARE I CAMPI DI TESTO ENABLED = TRUE:
	private void setEnabledTureIntoFields() {
		txtNome.setEnabled(true);
		txtCognome.setEnabled(true);
		dateChooser.setEnabled(true);
		txtTelefono.setEnabled(true);
		txtPrezzo.setEnabled(true);
		txtEmail.setEnabled(true);
	}
	
	//SERVE PER SETTARE I CAMPI DI TESTO ENABLE = FALSE:
	private void setEnabledFalseIntoFields() {
		txtNome.setEnabled(false);
		txtCognome.setEnabled(false);
		dateChooser.setEnabled(false);
		txtTelefono.setEnabled(false);
		txtPrezzo.setEnabled(false);
		txtEmail.setEnabled(false);
	}
	
	//CONVERTI LA DATA:
	private java.sql.Date getDateAndConvert(JDateChooser date){
		java.sql.Date sqlDate = (date.getDate() != null)
			    ? new java.sql.Date(date.getDate().getTime())
			    : null;
		return sqlDate;
	}
	
	//CONTROLLO I CAMPI DI TESTO:
	private boolean ctrlTxtFields(){
		if(! txtPrezzo.getText().trim().isBlank()){
			if(Double.valueOf(txtPrezzo.getText().trim()) < 0){
				JOptionPane.showMessageDialog(null,"Errore, il prezzo non può essere minore di 0!");
				return false;
			}
		}
		if(! txtTelefono.getText().trim().isBlank()){
			if(txtTelefono.getText().trim().length() != 10 && txtTelefono.getText().trim().length() != 11){
				JOptionPane.showMessageDialog(null,"Errore, il numero di telefono/cellulare non è valido!");
				return false;
			}else{
				if(! txtTelefono.getText().trim().matches("^[0-9]+$")) {
					JOptionPane.showMessageDialog(null, "Errore, il numero di telefono/cellulare deve contenere solo cifre!");
					return false;
				}
			}	
		}
		if(! txtEmail.getText().trim().isBlank()) {
			if(! txtEmail.getText().trim().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
				JOptionPane.showMessageDialog(null, "Errore, l'email inserita non è valida!");
				return false;
			}
		}
		return true;
	}
	
	
}
