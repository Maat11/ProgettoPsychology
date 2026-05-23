import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
import javax.swing.JSpinner;
import java.awt.SystemColor;

public class FinestraInserisciAppuntamento extends JDialog {
	private Controller theController;
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel panelCentral;
	private JButton btnBack;
	private JButton btnInserisci;
	private JDateChooser dateChooser;
	private JTable table;
	private DefaultTableModel model;
	private JLabel lblDataSelezionata;
	private JTextField txtCodiceFiscale;
	private JTextField IdPaziente;
	private JTextField txtOraInizio;
	private JTextField txtOraFine;
	private JComboBox comboBox;
	private java.sql.Date sqlDate;
	private int countMouseClick;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public FinestraInserisciAppuntamento(Controller c) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				LocalDate oggi = LocalDate.now();
				sqlDate = Date.valueOf(oggi);
				//CAMBIO FORMATO DELLA DATA:
				
		        String dataFormattata = sdf.format(sqlDate);
		        
				lblDataSelezionata.setText(dataFormattata);
				theController.popolaTabellaAppuntamentiConPazientiEConData(sqlDate, model);
			}
			@Override
			public void windowClosing(WindowEvent e) {
				btnBack.doClick();
			}
		});
		theController = c;

		setTitle("Inserisci appuntamento");
		setSize(860, 374);
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
				JLabel lblWelcome = new JLabel("Inserisci un appuntamento");
				lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 15));
				panelTop.add(lblWelcome);
			}
		}
		{
			panelCentral = new JPanel();
			contentPanel.add(panelCentral, BorderLayout.CENTER);
			
			JLabel lblScegliData = new JLabel("Compila i campi");
			lblScegliData.setHorizontalAlignment(SwingConstants.CENTER);
			lblScegliData.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			dateChooser = new JDateChooser();
			dateChooser.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					if ("date".equals(evt.getPropertyName())) {
					    // Recupera la data selezionata
					    java.util.Date selectedUtilDate = dateChooser.getDate();
					    if (selectedUtilDate != null) {
					        try {
					            // Converte java.util.Date in LocalDate
					            LocalDate localDate = selectedUtilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

					            // Converte LocalDate in java.sql.Date
					            java.sql.Date sqlDate = Date.valueOf(localDate);

					            // Formatta la data per il label
					            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					            String sqlFormatString = sdf.format(sqlDate);

					            // Aggiorna il label con la data formattata
					            lblDataSelezionata.setText(sqlFormatString);

					            // Aggiorna la tabella con la data selezionata
					            theController.popolaTabellaAppuntamentiConPazientiEConData(sqlDate, model);

					        } catch (Exception e) {
					            // Gestisci eventuali errori durante la conversione
					            lblDataSelezionata.setText("Errore nel formato della data "+ e);
					        }
					    } else {
					        // Nessuna data selezionata
					        lblDataSelezionata.setText("Nessuna data selezionata");
					    }
					}
				}
			});
			
			JPanel panelTable = new JPanel();
			
			JLabel lblElencoAppuntamenti = new JLabel("Elenco appuntamenti per la data ");
			lblElencoAppuntamenti.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			lblDataSelezionata = new JLabel();
			lblDataSelezionata.setForeground(Color.RED);
			lblDataSelezionata.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			JLabel lblData = new JLabel("Data");
			lblData.setFont(new Font("Tahoma", Font.PLAIN, 13));
			
			JLabel lblNome = new JLabel("Codice fiscale");
			lblNome.setToolTipText("Inserisci l'id del paziente");
			lblNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
			
			txtCodiceFiscale = new JTextField();
			txtCodiceFiscale.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//VAI NELLA FINESTRA PER SCEGLIERE IL PAZIENTE QUANDO L'UTENTE CLICCA DUE VOLTE:
					countMouseClick++;
					if(countMouseClick == 2) {
						//VAI ALLA FINESTRA DOVE SI SCEGLIE IL PAZIENTE + COUNT = 0:
						IdPaziente = new JTextField();
						IdPaziente.setVisible(false);
						theController.fromFinestraInserisciAppuntamentoToFinestraSceltaPazientePerAppuntamento(txtCodiceFiscale, IdPaziente);
						countMouseClick = 0;
					}
				}
			});
			txtCodiceFiscale.setColumns(10);
			
			JLabel lblOraInizio = new JLabel("Ora inizio");
			lblOraInizio.setFont(new Font("Tahoma", Font.PLAIN, 13));
			
			txtOraInizio = new JTextField();
			txtOraInizio.setHorizontalAlignment(SwingConstants.CENTER);
			txtOraInizio.setToolTipText("Il formato che accetta è HH:mm");
			txtOraInizio.setColumns(10);
			
			JLabel lblOraFine = new JLabel("Ora fine");
			lblOraFine.setFont(new Font("Tahoma", Font.PLAIN, 13));
			
			txtOraFine = new JTextField();
			txtOraFine.setHorizontalAlignment(SwingConstants.CENTER);
			txtOraFine.setToolTipText("Il formato che accettà è HH:mm");
			txtOraFine.setColumns(10);
			
			JLabel lblModalità = new JLabel("Modalità");
			lblModalità.setFont(new Font("Tahoma", Font.PLAIN, 13));
			
			String[] tipoSeduta = {"Presenza", "Online"};
			
			comboBox = new JComboBox(tipoSeduta);
			
			GroupLayout gl_panelCentral = new GroupLayout(panelCentral);
			gl_panelCentral.setHorizontalGroup(
				gl_panelCentral.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelCentral.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_panelCentral.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panelCentral.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panelCentral.createSequentialGroup()
									.addGroup(gl_panelCentral.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panelCentral.createParallelGroup(Alignment.LEADING, false)
											.addGroup(gl_panelCentral.createSequentialGroup()
												.addComponent(lblData, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE))
											.addComponent(lblOraInizio, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
											.addComponent(lblNome, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
										.addComponent(lblOraFine, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
									.addGap(16)
									.addGroup(gl_panelCentral.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(comboBox, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(txtOraFine, Alignment.LEADING)
										.addComponent(txtOraInizio)
										.addComponent(dateChooser, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(txtCodiceFiscale, GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED, 59, Short.MAX_VALUE))
								.addGroup(gl_panelCentral.createSequentialGroup()
									.addComponent(lblScegliData, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
									.addGap(18)))
							.addGroup(gl_panelCentral.createSequentialGroup()
								.addComponent(lblModalità)
								.addPreferredGap(ComponentPlacement.RELATED)))
						.addGroup(gl_panelCentral.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panelCentral.createSequentialGroup()
								.addComponent(lblElencoAppuntamenti)
								.addGap(18)
								.addComponent(lblDataSelezionata, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE))
							.addComponent(panelTable, GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE))
						.addContainerGap())
			);
			gl_panelCentral.setVerticalGroup(
				gl_panelCentral.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelCentral.createSequentialGroup()
						.addGap(20)
						.addGroup(gl_panelCentral.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_panelCentral.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblScegliData)
								.addComponent(lblElencoAppuntamenti, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
							.addComponent(lblDataSelezionata, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_panelCentral.createParallelGroup(Alignment.LEADING)
							.addComponent(panelTable, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
							.addGroup(gl_panelCentral.createSequentialGroup()
								.addGap(1)
								.addGroup(gl_panelCentral.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNome, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtCodiceFiscale, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(14)
								.addGroup(gl_panelCentral.createParallelGroup(Alignment.LEADING)
									.addComponent(lblData)
									.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_panelCentral.createParallelGroup(Alignment.LEADING)
									.addComponent(lblOraInizio, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtOraInizio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_panelCentral.createParallelGroup(Alignment.BASELINE)
									.addComponent(txtOraFine, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblOraFine, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_panelCentral.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblModalità, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
									.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGap(24))
			);
			panelTable.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane = new JScrollPane();
			panelTable.add(scrollPane, BorderLayout.CENTER);
			
			model  = new DefaultTableModel(
					new Object[][]{},
					new String[]{"id App.", "Data", "Ora inizio", "Ora fine", "Id", "Nome", "Cognome", "Telefono"}
				);
			
			table = new JTable(model);
			table.setFont(new Font("Tahoma", Font.PLAIN, 13));
			
			//NON MOSTRARE LA COLONNA ID APPUNTAMENTO:
			table.getColumnModel().getColumn(0).setMinWidth(0);
			table.getColumnModel().getColumn(0).setMaxWidth(0);
			table.getColumnModel().getColumn(0).setWidth(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(0);
			
			//NON MOSTRARE LA COLONNA ID PAZIENTE:
			table.getColumnModel().getColumn(4).setMinWidth(0);
			table.getColumnModel().getColumn(4).setMaxWidth(0);
			table.getColumnModel().getColumn(4).setWidth(0);
			table.getColumnModel().getColumn(4).setPreferredWidth(0);
			
			scrollPane.setColumnHeaderView(table);
			scrollPane.setViewportView(table);
			panelCentral.setLayout(gl_panelCentral);
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new BorderLayout(0, 0));
			{
				btnBack = new JButton("Back");
				btnBack.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//TORNA INDIETRO:
						setEnabled(false);
						setVisible(false);
						theController.paginaPrincipale.setVisible(true);
						theController.paginaPrincipale.setEnabled(true);
						txtClear();
					}
				});
				btnBack.setActionCommand("Cancel");
				buttonPane.add(btnBack, BorderLayout.WEST);
			}
			{
				btnInserisci = new JButton("Inserisci");
				btnInserisci.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
//						CONTROLLO CAMPI: INSERISCI IL CTRLHour
						if(ctrlTxt()) {
							//INSERISCI APPUNTAMENTO:
							try {
								//CAST DELLA DATA: 
								java.sql.Date sqlDate = new java.sql.Date(dateChooser.getDate().getTime());
								//INSERIMENTO DELL'APPUNTAMENTO:
								if(txtOraFine.getText().isBlank()){
									if(ctrlHour()) {
										Appuntamento appuntamento = new Appuntamento(Integer.valueOf(String.valueOf(IdPaziente.getText().trim())), sqlDate, txtOraInizio.getText().trim(), txtOraFine.getText().trim(), comboBox.getSelectedItem().toString().trim());
										if(theController.inserisciAppuntamento(appuntamento)) {
											//PULISCI I CAMPI:
											txtClear();
											//AGGIORNA LA TABELLA:
											theController.popolaTabellaAppuntamentiConPazientiEConData(sqlDate, model);
											//MESSAGGIO:
											JOptionPane.showMessageDialog(null, "Hai inserito correttamente l'appuntamento!");
										}else {
											JOptionPane.showMessageDialog(null, "Errore, l'appuntamento non è stato inserito correttamente. RIPROVA!");
										}
									}else {
										JOptionPane.showMessageDialog(null, "Errore con l'ora");
									}
								}
							} catch(ClassCastException  cast) {
								JOptionPane.showMessageDialog(null, "Errore nel cast delle date! "+ cast);
							} catch(Exception exc) {
								System.out.println(exc.toString());
								JOptionPane.showMessageDialog(null, "Errore di tipo 2");
							}
						}
					}		
				});
				btnInserisci.setActionCommand("OK");
				buttonPane.add(btnInserisci, BorderLayout.EAST);
				getRootPane().setDefaultButton(btnInserisci);
			}
		}
	}
//METODI:
	private boolean ctrlHour() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

	    try {
	        // Parsing dell'orario di inizio
	        LocalTime oraIn = LocalTime.parse(txtOraInizio.getText(), formatter);

	        // Se txtOraFine è vuoto, imposta oraFin a un'ora dopo oraIn
	        LocalTime oraFin;
	        if (txtOraFine.getText().isBlank()) {
	            oraFin = oraIn.plusHours(1); // Aggiungi un'ora
	            txtOraFine.setText(oraFin.format(formatter)); // Aggiorna il campo txtOraFine
	        } else {
	            // Altrimenti, parsalo
	            oraFin = LocalTime.parse(txtOraFine.getText(), formatter);
	        }

	        // Calcola la differenza in minuti
	        long minutiDifferenza = Duration.between(oraIn, oraFin).toMinutes();

	        // Verifica se la differenza è almeno 60 minuti
	        return minutiDifferenza >= 60;
	    } catch (DateTimeParseException e) {
	        // Se il formato non è valido, restituisci false
	        return false;
	    }
	}
	private boolean ctrlTxt() {
		if(txtCodiceFiscale.getText().isBlank()) {
			JOptionPane.showMessageDialog(null, "Errore, il campo id paziente non può essere vuoto!");
			return false;
		}
		if(dateChooser.getDate() == null) {
			JOptionPane.showMessageDialog(null, "il campo della data non può essere vuota");
			return false;
		}
		String regex = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";
		String regex2 = "([01]?[0-9]|2[0-3])";
		if(txtOraInizio.getText().isBlank()) {
			JOptionPane.showMessageDialog(null, "Errore, il campo ora inizio non può essere vuoto!");
			return false;
		}else {
			if(! txtOraInizio.getText().matches(regex)) {
				if(txtOraInizio.getText().trim().matches(regex2)) {
					String oraIn = txtOraInizio.getText()+":00";
					txtOraInizio.setText(oraIn);
				}else {
					return false;
				}
			}
		}
		if(! txtOraFine.getText().isBlank()) {
			if(! txtOraFine.getText().matches(regex)) {
				if(txtOraFine.getText().trim().matches(regex2)) {
					String oraFin = txtOraFine.getText()+":00";
					txtOraFine.setText(oraFin);
				}else {
					return false;
				}
			}
		}
		return true;
	}
	
	//PULISCI I CAMPI:
	private void txtClear() {
		txtCodiceFiscale.setText(null);
		dateChooser.setDate(null);
		txtOraInizio.setText(null);
		txtOraFine.setText(null);
		comboBox.setSelectedIndex(0);
	}
}
