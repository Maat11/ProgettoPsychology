import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class FinestraNotaRapida extends JDialog {
	private Controller theController;
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblWelcome;
	private JButton btnInserisci;
	private JButton btnBack;
	private JLabel lblNomeCognomePaz;
	private JTextField txtTitoloNota;
	private Paziente paziente;
	private JTextArea txtAreaNota;
	
	public FinestraNotaRapida(Controller c, int idPaz, int idAppuntamento) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				//FUNZIONE CHE SERVE PER TROVARE IL PAZIENTE, COSI' HO I SUOI DATI SEMPRE A PORTATA DI MANO NEL CASO:
				paziente = theController.trovaPaziente(idPaz);
				
				if(paziente == null) {
					JOptionPane.showConfirmDialog(null, "Mi dispiace, qualcosa è andato storto, sarai inidirizzato alla pagina precedente!", "Messaggio di ERRORE", JOptionPane.YES_NO_CANCEL_OPTION);
					btnBack.doClick();
				}
				
				//NEL CASO DOVESSE ESSERE TUTTO OK PROCEDI AVANTI:
				lblWelcome.setText("Inserisci una nota rapida per il paziente" +" ");
				lblNomeCognomePaz.setText(paziente.getNome() + " " + paziente.getCognome());
				
			}
			@Override
			public void windowClosing(WindowEvent e) {
				btnBack.doClick();
			}
		});
		theController = c;
		
		setTitle("Finestra nota rapida");
		setSize(600, 480);
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
				lblWelcome = new JLabel();
				lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 17));
				panelTop.add(lblWelcome);
			}
			{
				lblNomeCognomePaz = new JLabel();
				lblNomeCognomePaz.setForeground(SystemColor.textHighlight);
				lblNomeCognomePaz.setFont(new Font("Tahoma", Font.PLAIN, 17));
				panelTop.add(lblNomeCognomePaz);
			}
		}
		{
			JPanel panelCentral = new JPanel();
			contentPanel.add(panelCentral, BorderLayout.CENTER);
			panelCentral.setLayout(new BorderLayout(0, 0));
			
			JPanel panelCentralTop = new JPanel();
			panelCentralTop.setBackground(SystemColor.inactiveCaptionBorder);
			panelCentral.add(panelCentralTop, BorderLayout.NORTH);
			panelCentralTop.setLayout(new BorderLayout(0, 0));
			
			JLabel lblMessaggio2 = new JLabel("Compila i seguenti campi");
			lblMessaggio2.setForeground(SystemColor.textHighlight);
			lblMessaggio2.setFont(new Font("Tahoma", Font.PLAIN, 13));
			panelCentralTop.add(lblMessaggio2);
			
			JPanel panelCentral2 = new JPanel();
			panelCentral.add(panelCentral2, BorderLayout.CENTER);
			
			JLabel lblNewLabel = new JLabel("Titolo nota");
			lblNewLabel.setForeground(SystemColor.textHighlight);
			lblNewLabel.setToolTipText("Questo campo non è obbligatorio");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
			
			txtTitoloNota = new JTextField();
			txtTitoloNota.setForeground(Color.RED);
			txtTitoloNota.setHorizontalAlignment(SwingConstants.CENTER);
			txtTitoloNota.setFont(new Font("Tahoma", Font.PLAIN, 14));
			txtTitoloNota.setToolTipText("Inserisci un titolo alla nota rapida ");
			txtTitoloNota.setColumns(10);
			
			JLabel lblNewLabel_2 = new JLabel("Facoltativo");
			lblNewLabel_2.setForeground(Color.LIGHT_GRAY);
			lblNewLabel_2.setToolTipText("L'aggiunta del titolo di una nota è facoltativo");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNewLabel_2.setEnabled(false);
			
			JPanel panelTxtArea = new JPanel();
			GroupLayout gl_panelCentral2 = new GroupLayout(panelCentral2);
			gl_panelCentral2.setHorizontalGroup(
				gl_panelCentral2.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelCentral2.createSequentialGroup()
						.addContainerGap()
						.addComponent(panelTxtArea, GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE)
						.addContainerGap())
					.addGroup(Alignment.TRAILING, gl_panelCentral2.createSequentialGroup()
						.addGap(187)
						.addGroup(gl_panelCentral2.createParallelGroup(Alignment.TRAILING)
							.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
							.addComponent(txtTitoloNota, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE))
						.addGap(18)
						.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
						.addGap(103))
			);
			gl_panelCentral2.setVerticalGroup(
				gl_panelCentral2.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelCentral2.createSequentialGroup()
						.addGap(24)
						.addComponent(lblNewLabel)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panelCentral2.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNewLabel_2)
							.addComponent(txtTitoloNota, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addComponent(panelTxtArea, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
						.addContainerGap())
			);
			panelTxtArea.setLayout(new BorderLayout(0, 0));
			
			JLabel lblNota = new JLabel("Nota");
			lblNota.setHorizontalAlignment(SwingConstants.CENTER);
			lblNota.setForeground(SystemColor.textHighlight);
			lblNota.setFont(new Font("Tahoma", Font.PLAIN, 14));
			panelTxtArea.add(lblNota, BorderLayout.NORTH);
			
			txtAreaNota = new JTextArea();
			txtAreaNota.setWrapStyleWord(true);
			txtAreaNota.setLineWrap(true);
			txtAreaNota.setFont(new Font("Tahoma", Font.PLAIN, 14));
			panelTxtArea.add(txtAreaNota, BorderLayout.CENTER);
			panelCentral2.setLayout(gl_panelCentral2);
		}
		{
			JPanel panelBottom = new JPanel();
			contentPanel.add(panelBottom, BorderLayout.SOUTH);
			panelBottom.setLayout(new BorderLayout(0, 0));
			{
				btnBack = new JButton("Back");
				btnBack.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//TORNA INDIETRO:
						setVisible(false);
						
						theController.paginaPrincipale.setVisible(true);
						theController.paginaPrincipale.setEnabled(true);
					}
				});
				panelBottom.add(btnBack, BorderLayout.WEST);
			}
			{
				btnInserisci = new JButton("Inserisci");
				btnInserisci.setToolTipText("Per inserire una nota devi inserire del teso nella nota");
				btnInserisci.setEnabled(false);
				btnInserisci.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//INSERISCI NOTA:
						NotaRapida notaRap = new NotaRapida(idAppuntamento, idPaz, txtAreaNota.getText().trim());
						
						int confirm = JOptionPane.showConfirmDialog(null, "Procede per l'inserimento di una nota rapida per il paziente scelto?", "Finestra di avviso", JOptionPane.YES_NO_CANCEL_OPTION);
						
						if(confirm == JOptionPane.YES_OPTION) {
							if(! txtTitoloNota.getText().trim().isBlank()) {
								notaRap.setParolaChiave(txtTitoloNota.getText().trim());
							}
							if(theController.inserisciNotaRapida(notaRap)) {
								JOptionPane.showMessageDialog(null, "La nota rapida è stata inserita correttamente!");
								
								//PULISCI I VARI CAMPI:
								clearFields();
								
								//SET ENABLE FALSE IL PULSANTE PER INSERIRE UNA NOTA:
								btnInserisci.setEnabled(false);
							}
						}else {
							JOptionPane.showMessageDialog(null, "L'operazione è stata annullata");
						}
					}
				});
				panelBottom.add(btnInserisci, BorderLayout.EAST);
			}
		}
	}
//METHODS:
	private void clearFields() {
		txtTitoloNota.setText(null);
		txtAreaNota.setText(null);
	}
}
