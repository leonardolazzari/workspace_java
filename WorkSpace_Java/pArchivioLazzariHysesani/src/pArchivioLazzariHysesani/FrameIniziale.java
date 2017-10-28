package pArchivioLazzariHysesani;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrameIniziale {

	private JFrame frame;
	private JTextField textFieldFile;
	private JButton btnAnnulla;
	private JButton btnAvanti;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameIniziale window = new FrameIniziale();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FrameIniziale() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 471, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblBenvenuto = new JLabel("Benvenuto, inserisci il nome del file da modificare o da creare");
		lblBenvenuto.setBounds(10, 21, 435, 20);
		lblBenvenuto.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		textFieldFile = new JTextField();
		textFieldFile.setBounds(136, 117, 184, 20);
		textFieldFile.setColumns(10);
		
		btnAnnulla = new JButton("Annulla");
		btnAnnulla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		btnAnnulla.setBounds(10, 227, 128, 23);
		
		btnAvanti = new JButton("Avanti");
		btnAvanti.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String nomeFile = textFieldFile.getText();
				if(!nomeFile.equals("")) {
					ApplicationWindow application = new ApplicationWindow(nomeFile);
					application.show();
					frame.dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "Inserire il nome del file prima di proseguire");
				}
			}
		});
		btnAvanti.setBounds(317, 227, 128, 23);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnAnnulla);
		frame.getContentPane().add(btnAvanti);
		frame.getContentPane().add(lblBenvenuto);
		frame.getContentPane().add(textFieldFile);
	}

}
