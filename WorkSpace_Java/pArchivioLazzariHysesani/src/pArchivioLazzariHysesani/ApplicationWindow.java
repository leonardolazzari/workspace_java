package pArchivioLazzariHysesani;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class ApplicationWindow extends JFrame{


	private Archivio registro;
	private MyTable tabellaRegistro;



	public ApplicationWindow(String nomeFile) {
		super();
		registro = new Archivio(nomeFile, 0);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 710, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		
		
		String[] nomeColonne = new String[5];
		nomeColonne[0] = "Nome";
		nomeColonne[1] = "Cognome";
		nomeColonne[2] = "Classe";
		nomeColonne[3] = "Data di nascita";
		nomeColonne[4] = "Luogo di nascita";
		tabellaRegistro = new MyTable(registro.toMatriceString(), nomeColonne);
		getContentPane().add(tabellaRegistro);
		
		JButton btnRimuovi = new JButton("Rimuovi");
		btnRimuovi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				/*
				 * verifica se è stata selezionata dall'utente una riga, se si rimuove l'oggetto dal file,
				 * se no avvisa l'utente che deve essere selezionata una riga
				 */
				int rigaSelezionata = tabellaRegistro.getSelectedRow();
				if(rigaSelezionata != -1) {
					registro.rimuoviStudente(rigaSelezionata);
					getContentPane().remove(tabellaRegistro);
					tabellaRegistro = new MyTable(registro.toMatriceString(), nomeColonne);
					getContentPane().add(tabellaRegistro);
					getContentPane().repaint();
				}
				else {
					JOptionPane.showMessageDialog(null, "Selezionare una riga prima di premere il bottone calcella");
				}
			}
		});
		btnRimuovi.setBounds(230, 412, 89, 23);
		getContentPane().add(btnRimuovi);
		
		JButton btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				/*
				 * apre un frame che permette all'utente di aggiungere uno studente 
				 */
				FrameAggiungi frameAggiungi = new FrameAggiungi(registro);
				
			}
		});
		btnAggiungi.setBounds(10, 412, 89, 23);
		getContentPane().add(btnAggiungi);
		
		JButton btnModifica = new JButton("Modifica");
		btnModifica.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/*
				 * memorizza la riga selezionata e apre un frame per abilitare l'utente alla modifica della riga
				 */
				int rigaSelezionata = tabellaRegistro.getSelectedRow();
				if(rigaSelezionata != -1) {
					FrameModifica frameModifica = new FrameModifica(registro, registro.toArrayList().get(rigaSelezionata), rigaSelezionata);
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Selezionare una riga prima di premere il bottone modifica");
				}
				
			}
		});
		btnModifica.setBounds(120, 412, 89, 23);
		getContentPane().add(btnModifica);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/*
				 * aggiorna la tabella con i dati contenuti nel file
				 */
				getContentPane().remove(tabellaRegistro);
				tabellaRegistro = new MyTable(registro.toMatriceString(), nomeColonne);
				getContentPane().add(tabellaRegistro);
				getContentPane().repaint();
			}
		});
		btnRefresh.setBounds(450, 412, 94, 23);
		getContentPane().add(btnRefresh);
		
		JButton btnUnisci = new JButton("Unisci");
		btnUnisci.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/*
				 * richiede all'utente con quale file unire il file corrente (se il file scelto dall'utente non esiste da un errore generico)
				 * chiede all'utente quale sarà il nome del terzo file.
				 */
				String nomeSecondoFile = JOptionPane.showInputDialog(null, "Inserire il nome del file con cui mescolare il file " + registro.getNomeFile());
				String nomeTerzoFile = JOptionPane.showInputDialog(null, "Inserire il nome del file in cui fondere il file " + registro.getNomeFile() + " con il file " + nomeSecondoFile);
				if(registro.fusione(nomeSecondoFile, nomeTerzoFile))
					JOptionPane.showMessageDialog(null, "Fusione completata");
				else
					JOptionPane.showMessageDialog(null, "La fusione non è andata a buon fine");
			}
		});
		btnUnisci.setBounds(340, 412, 89, 23);
		getContentPane().add(btnUnisci);
		
		JLabel labelTitolo = new JLabel("Benvenuto, questa interfaccia ti permetterà di gestire il file ''" + registro.getNomeFile() + "''");
		labelTitolo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelTitolo.setBounds(10, 11, 674, 23);
		getContentPane().add(labelTitolo);
		
		JButton btnChiudi = new JButton("Chiudi");
		btnChiudi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				System.exit(1);
			}
		});
		btnChiudi.setBounds(595, 13, 89, 23);
		getContentPane().add(btnChiudi);
		
		JButton btnCambiaFile = new JButton("Cambia File");
		btnCambiaFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/*
				 * permette all'utente di modificare il file da gestire
				 */
				String nomeModificato = JOptionPane.showInputDialog(null, "Inserire il nome del file che si vuole creare o gestire");
				registro = new Archivio(nomeModificato, 0);
				getContentPane().remove(tabellaRegistro);
				tabellaRegistro = new MyTable(registro.toMatriceString(), nomeColonne);
				getContentPane().add(tabellaRegistro);
				getContentPane().repaint();
				
				labelTitolo.setText("Benvenuto, questa interfaccia ti permetterà di gestire il file ''" + registro.getNomeFile() + "''");
			}
		});
		btnCambiaFile.setBounds(576, 412, 108, 23);
		getContentPane().add(btnCambiaFile);
	}
}
