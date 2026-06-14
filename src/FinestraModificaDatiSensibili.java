import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FinestraModificaDatiSensibili extends JDialog {
	private Controller theController;
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton btnBack;
	private JButton btnModifica;
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtTelefono;
	private JTextField txtEmail;
	private int idPaz;
	
	public FinestraModificaDatiSensibili(Controller c) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				//POPOLA TABELLA:
				theController.popolaTabellaConPazienti(model, "");
			}
			@Override
			public void windowClosing(WindowEvent e) {
				btnBack.doClick();
			}
		});
		theController = c;
		
		setTitle("Mdifica dati sensibili");
		setSize(950, 550);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTop = new JPanel();
		panelTop.setBackground(SystemColor.info);
		contentPanel.add(panelTop, BorderLayout.NORTH);
		
		JLabel lblWelcome = new JLabel("Modifica i dati sensibili");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelTop.add(lblWelcome);
		
		JPanel panelCentral = new JPanel();
		contentPanel.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCentralSx = new JPanel();
		panelCentral.add(panelCentralSx, BorderLayout.WEST);
		
		JLabel lblWelcome2 = new JLabel("Compila i vampi interessati");
		lblWelcome2.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTelefono.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		GroupLayout gl_panelCentralSx = new GroupLayout(panelCentralSx);
		gl_panelCentralSx.setHorizontalGroup(
			gl_panelCentralSx.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelCentralSx.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelCentralSx.createParallelGroup(Alignment.LEADING)
						.addComponent(lblWelcome2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_panelCentralSx.createSequentialGroup()
							.addGroup(gl_panelCentralSx.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblEmail, GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
								.addComponent(lblTelefono, GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(gl_panelCentralSx.createParallelGroup(Alignment.LEADING)
								.addComponent(txtTelefono, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtEmail, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		gl_panelCentralSx.setVerticalGroup(
			gl_panelCentralSx.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCentralSx.createSequentialGroup()
					.addGap(26)
					.addComponent(lblWelcome2)
					.addGap(47)
					.addGroup(gl_panelCentralSx.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTelefono)
						.addComponent(txtTelefono, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panelCentralSx.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEmail)
						.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(314, Short.MAX_VALUE))
		);
		panelCentralSx.setLayout(gl_panelCentralSx);
		
		JPanel panelCentralCentral = new JPanel();
		panelCentral.add(panelCentralCentral, BorderLayout.CENTER);
		panelCentralCentral.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panelCentralCentral.add(scrollPane, BorderLayout.CENTER);
		
		model  = new DefaultTableModel(
				new Object[][]{},
				new String[]{"id paziente", "Nome", "Cognome", "C.F.", "Nascita", "Telefono", "Email",  "Prezzo", "Credito"}
			);
		
		table = new JTable(model);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int rowSel = table.rowAtPoint(e.getPoint());
				if(rowSel != -1) {
					txtTelefono.setText(String.valueOf(table.getValueAt(rowSel, 5)));
					txtEmail.setText(String.valueOf(table.getValueAt(rowSel, 6)));
					
					idPaz = Integer.valueOf(String.valueOf(table.getValueAt(rowSel, 0)));
					
				}
			}
		});
		
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
				
				theController.finestraModificaDatiPaziente.setVisible(true);
			}
		});
		panelBottom.add(btnBack, BorderLayout.WEST);
		
		btnModifica = new JButton("Modifica");
		btnModifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//CONTROLLO I VARI CAMPI SE SONO COMPILATI CORRETTAMENTE:
				int confirm = JOptionPane.showConfirmDialog(null, "Modificare il/i dato/i del paziente selezionato?", "Conferma operazione", JOptionPane.YES_NO_CANCEL_OPTION);
				if(confirm == JOptionPane.YES_OPTION) {
					if(ctrlTelefono()) {
						//MODIFICA DEL NUMERO DI TELEFONO/CELLULARE:
						if(theController.modificaTelefono(idPaz, txtTelefono.getText().trim())) {
							JOptionPane.showMessageDialog(null, "La modifica del numero di telefono è avvenunta correttamente!");
						}
					}
					if(ctrlEmail()) {
						//MODIFICA DELL'EMAIL:
						
					}
				}else {
					JOptionPane.showMessageDialog(null, "L'operazione è stata annullata!");
				}
				//PULISCI I CAMPI DI TESTO:
				clearField();
			}
		});
		panelBottom.add(btnModifica, BorderLayout.EAST);
	}
	
//METHODS:
	//SERVE PER CONTROLLARE SE IL CAMPO TELEFONO E' COMPILATO CORRETTAMENTE:
	private boolean ctrlTelefono() {
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
		return true;
	}
	
	//SERVE PER CONTROLLARE SE IL CAMPO EMAIL E' COMPILATO CORRETTAMENTE:
	private boolean ctrlEmail() {
		if(! txtEmail.getText().trim().isBlank()) {
			if(! txtEmail.getText().trim().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
				JOptionPane.showMessageDialog(null, "Errore, l'email inserita non è valida!");
				return false;
			}
		}
		return true;
	}
	
	//SERVE PER PULIRE I CAMPI:
	private void clearField() {
		txtTelefono.setText(null);
		txtEmail.setText(null);
	}
}
