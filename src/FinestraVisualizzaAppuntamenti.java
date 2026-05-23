import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.GridLayout;
import java.awt.CardLayout;
import net.miginfocom.swing.MigLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.BoxLayout;
import com.toedter.calendar.JDateChooser;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.Date;
import java.beans.PropertyChangeEvent;

public class FinestraVisualizzaAppuntamenti extends JDialog {
	private Controller theController;
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JDateChooser dateChooser;
	private JTable table;
	private DefaultTableModel model;
	private JButton btnBack;
	
	public FinestraVisualizzaAppuntamenti(Controller c) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				btnBack.doClick();
			}
			@Override
			public void windowActivated(WindowEvent e) {
				//DATA ODIERNA:
				LocalDate dataOdiernaLocalDate = LocalDate.now();
				
				java.sql.Date sqlDate = java.sql.Date.valueOf(dataOdiernaLocalDate);
				
				//POPOLA TABELLA:
				theController.popolaTabellaAppuntamentiConPazientiEConData(sqlDate, model);
			}
		});
		theController = c;
		
		setTitle("Finestra per visualizzare gli appuntamenti");
		setSize(650, 600);
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
				JLabel lblWelcome = new JLabel("Tutti i tuoi appuntamenti (Capire se serve questa parte di messaggio)");
				lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 17));
				panelTop.add(lblWelcome);
			}
		}
		{
			JPanel panelCentral = new JPanel();
			contentPanel.add(panelCentral, BorderLayout.CENTER);
			panelCentral.setLayout(new BorderLayout(0, 0));
			{
				JPanel panelCentralTop = new JPanel();
				panelCentral.add(panelCentralTop, BorderLayout.NORTH);
				panelCentralTop.setLayout(new BorderLayout(0, 0));
				{
					JLabel lblSelezionaLaData = new JLabel("Seleziona la data");
					lblSelezionaLaData.setFont(new Font("Tahoma", Font.PLAIN, 13));
					panelCentralTop.add(lblSelezionaLaData, BorderLayout.WEST);
				}
				{
					JPanel panel = new JPanel();
					panelCentralTop.add(panel, BorderLayout.EAST);
					{
						dateChooser = new JDateChooser();
						dateChooser.addPropertyChangeListener(new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent evt) {
								if ("date".equals(evt.getPropertyName())){
									Date selectedUtilDate = (Date) evt.getNewValue();
									 java.sql.Date sqlDate = null;
									if(selectedUtilDate != null) {
										// Converte java.util.Date in java.sql.Date
										sqlDate = new java.sql.Date(selectedUtilDate.getTime());
										//POPOLA TABELLA:
										theController.popolaTabellaAppuntamentiConPazientiEConData(sqlDate, model);
									}
								}
							}
						});
					}
					GroupLayout gl_panel = new GroupLayout(panel);
					gl_panel.setHorizontalGroup(
						gl_panel.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_panel.createSequentialGroup()
								.addContainerGap(22, Short.MAX_VALUE)
								.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
					);
					gl_panel.setVerticalGroup(
						gl_panel.createParallelGroup(Alignment.TRAILING)
							.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
								.addContainerGap()
								.addComponent(dateChooser, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addContainerGap())
					);
					panel.setLayout(gl_panel);
				}
			}
			{
				JPanel panelCentral2 = new JPanel();
				panelCentral.add(panelCentral2, BorderLayout.CENTER);
				panelCentral2.setLayout(new BorderLayout(0, 0));
				{
					JScrollPane scrollPane = new JScrollPane();
					panelCentral2.add(scrollPane, BorderLayout.CENTER);
					
					model  = new DefaultTableModel(
							new Object[][]{},
							new String[]{"id App", "Data", "Ora inizio" , "Ora fine" , "id paziente", "Nome", "Cognome", "Telefono", "Modalità", "Pagato"}
						);
					
					{
						table = new JTable(model);
						
						//MI SERVE PER NON FARE VISUALIZZARE L'ID DELL'APPUNTAMENTO:
						table.getColumnModel().getColumn(0).setWidth(0);
						table.getColumnModel().getColumn(0).setMaxWidth(0);
						table.getColumnModel().getColumn(0).setMinWidth(0);
						table.getColumnModel().getColumn(0).setPreferredWidth(0);
						
						//MI SERVE A NON FARE VISUALIZZARE L'ID DEL PAZIENTE:
						table.getColumnModel().getColumn(4).setWidth(0);
						table.getColumnModel().getColumn(4).setMaxWidth(0);
						table.getColumnModel().getColumn(4).setMinWidth(0);
						table.getColumnModel().getColumn(4).setPreferredWidth(0);
						
						scrollPane.setColumnHeaderView(table);
						scrollPane.setViewportView(table);
					}
				}
			}
		}
		{
			JPanel panelBottom = new JPanel();
			contentPanel.add(panelBottom, BorderLayout.SOUTH);
			{
				btnBack = new JButton("Back");
				btnBack.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						
						theController.paginaPrincipale.setVisible(true);
						theController.paginaPrincipale.setEnabled(true);
					}
				});
				panelBottom.add(btnBack);
			}
		}
	}

}
