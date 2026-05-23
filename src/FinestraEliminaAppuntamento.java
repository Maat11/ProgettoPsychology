import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.SystemColor;
import java.awt.Color;

public class FinestraEliminaAppuntamento extends JDialog {
	private Controller theController;
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private java.sql.Date sqlDate;
	private DefaultTableModel model;
	private JButton btnBack;
	private JButton btnElimina;
	private JTable table;
	private JDateChooser dateChooser;
	private JLabel lblAppSelezionato;
	private java.sql.Date dataSel;
	private String dataSceltaString;
	private String oraInSel;
	private String oraFinSel;
	private String pagato;
	
	public FinestraEliminaAppuntamento(Controller c) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				//DATA ODIERNA:
				LocalDate dataOdiernaLocalDate = LocalDate.now();
				//TIPO FORMATTER:
				
				sqlDate = java.sql.Date.valueOf(dataOdiernaLocalDate);
				
				//POPOLA TABELLA:
				theController.popolaTabellaAppuntamentiConPazientiEConData(sqlDate, model);
			}
			@Override
			public void windowClosing(WindowEvent e) {
				btnBack.doClick();
			}
		});
		theController = c;
		
		setSize(1050, 600);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panelTop = new JPanel();
			contentPanel.add(panelTop, BorderLayout.NORTH);
			{
				JLabel lblWelcome = new JLabel("Seleziona l'appuntamento che vuoi eliminare");
				lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 17));
				panelTop.add(lblWelcome);
			}
		}
		{
			JPanel panelCentral = new JPanel();
			contentPanel.add(panelCentral, BorderLayout.CENTER);
			panelCentral.setLayout(new BorderLayout(0, 0));
			{
				lblAppSelezionato = new JLabel("");
				lblAppSelezionato.setForeground(Color.BLUE);
				lblAppSelezionato.setFont(new Font("Tahoma", Font.PLAIN, 17));
				lblAppSelezionato.setVisible(false);
				panelCentral.add(lblAppSelezionato, BorderLayout.SOUTH);
			}
			
			model  = new DefaultTableModel(
					new Object[][]{},
					new String[]{"Data", "Inizio", "Fine", "Id", "Nome", "Cognome", "Telefono", "Modalità", "Pagato"}
				);
			
			{
				JPanel panelCentral2 = new JPanel();
				panelCentral.add(panelCentral2, BorderLayout.CENTER);
				panelCentral2.setLayout(new BorderLayout(0, 0));
				{
					JPanel panel = new JPanel();
					panelCentral2.add(panel, BorderLayout.NORTH);
					panel.setLayout(new BorderLayout(0, 0));
					{
						JLabel lblDataSelezionata = new JLabel("Inserisci una data");
						lblDataSelezionata.setFont(new Font("Tahoma", Font.PLAIN, 14));
						panel.add(lblDataSelezionata, BorderLayout.WEST);
					}
					{
						JPanel panelContentJDateChooser = new JPanel();
						panel.add(panelContentJDateChooser, BorderLayout.EAST);
						{
							dateChooser = new JDateChooser();
							GroupLayout gl_panelContentJDateChooser = new GroupLayout(panelContentJDateChooser);
							gl_panelContentJDateChooser.setHorizontalGroup(
								gl_panelContentJDateChooser.createParallelGroup(Alignment.TRAILING)
									.addGroup(Alignment.LEADING, gl_panelContentJDateChooser.createSequentialGroup()
										.addContainerGap()
										.addComponent(dateChooser, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
										.addContainerGap())
							);
							gl_panelContentJDateChooser.setVerticalGroup(
								gl_panelContentJDateChooser.createParallelGroup(Alignment.TRAILING)
									.addGroup(Alignment.LEADING, gl_panelContentJDateChooser.createSequentialGroup()
										.addContainerGap()
										.addComponent(dateChooser, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addContainerGap())
							);
							panelContentJDateChooser.setLayout(gl_panelContentJDateChooser);
							dateChooser.addPropertyChangeListener(new PropertyChangeListener() {
								public void propertyChange(PropertyChangeEvent evt) {
									//FORMATTA LA DATA:
									if ("date".equals(evt.getPropertyName())){
										Date selectedUtilDate = (Date) evt.getNewValue();
										 java.sql.Date sqlDate2 = null;
										if(selectedUtilDate != null) {
											// Converte java.util.Date in java.sql.Date
											sqlDate2 = new java.sql.Date(selectedUtilDate.getTime());
											//POPOLA TABELLA:
											theController.popolaTabellaAppuntamentiConPazientiEConData(sqlDate2, model);
										}
									}
								}
							});
						}
					}
				}
				{
					JScrollPane scrollPane = new JScrollPane();
					panelCentral2.add(scrollPane, BorderLayout.CENTER);
					{
						table = new JTable(model);
						table.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								int selRow = table.rowAtPoint(e.getPoint());
								lblAppSelezionato.setText("L'appuntamento selezionato è in data " + " " + String.valueOf(table.getValueAt(selRow, 0)) + " e " + "inizia alle ore " + String.valueOf(table.getValueAt(selRow, 1)) + " e finisce alle ore " + String.valueOf(table.getValueAt(selRow, 2)) + " con il paziente " + String.valueOf(table.getValueAt(selRow, 4)) + " " + String.valueOf(table.getValueAt(selRow, 5)));
								lblAppSelezionato.setVisible(true);
								
								dataSceltaString = String.valueOf(table.getValueAt(selRow, 0));
								oraInSel = String.valueOf(table.getValueAt(selRow, 1));
								oraFinSel = String.valueOf(table.getValueAt(selRow, 2));
								pagato = String.valueOf(table.getValueAt(selRow, 8));
								
								DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						        LocalDate localDate = LocalDate.parse(dataSceltaString, inputFormatter);

						        // 2. Formatta LocalDate nel formato "yyyy-MM-dd"
						        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						        String formattedString = localDate.format(outputFormatter);

						        dataSel = java.sql.Date.valueOf(formattedString);
								
							}
						});
						
//						NASCONDI L'ID DEL PAZIENTE:
						table.getColumnModel().getColumn(3).setWidth(0);
						table.getColumnModel().getColumn(3).setMaxWidth(0);
						table.getColumnModel().getColumn(3).setMinWidth(0);
						table.getColumnModel().getColumn(3).setPreferredWidth(0);
						
						scrollPane.setColumnHeaderView(table);
						scrollPane.setViewportView(table);
					}
				}
			}
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
				btnElimina = new JButton("Elimina");
				btnElimina.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//ELIMINA APPUNTAMENTO:
						if(ctrlField()) {
							int scelta = JOptionPane.showConfirmDialog(null, "Eliminare definitivamente l'appuntamento?", "Messaggio di conferma", JOptionPane.YES_NO_OPTION);
							if(scelta == JOptionPane.YES_OPTION) {
								if(theController.eliminaAppuntamento(dataSel, oraInSel, oraFinSel)) {
									JOptionPane.showMessageDialog(null, "L'appuntamento è stato eliminato con successo");
									lblAppSelezionato.setText(null);
								}
							}else {
								JOptionPane.showMessageDialog(null, "Operazione annullata!");
							}
						}
					}
				});
				panelBottom.add(btnElimina, BorderLayout.EAST);
			}
		}
	}
//METODI:
	private boolean ctrlField() {
		if(pagato.equalsIgnoreCase("Non definito")) {
			return true;
		}else {
			JOptionPane.showMessageDialog(null, "Errore, non puoi disdire un appuntamento dove risulta pagato!");
			return false;
		}
	}
}
