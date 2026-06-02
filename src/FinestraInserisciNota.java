import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
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

public class FinestraInserisciNota extends JDialog {
	private Controller theController;
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel panelCentralTop;
	private JTextField textField;
	private JTable table;
	private DefaultTableModel model;

	public FinestraInserisciNota(Controller c) {
		theController = c;
		
		setTitle("Finesra per l'inserimento di una nota");
		setSize(615, 440);
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
			contentPanel.add(panelCentral, BorderLayout.CENTER);
			{
				panelCentralTop = new JPanel();
				panelCentralTop.setLayout(new BorderLayout(0, 0));
				{
					JScrollPane scrollPane = new JScrollPane();
					panelCentralTop.add(scrollPane, BorderLayout.CENTER);
					
					model  = new DefaultTableModel(
							new Object[][]{},
							new String[]{"id paziente", "Nome", "Cognome", "C.F.", "Nascita", "Telefono", "Email"}
						);
					
					table = new JTable(model);
					
					//NON MOSTRO L'ID DEL PAZIENTE:
					table.getColumnModel().getColumn(0).setWidth(0);
					table.getColumnModel().getColumn(0).setMaxWidth(0);
					table.getColumnModel().getColumn(0).setMinWidth(0);
					table.getColumnModel().getColumn(0).setPreferredWidth(0);
					
					table.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							
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
			
			textField = new JTextField();
			textField.setColumns(10);
			
			JLabel lblNota = new JLabel("Nota");
			lblNota.setHorizontalAlignment(SwingConstants.CENTER);
			lblNota.setFont(new Font("Tahoma", Font.PLAIN, 16));
			
			JTextArea textArea = new JTextArea();
			GroupLayout gl_panelCentral = new GroupLayout(panelCentral);
			gl_panelCentral.setHorizontalGroup(
				gl_panelCentral.createParallelGroup(Alignment.TRAILING)
					.addComponent(panelCentralTop, GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
					.addGroup(gl_panelCentral.createSequentialGroup()
						.addContainerGap()
						.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
						.addContainerGap())
					.addGroup(gl_panelCentral.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblNota, GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
						.addContainerGap())
					.addGroup(gl_panelCentral.createSequentialGroup()
						.addGap(215)
						.addComponent(lblTitolo, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
						.addGap(18)
						.addComponent(textField, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
						.addGap(124))
			);
			gl_panelCentral.setVerticalGroup(
				gl_panelCentral.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelCentral.createSequentialGroup()
						.addComponent(panelCentralTop, GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
						.addGap(44)
						.addGroup(gl_panelCentral.createParallelGroup(Alignment.BASELINE)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblTitolo))
						.addGap(26)
						.addComponent(lblNota, GroupLayout.PREFERRED_SIZE, 19, Short.MAX_VALUE)
						.addGap(18)
						.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
						.addGap(36))
			);
			panelCentral.setLayout(gl_panelCentral);
		}
		{
			JPanel panelBottom = new JPanel();
			contentPanel.add(panelBottom, BorderLayout.SOUTH);
			panelBottom.setLayout(new BorderLayout(0, 0));
			{
				JButton btnBack = new JButton("Back");
				panelBottom.add(btnBack, BorderLayout.WEST);
			}
			{
				JButton btnInserisci = new JButton("Inserisci");
				panelBottom.add(btnInserisci, BorderLayout.EAST);
			}
		}
	}
}
