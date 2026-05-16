import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;
import java.awt.SystemColor;
import javax.swing.SwingConstants;

public class FinestraInserisciPaziente extends JDialog {
	private Controller theController;
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtNome;
	private JTextField txtCognome;
	private JTextField txtCodiceFiscale;
	private JDateChooser dateChooserNascita;
	private JTextField txtTelefono;
	private JTextField txtPrezzo;
	private JButton btnIndietro;
	
	public FinestraInserisciPaziente(Controller c) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {//COSI' QUANDO LA CHIUDE CON LA X NON SI IMBALLA;
				btnIndietro.doClick();
			}
		});
		theController = c;
		
		setTitle("Finestra inserisci paziente");
		setSize(500, 350);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.activeCaption);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panelTop = new JPanel();
			panelTop.setBackground(SystemColor.info);
			contentPanel.add(panelTop, BorderLayout.NORTH);
			{
				JLabel lblWelcome = new JLabel("Inserisci un nuovo cliente");
				lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 20));
				panelTop.add(lblWelcome);
			}
		}
		{
			JPanel panelCentral = new JPanel();
			panelCentral.setBackground(SystemColor.activeCaption);
			contentPanel.add(panelCentral, BorderLayout.CENTER);
			
			JLabel lblCognome = new JLabel("Cognome");
			lblCognome.setHorizontalAlignment(SwingConstants.CENTER);
			lblCognome.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			JLabel lblCodiceFiscale = new JLabel("Codice fiscale");
			lblCodiceFiscale.setHorizontalAlignment(SwingConstants.CENTER);
			lblCodiceFiscale.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			JLabel lblDataNascita = new JLabel("Data nascita");
			lblDataNascita.setHorizontalAlignment(SwingConstants.CENTER);
			lblDataNascita.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			JLabel lblTelefono = new JLabel("Telefono");
			lblTelefono.setHorizontalAlignment(SwingConstants.CENTER);
			lblTelefono.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			JLabel lblPrezzo = new JLabel("Prezzo");
			lblPrezzo.setHorizontalAlignment(SwingConstants.CENTER);
			lblPrezzo.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			JLabel lblNome = new JLabel("Nome");
			lblNome.setHorizontalAlignment(SwingConstants.CENTER);
			lblNome.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			txtNome = new JTextField();
			txtNome.setColumns(10);
			
			txtCognome = new JTextField();
			txtCognome.setColumns(10);
			
			txtCodiceFiscale = new JTextField();
			txtCodiceFiscale.setColumns(10);
			
			dateChooserNascita = new JDateChooser();
			
			txtTelefono = new JTextField();
			txtTelefono.setColumns(10);
			
			txtPrezzo = new JTextField();
			txtPrezzo.setColumns(10);
			GroupLayout gl_panelCentral = new GroupLayout(panelCentral);
			gl_panelCentral.setHorizontalGroup(
				gl_panelCentral.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelCentral.createSequentialGroup()
						.addGap(103)
						.addGroup(gl_panelCentral.createParallelGroup(Alignment.LEADING)
							.addComponent(lblCodiceFiscale, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblDataNascita, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblCognome, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblNome, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblTelefono, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblPrezzo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGap(18)
						.addGroup(gl_panelCentral.createParallelGroup(Alignment.LEADING)
							.addComponent(txtNome, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
							.addComponent(txtCognome)
							.addComponent(txtCodiceFiscale, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
							.addComponent(txtTelefono)
							.addComponent(txtPrezzo)
							.addComponent(dateChooserNascita, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
						.addGap(156))
			);
			gl_panelCentral.setVerticalGroup(
				gl_panelCentral.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelCentral.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_panelCentral.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNome, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panelCentral.createSequentialGroup()
								.addGap(1)
								.addComponent(txtNome)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panelCentral.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblCognome)
							.addGroup(gl_panelCentral.createSequentialGroup()
								.addGap(1)
								.addComponent(txtCognome)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panelCentral.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblCodiceFiscale, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panelCentral.createSequentialGroup()
								.addGap(1)
								.addComponent(txtCodiceFiscale)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panelCentral.createParallelGroup(Alignment.LEADING)
							.addComponent(lblDataNascita, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
							.addComponent(dateChooserNascita, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panelCentral.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblTelefono, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panelCentral.createSequentialGroup()
								.addGap(1)
								.addComponent(txtTelefono)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panelCentral.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblPrezzo, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panelCentral.createSequentialGroup()
								.addGap(1)
								.addComponent(txtPrezzo)))
						.addGap(79))
			);
			panelCentral.setLayout(gl_panelCentral);
		}
		{
			JPanel panelBottom = new JPanel();
			panelBottom.setBackground(SystemColor.activeCaption);
			getContentPane().add(panelBottom, BorderLayout.SOUTH);
			panelBottom.setLayout(new BorderLayout(0, 0));
			{
				JButton btnInserisci = new JButton("Inserisci");
				btnInserisci.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//FUNZIONE CHE SERVE PER CONTROLLARE I CAMPI DI TESTO:
						if(ctrlFields()){
							//FUNZIONE CHE SERVE PER L'INSERIMENTO DI UN PAZIENTE:
							
							Paziente p = new Paziente(txtNome.getText().trim(), txtCognome.getText().trim(), txtCodiceFiscale.getText().trim(), getDate(dateChooserNascita) ,txtTelefono.getText().trim(), Double.valueOf(txtPrezzo.getText().trim()));
							if(theController.inserisciPaziente(p)) {
								JOptionPane.showMessageDialog(null, "L'inserimento è andato a buon fine!");
								
								//CLEAR FIELDS:
								clearTextFiedls();
							}else {
								JOptionPane.showMessageDialog(null, "L'inserimento non è andato a buon fine!");
							}
						}
					}
				});
				btnInserisci.setFont(new Font("Tahoma", Font.PLAIN, 14));
				btnInserisci.setActionCommand("OK");
				panelBottom.add(btnInserisci, BorderLayout.EAST);
				getRootPane().setDefaultButton(btnInserisci);
			}
			{
				btnIndietro = new JButton("Indietro");
				btnIndietro.setFont(new Font("Tahoma", Font.PLAIN, 14));
				btnIndietro.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//TORNA INDIETRO:
						setEnabled(false);
						clearTextFiedls();
						
						theController.paginaPrincipale.setVisible(true);
						theController.paginaPrincipale.setEnabled(true);
						
						//CLEAR FIELDS:
						clearTextFiedls();
					}
				});
				btnIndietro.setActionCommand("Cancel");
				panelBottom.add(btnIndietro, BorderLayout.WEST);
			}
		}
	}
	
	
//METHODS:
	//SERVE PER PULIRE I CAMPI:
	private void clearTextFiedls() {
		txtNome.setText(null);
		txtCognome.setText(null);
		txtCodiceFiscale.setText(null);
		dateChooserNascita.setDate(null);
		txtTelefono.setText(null);
		txtPrezzo.setText(null);
	}
	//SERVE PER CONTROLLARE I CAMPI SE SONO CORRETTI:
	private boolean ctrlFields() {
		if(txtNome.getText().trim().isBlank()) {
			JOptionPane.showMessageDialog(null, "Errore, il campo 'Nome' non può essere vuoto!");
			return false;
		}
		if(txtCognome.getText().trim().isBlank()) {
			JOptionPane.showMessageDialog(null, "Errore, il campo 'Cognome' non può essere vuoto!");
			return false;
		}
		if(txtCodiceFiscale.getText().trim().isBlank()) {
			JOptionPane.showMessageDialog(null, "Errore, il campo 'Codice fiscale' non può essere vuoto!");
			return false;
		}else {
			if(txtCodiceFiscale.getText().trim().length() != 16) {
				JOptionPane.showMessageDialog(null, "Errore, il codice fiscale non può avere meno o più caratteri di 16");
				return false;
			}else {
				if(! txtCodiceFiscale.getText().trim().toUpperCase().matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$")) {
					JOptionPane.showMessageDialog(null, "Errore, il formato del codice fiscale non è corretto!");
					return false;
				}
			}
		}
		if(dateChooserNascita.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Errore, il campo 'data di nascita' non può essere vuoto!");
			return false;
		}
		if(txtTelefono.getText().trim().isBlank()) {
			JOptionPane.showMessageDialog(null, "Il campo 'telefono' non può essere vuoto!");
			return false;
		}else {
			if(txtTelefono.getText().trim().length() != 10 && txtTelefono.getText().trim().length() != 11) {
				JOptionPane.showMessageDialog(null, "Errore, il numero di telefono/cellulare NON deve essere diverso di 10 o 11 cifre!");
				return false;				
			}else{
				if(! txtTelefono.getText().trim().matches("^[0-9]+$")) {
					JOptionPane.showMessageDialog(null, "Errore, il numero di telefono/cellulare deve contenere solo cifre!");
					return false;
				}
			}
		}
		if(txtPrezzo.getText().trim().isBlank()) {
			JOptionPane.showMessageDialog(null, "Il campo 'prezzo' non può essere vuoto!");
			return false;
		}else{
			if(Double.valueOf(txtPrezzo.getText().trim()) < 0) {
				JOptionPane.showMessageDialog(null, "Il prezzo non può essere minore di 0!");
				return false;
			}
		}
		return true;
	}
	
	//CONVERTI LA DATA DI UN JCOMPONENT IN UNO PER IL DB:
	private java.sql.Date getDate(JDateChooser date){
		java.sql.Date sqlDate = (date.getDate() != null)
			    ? new java.sql.Date(date.getDate().getTime())
			    : null;
		return sqlDate;
	}
	
	
}
