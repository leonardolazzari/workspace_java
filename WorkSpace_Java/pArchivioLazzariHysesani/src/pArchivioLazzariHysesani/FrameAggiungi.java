package pArchivioLazzariHysesani;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;

public class FrameAggiungi extends JFrame {
	
	Studente studente;
	Archivio registro;
	JLabel lbInserimento;
	JButton btnAnnulla;
	JButton btnCancella;
	JButton btnAggiungi;
	private JTextField textFieldNome;
	private JTextField textFieldCognome;
	private JTextField textFieldClasse;
	private JTextField textFieldDataNascita;
	private JTextField textFieldLuogoNascita;
	
	FrameAggiungi(Archivio registro){
		
		
		
		super();
		setBounds(100, 100, 452, 193);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setVisible(true);
		
		this.registro = registro;
		
		lbInserimento = new JLabel("Inserisci i dati del nuovo studente");
		lbInserimento.setBounds(10, 0, 414, 14);
		getContentPane().add(lbInserimento);
		
		btnAnnulla = new JButton("Annulla");
		btnAnnulla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnAnnulla.setBounds(13, 121, 89, 23);
		getContentPane().add(btnAnnulla);
		
		btnCancella = new JButton("Cancella");
		btnCancella.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textFieldNome.setText("");
				textFieldCognome.setText("");
				textFieldClasse.setText("");
				textFieldDataNascita.setText("");
				textFieldLuogoNascita.setText("");
			}
		});
		btnCancella.setBounds(181, 121, 89, 23);
		getContentPane().add(btnCancella);
		
		btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/*
				 * verifica se tutti i campi del frame sono stati riempiti,
				 * se si crea un nuovo oggetto studente e lo aggiunge al file,
				 * se no avverte l'utente che deve riempire i campi
				 */
				String nome = textFieldNome.getText();
				String cognome = textFieldCognome.getText();
				String classe = textFieldClasse.getText();
				String dataNascita = textFieldDataNascita.getText();
				String luogoNascita = textFieldLuogoNascita.getText();
				if(!nome.equals("") && !cognome.equals("") && !classe.equals("") && !dataNascita.equals("") && !luogoNascita.equals("")) {
					studente = new Studente(nome, cognome, classe, dataNascita, luogoNascita);
					registro.aggiungiStudente(studente);
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "Riempire tutti i campi prima di aggiungere lo studente");
				}
			}
		});
		btnAggiungi.setBounds(338, 121, 89, 23);
		getContentPane().add(btnAggiungi);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(13, 42, 180, 20);
		getContentPane().add(textFieldNome);
		textFieldNome.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(13, 25, 46, 14);
		getContentPane().add(lblNome);
		
		textFieldCognome = new JTextField();
		textFieldCognome.setColumns(10);
		textFieldCognome.setBounds(244, 42, 180, 20);
		getContentPane().add(textFieldCognome);
		
		JLabel lblCognome = new JLabel("Cognome");
		lblCognome.setBounds(244, 25, 77, 14);
		getContentPane().add(lblCognome);
		
		textFieldClasse = new JTextField();
		textFieldClasse.setColumns(10);
		textFieldClasse.setBounds(13, 90, 86, 20);
		getContentPane().add(textFieldClasse);
		
		JLabel lblClasse = new JLabel("Classe");
		lblClasse.setBounds(13, 73, 86, 14);
		getContentPane().add(lblClasse);
		
		textFieldDataNascita = new JTextField();
		textFieldDataNascita.setColumns(10);
		textFieldDataNascita.setBounds(181, 90, 86, 20);
		getContentPane().add(textFieldDataNascita);
		
		JLabel lblDataDiNascita = new JLabel("Data di Nascita");
		lblDataDiNascita.setBounds(181, 73, 86, 14);
		getContentPane().add(lblDataDiNascita);
		
		textFieldLuogoNascita = new JTextField();
		textFieldLuogoNascita.setColumns(10);
		textFieldLuogoNascita.setBounds(338, 90, 86, 20);
		getContentPane().add(textFieldLuogoNascita);
		
		JLabel lblLuogoDiNascita = new JLabel("Luogo di Nascita");
		lblLuogoDiNascita.setBounds(338, 73, 98, 14);
		getContentPane().add(lblLuogoDiNascita);
		
	}
}
