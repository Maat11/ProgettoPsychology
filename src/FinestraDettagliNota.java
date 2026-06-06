import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.UIManager;
import java.awt.Color;

public class FinestraDettagliNota extends JDialog {
	private Controller theController;
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtTitolo;
	private JButton btnModifica;
	private JButton btnBack;
	private JTextArea textNota;
	private JLabel lblTitolo;
	private JLabel lblNota;
	private Nota nota;

	public FinestraDettagliNota(int idNota, Controller c) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				try{
					nota = theController.prendiNotaSelezionata(idNota);
				}catch(NullPointerException exc) {
					JOptionPane.showMessageDialog(null, "Errore! la nota non può essere visto nei dettagli, contattare lo sviluppatore");
				}
				
				if(nota != null) {
					txtTitolo.setText(nota.getParolaChiave());
					textNota.setText(nota.getNota());
				}
				
			}
			@Override
			public void windowClosing(WindowEvent e) {
				btnBack.doClick();
			}
		});
		theController = c;
		
		setTitle("Dettagli nota");
		setSize(800, 550);
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
				JLabel lblWelcome = new JLabel("Dettagli della nota scelta");
				lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 18));
				panelTop.add(lblWelcome);
			}
		}
		{
			JPanel panelCentral = new JPanel();
			panelCentral.setBackground(new Color(255, 192, 203));
			contentPanel.add(panelCentral, BorderLayout.CENTER);
			lblTitolo = new JLabel("Titolo");
			lblTitolo.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTitolo.setForeground(SystemColor.textHighlight);
			lblTitolo.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtTitolo = new JTextField();
			txtTitolo.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtTitolo.setColumns(10);
			lblNota = new JLabel("Nota");
			lblNota.setForeground(SystemColor.textHighlight);
			lblNota.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			textNota = new JTextArea();
			textNota.setWrapStyleWord(true);
			textNota.setLineWrap(true);
			textNota.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					btnModifica.setEnabled(true);
				}
			});
			textNota.setFont(new Font("Tahoma", textNota.getFont().getStyle(), 14));
			GroupLayout gl_panelCentral = new GroupLayout(panelCentral);
			gl_panelCentral.setHorizontalGroup(
				gl_panelCentral.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelCentral.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_panelCentral.createParallelGroup(Alignment.LEADING)
							.addGroup(Alignment.TRAILING, gl_panelCentral.createSequentialGroup()
								.addComponent(lblTitolo, GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
								.addGap(18)
								.addComponent(txtTitolo, 261, 261, 261)
								.addGap(188))
							.addGroup(Alignment.TRAILING, gl_panelCentral.createSequentialGroup()
								.addComponent(textNota)
								.addContainerGap())
							.addGroup(Alignment.TRAILING, gl_panelCentral.createSequentialGroup()
								.addComponent(lblNota, GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)
								.addContainerGap())))
			);
			gl_panelCentral.setVerticalGroup(
				gl_panelCentral.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelCentral.createSequentialGroup()
						.addGap(33)
						.addGroup(gl_panelCentral.createParallelGroup(Alignment.BASELINE)
							.addComponent(txtTitolo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblTitolo, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
						.addGap(91)
						.addComponent(lblNota)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(textNota, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
						.addContainerGap())
			);
			panelCentral.setLayout(gl_panelCentral);
		}
		{
			JPanel panelBottom = new JPanel();
			panelBottom.setBackground(new Color(255, 192, 203));
			contentPanel.add(panelBottom, BorderLayout.SOUTH);
			{
				btnBack = new JButton("Back");
				btnBack.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//TORNA INDIETRO:
						setVisible(false);
						
						theController.paginaNote.setVisible(true);
						theController.paginaNote.setEnabled(true);
					}
				});
				panelBottom.setLayout(new BorderLayout(0, 0));
				panelBottom.add(btnBack, BorderLayout.WEST);
			}
			{
				btnModifica = new JButton("Modifica");
				btnModifica.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int confirm = JOptionPane.showConfirmDialog(null, "Confermare l'operazione di modifica alla nota?", "Finestra conferma", JOptionPane.YES_NO_OPTION);
					
						if(confirm == JOptionPane.YES_OPTION) {
							nota.setParolaChiave(txtTitolo.getText().trim());
							nota.setNota(textNota.getText().trim());
							if(theController.modificaNota(nota)) {
								JOptionPane.showMessageDialog(null, "L'operaizone è andata a buon fine!");
								
								//RICARICHIAMO LA NOTA:
								txtTitolo.setText(nota.getParolaChiave());
								textNota.setText(nota.getNota());
							}
						}else {
							JOptionPane.showMessageDialog(null, "L'operazione è stata annullata!");
						}
					}
				});
				btnModifica.setForeground(new Color(255, 0, 0));
				btnModifica.setEnabled(false);
				panelBottom.add(btnModifica, BorderLayout.EAST);
			}
		}
	}
}
