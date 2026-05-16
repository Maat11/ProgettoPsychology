import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;

public class FinestraSceltaPazientePerAppuntamento extends JDialog {
	private Controller theController;
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton btnBack;
	private JButton btnOK;
	private final JPanel panelTop = new JPanel();
	private DefaultTableModel model;
	private JTable table;
	private JLabel lblWelcome2;
	private JLabel lblPazienteSelezionato;
	
	public FinestraSceltaPazientePerAppuntamento(JTextField txtCodiceFiscale, JTextField idPaziente, Controller c) {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
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
		
		setTitle("Finestra scegli paziente per l'appuntamento");
		setSize(650, 350);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(panelTop, BorderLayout.NORTH);
		{
			JLabel lblWelcome = new JLabel("Seleziona il paziente");
			lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 17));
			panelTop.add(lblWelcome);
		}
		{
			JPanel panelCentral = new JPanel();
			contentPanel.add(panelCentral, BorderLayout.CENTER);
			panelCentral.setLayout(new BorderLayout(0, 0));
			{
				JScrollPane scrollPane = new JScrollPane();
				panelCentral.add(scrollPane, BorderLayout.CENTER);
				
				model  = new DefaultTableModel(
						new Object[][]{},
						new String[]{"id", "Nome", "Cognome" , "Codice fiscale" , "Nascita", "Telefono"}
					);
				
				table = new JTable(model);
				table.getColumnModel().getColumn(0).setMinWidth(0);
				table.getColumnModel().getColumn(0).setMaxWidth(0);
				table.getColumnModel().getColumn(0).setWidth(0);
				table.getColumnModel().getColumn(0).setPreferredWidth(0);
				table.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int selRow = table.rowAtPoint(e.getPoint());
						if(selRow != -1) {
							txtCodiceFiscale.setText((String.valueOf(table.getValueAt(selRow, 3))));
							idPaziente.setText((String.valueOf(table.getValueAt(selRow, 0))));
							
							//MOSTRA I DETTAGLI DEL PAZIENTE:
							lblWelcome2.setVisible(true);
							lblPazienteSelezionato.setText(String.valueOf(table.getValueAt(selRow, 1)) + " " + String.valueOf(table.getValueAt(selRow, 2)));
							lblPazienteSelezionato.setVisible(true);
							
							btnBack.setEnabled(true);
							btnOK.setEnabled(true);
						}
					}
				});
				scrollPane.setColumnHeaderView(table);
				scrollPane.setViewportView(table);
				
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.SOUTH);
			panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				lblWelcome2 = new JLabel("Paziente scelto:");
				lblWelcome2.setVisible(false);
				lblWelcome2.setFont(new Font("Tahoma", Font.PLAIN, 17));
				panel.add(lblWelcome2);
			}
			{
				lblPazienteSelezionato = new JLabel("");
				lblPazienteSelezionato.setVisible(false);
				lblPazienteSelezionato.setForeground(Color.RED);
				lblPazienteSelezionato.setFont(new Font("Tahoma", Font.PLAIN, 17));
				panel.add(lblPazienteSelezionato);
			}
		}
		{
			JPanel panelBottom = new JPanel();
			getContentPane().add(panelBottom, BorderLayout.SOUTH);
			{
				btnBack = new JButton("Back");
				btnBack.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//TORNA INDIETRO:
						txtCodiceFiscale.setText(null);
						setVisible(false);
						theController.finestraInserisciAppuntamento.setVisible(true);
						theController.finestraInserisciAppuntamento.setEnabled(true);
					}
				});
				panelBottom.setLayout(new BorderLayout(0, 0));
				panelBottom.add(btnBack, BorderLayout.WEST);
			}
			{
				btnOK = new JButton("OK");
				btnOK.setEnabled(false);
				btnOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						theController.finestraInserisciAppuntamento.setVisible(true);
						theController.finestraInserisciAppuntamento.setEnabled(true);
					}
				});
				btnOK.setActionCommand("OK");
				panelBottom.add(btnOK, BorderLayout.EAST);
				getRootPane().setDefaultButton(btnOK);
			}
		}
	}

}
