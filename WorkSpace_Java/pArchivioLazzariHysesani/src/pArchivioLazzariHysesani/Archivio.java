package pArchivioLazzariHysesani;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Archivio {
	String nomeFile;
	
	Archivio(String nomeFile, int nOggetti){
		Scanner input = new Scanner(System.in);
		this.nomeFile = nomeFile;
		//se non esiste gia un file di nome nomeFile allora lo crea
		if(!new File(nomeFile).exists()) {
			try {
				creaListStudenti(nOggetti);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	Archivio(String nomeFile, String nomeFileTxt){
		File fObj = new File(nomeFile);
		if(new File(nomeFileTxt).exists()) {
			if(fObj.exists()) {
				try {
					Files.delete(fObj.toPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			this.nomeFile = nomeFile;
			Studente studente = null;
			String rigaDati = new String();
			String[] splitDati = new String[5];
			BufferedReader br = null;
			ObjectOutputStream oos = null;
			try {
				oos = new ObjectOutputStream(new FileOutputStream(nomeFile));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				br = new BufferedReader(new FileReader("registro.txt"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			while(rigaDati != null) {
				try {
					rigaDati = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(rigaDati != null) {
					splitDati = rigaDati.split(", ");
					studente = new Studente(splitDati[0], splitDati[1], splitDati[2], splitDati[3], splitDati[4]);
					try {
						oos.writeObject(studente);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				oos.writeObject(new SignalEOF());
				oos.flush();
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
	}
	
	public String getNomeFile() {
		return nomeFile;
	}
	
	/*
	 * crea il file a partire da un arraylist
	 */
	private void creaFile(ArrayList<Studente> listaStudenti) {
		new File(nomeFile).delete();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(nomeFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//crea il file utilizzando l'ArrayList listaStudenti
		for(int i = 0; i < listaStudenti.size(); i++){
			try {
				oos.writeObject(listaStudenti.get(i));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			oos.writeObject(new SignalEOF());
			oos.flush();
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*
	 * crea il file di n oggetti e richiede all'utente con quali dati popolare gli oggetti
	 */
	private void creaListStudenti(int nOggetti) throws IOException{
		ArrayList<Studente> listaStudenti = new ArrayList<Studente>();
		Scanner input = new Scanner(System.in);
		//aggiungo all'ArrayList nOggetti di tipo studente con i parametri dati in input
		for(int i = 0; i < nOggetti; i++){
			System.out.println("Inserisci Nome, Cognome, Classe, Luogo di nascita e Data di nascita");
			listaStudenti.add(new Studente(input.nextLine(), input.nextLine(), input.nextLine(), input.nextLine(), input.nextLine()));
		}
		
		creaFile(listaStudenti);
		
	}
	
	
	/*
	 * stampa i dati presenti nel file
	 */
	void visualizza() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(nomeFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object aus = new Object();
		do{
			// per poter visualizzare le informazioni presenti nel file ciclo finche non viene letta 
			// l'istanza della classe SignalEOF
			try {
				aus = ois.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(aus instanceof Studente){
				Studente ausStudente = (Studente) aus;
				System.out.println(ausStudente);
			}
		}while (!(aus instanceof SignalEOF));
		try {
			ois.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*
	 * aggiunge l'oggetto nuovo, passato come parametro, nell'ultima posizione del file
	 */
	void aggiungiStudente(Studente nuovo) {
		File originale = new File(nomeFile);
		File temporaneo = new File("temp.dat");
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream("temp.dat"));
			in = new ObjectInputStream(new FileInputStream(nomeFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object aus = null;
		do {
			try {
				aus = in.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!(aus instanceof SignalEOF)) {
				try {
					out.writeObject(aus);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} while(!(aus instanceof SignalEOF));
		
		try {
			out.writeObject(nuovo);
			out.writeObject(new SignalEOF());
			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		originale.delete();
		temporaneo.renameTo(originale);
		System.out.println();
	}
	
	
	/*
	 * rimuove l'oggetto, alla posizione nRiga, presente nel file. 
	 */
	void rimuoviStudente(int nRiga) {
		File originale = new File(nomeFile);
		File temporaneo = new File("temp.dat");
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream("temp.dat"));
			in = new ObjectInputStream(new FileInputStream(nomeFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object aus = null;
		int n = 0;
		
		do {
			try {
				aus = in.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!(aus instanceof SignalEOF)) {
				if(n != nRiga) {
					try {
						out.writeObject(aus);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				n++;
			}
		} while(!(aus instanceof SignalEOF));
		
		try {
			out.writeObject(new SignalEOF());
			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Files.delete(originale.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		temporaneo.renameTo(originale);
	}
	
	
	/*
	 * modifica una riga del file sostituendo ad essa l'oggetto modificato
	 */
	void modificaRiga (int nRiga, Studente modificato) {
		File originale = new File(nomeFile);
		File temporaneo = new File("temp.dat");
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream("temp.dat"));
			in = new ObjectInputStream(new FileInputStream(nomeFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object aus = null;
		int n = 0;
		
		do {
			try {
				aus = in.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!(aus instanceof SignalEOF)) {
				if(n != nRiga) {
					try {
						out.writeObject(aus);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					try {
						out.writeObject(modificato);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				n++;
			}
		} while(!(aus instanceof SignalEOF));
		
		try {
			out.writeObject(new SignalEOF());
			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		originale.delete();
		temporaneo.renameTo(originale);
	}
	
	
	/*
	 * ritorna il file sotto forma di ArrayList
	 */
	ArrayList<Studente> toArrayList() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(nomeFile));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Object aus = new Object();
		ArrayList<Studente> listaStudenti = new ArrayList<Studente>(); 
		do{
			// per poter visualizzare le informazioni presenti nel file ciclo finche non viene letta 
			// l'istanza della classe SignalEOF
			try {
				aus = ois.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(aus instanceof Studente){
				listaStudenti.add((Studente) aus);
			}
		}while (!(aus instanceof SignalEOF));
		try {
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listaStudenti;
	}
	
	
	/*
	 * ritorna il file sotto forma di matrice di stringhe
	 */
	String[][] toMatriceString()	{
		ArrayList<Studente> listStudenti = toArrayList();
		String[][] matriceStudenti = new String[listStudenti.size()][5];
		for(int i = 0; i < matriceStudenti.length; i++) {
			matriceStudenti[i][0] = listStudenti.get(i).getNome();
			matriceStudenti[i][1] = listStudenti.get(i).getCognome();
			matriceStudenti[i][2] = listStudenti.get(i).getClasse();
			matriceStudenti[i][3] = listStudenti.get(i).getLuogoNascita();
			matriceStudenti[i][4] = listStudenti.get(i).getDataNascita();
		}
		return matriceStudenti;
	}
	
	
	/*
	 * ordina il file usando il cognome come discriminante
	 */
	void ordinaRegistroCognome() {
		ArrayList<Studente> registro = toArrayList();
		Studente aus = null;
		for(int i = 0; i < registro.size(); i++) {
			for(int j = 0; j < registro.size() - 1; j++) {
				if(registro.get(j).getCognome().compareTo(registro.get(j+1).getCognome()) > 0) {
					aus = registro.get(j);
					registro.set(j, registro.get(j+1));
					registro.set(j+1, aus);
				}
			}
		}
		creaFile(registro);
		
	}
	
	
	/*
	 * unisce il file corrente con un file scelto dall'utente in un terzo file
	 */
	boolean fusione(String nomeSecondoFile, String nomeFileUnione) {
		boolean aus = false;
		File primoFile = new File(nomeFile);
		File secondoFile = new File(nomeSecondoFile);
		File unioneFile = new File(nomeFileUnione);
		if(secondoFile.exists()) {
			ordinaRegistroCognome();
			new Archivio(nomeSecondoFile, 0).ordinaRegistroCognome();
			ObjectOutputStream out = null;
			ObjectInputStream in1 = null;
			ObjectInputStream in2 = null;
			try {
				out = new ObjectOutputStream(new FileOutputStream(nomeFileUnione));
				in1 = new ObjectInputStream(new FileInputStream(nomeFile));
				in2 = new ObjectInputStream(new FileInputStream(nomeSecondoFile));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Object ausPrimoFile = null;
			Object ausSecondoFile = null;
			Studente studenteR1 = null;
			Studente studenteR2 = null;
			do {
				try {
					if(studenteR1 == null && !(ausPrimoFile instanceof SignalEOF)) {
						ausPrimoFile = in1.readObject();
					} 
					if(studenteR2 == null && !(ausSecondoFile instanceof SignalEOF)) {
						ausSecondoFile = in2.readObject();
					}
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
				if(!(ausPrimoFile instanceof SignalEOF) && !(ausSecondoFile instanceof SignalEOF)) {
					studenteR1 = (Studente)ausPrimoFile;
					studenteR2 = (Studente)ausSecondoFile;
					if(studenteR1.getCognome().compareTo(studenteR2.getCognome()) < 0) {
						try {
							out.writeObject(studenteR1);
						} catch (IOException e) {
							e.printStackTrace();
						}
						studenteR1 = null;
					}
					else {
						try {
							out.writeObject(studenteR2);
						} catch (IOException e) {
							e.printStackTrace();
						}
						studenteR2 = null;
					}
				}
				else if(!(ausPrimoFile instanceof SignalEOF) && (ausSecondoFile instanceof SignalEOF)) {
					studenteR1 = (Studente)ausPrimoFile;
					try {
						out.writeObject(studenteR1);
					} catch (IOException e) {
						e.printStackTrace();
					}
					studenteR1 = null;
				}
				else if((ausPrimoFile instanceof SignalEOF) && !(ausSecondoFile instanceof SignalEOF)) {
					studenteR2 = (Studente)ausSecondoFile;
					try {
						out.writeObject(studenteR2);
					} catch (IOException e) {
						e.printStackTrace();
					}
					studenteR2 = null;
				}	
				
			} while(!(ausPrimoFile instanceof SignalEOF) || !(ausSecondoFile instanceof SignalEOF));
			aus = (ausPrimoFile instanceof SignalEOF) && (ausSecondoFile instanceof SignalEOF);
			try {
				out.writeObject(new SignalEOF());
				in1.close();
				in2.close();
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return aus;
	}
	
	private int divisioneFile(int nOggetti) throws FileNotFoundException, IOException, ClassNotFoundException {
		int nFile = 0;
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(nomeFile));
		Object aus = in.readObject();
		
		do{
			if(!(aus instanceof SignalEOF)){
				nFile++;
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomeFile + nFile));
				for(int i = 0; i < nOggetti && !(aus instanceof SignalEOF); i++){
					out.writeObject(aus);
					aus = in.readObject();
				}
				out.writeObject(new SignalEOF());
				out.close();
				
			}
		}while(!(aus instanceof SignalEOF));
		
		in.close();
		
		
		
		return nFile;
	}
	
	
	
	
	public void ordinaPerFusioneACoppia(int nOggetti) {
		int nFile = 0;
		try {
			nFile = divisioneFile(nOggetti);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		File fileOriginale = new File(nomeFile);
		File terzoFile = null;
		for(int i = 1; i < nFile; i++) {
			
			File fileCorrente = new File(nomeFile + (i));
			File fileSuccessivo = new File(nomeFile + (i+1));
			new Archivio(fileCorrente.getPath(), 0).fusione(fileSuccessivo.getPath(), "aus");
			
			terzoFile = new File("aus");
			try {
				Files.delete(fileCorrente.toPath());
				Files.delete(fileSuccessivo.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			terzoFile.renameTo(fileSuccessivo);
			terzoFile = fileSuccessivo;
		}
		
		try {
			Files.delete(fileOriginale.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		terzoFile.renameTo(fileOriginale);
		
	}
	
	public void addInFile(Studente studente) {
		ArrayList<Studente> studentiPresenti = toArrayList();
		studentiPresenti.add(studente);
		creaFile(studentiPresenti);
	}
	
	public void fusioneTotale(File[] files) {
		ObjectInputStream[] oiss = new ObjectInputStream[files.length];
		int i;
		int rigaStudenteScelto = -1;
		for(i = 0; i < files.length; i++) {
			try {
				oiss[i] = new ObjectInputStream(new FileInputStream(files[i].getPath()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		Studente precedente = null;
		Object[] ausStudenti = new Object[oiss.length];
		ArrayList<Studente> studenti = new ArrayList<Studente>();
		do {
			
			for(i = 0; i < oiss.length; i++) {
				if((i == rigaStudenteScelto || rigaStudenteScelto == -1) && !(ausStudenti[i] instanceof SignalEOF)) {
					try {
						ausStudenti[i] = oiss[i].readObject();
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					if(ausStudenti[i] instanceof Studente) {
						studenti.add(i, (Studente) ausStudenti[i]);
					}
				}
			}
			precedente = studenti.get(0);
			rigaStudenteScelto = 0;
			for(i = 1; i < studenti.size(); i++) {
				if(precedente.getCognome().compareTo(studenti.get(i).getCognome()) > 0) {
					precedente = studenti.get(i);
					rigaStudenteScelto = i;
				}
			}
			studenti.remove(rigaStudenteScelto);
			addInFile(precedente);
		
		} while(studenti.size() > 0);
		for(i = 0; i < oiss.length; i++) {
			try {
				oiss[i].close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void ordinaPerFusioneTotale(int nOggetti) {
		
		int nFile = 0;
		try {
			nFile = divisioneFile(nOggetti);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		pulisciFile();
		File[] file = new File[nFile];
		for(int i = 0; i < file.length; i++) {
			file[i] = new File(nomeFile + (i + 1));
			new Archivio(file[i].getPath(), 0).ordinaRegistroCognome();
		}
		fusioneTotale(file);
		for(int i = 0; i < file.length; i++) {
			try {
				Files.delete(file[i].toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void pulisciFile() {
		File f = new File(nomeFile);
		try {
			f.delete();
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f.getPath()));
			oos.writeObject(new SignalEOF());
			oos.flush();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int size() {
		int nOggetti = 0;
		Object aus = null;
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(nomeFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		do {
			try {
				aus = in.readObject();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			if(!(aus instanceof SignalEOF)) {
				nOggetti++;
			}
		}while(!(aus instanceof SignalEOF));
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nOggetti;
	}
	
	public void toFileTxt(String nomeFileTxt) {
		ObjectInputStream ois = null;
		BufferedWriter bw = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(nomeFile));
			bw = new BufferedWriter(new FileWriter(nomeFileTxt + ".txt"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Object ausObj = new Object(); 
		Studente ausStudente = null;
		String ausString = null;
		do{
			// per poter visualizzare le informazioni presenti nel file ciclo finche non viene letta 
			// l'istanza della classe SignalEOF
			try {
				ausObj = ois.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(ausObj instanceof Studente){
				ausStudente = (Studente)ausObj;
				ausString = ausStudente.getNome() + ", " + ausStudente.getCognome() + ", " + ausStudente.getClasse() + ", " +
							ausStudente.getLuogoNascita() + ", " + ausStudente.getDataNascita();
				try {
					bw.write(ausString);
					bw.newLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
			}
		}while (!(ausObj instanceof SignalEOF));
		try {
			ois.close();
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stampaTxt(String fileTxt) {
		BufferedReader bw = null;
		try {
			bw = new BufferedReader(new FileReader(fileTxt + ".txt"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String ausString = null;
		try {
			ausString = bw.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(ausString != null){
			// per poter visualizzare le informazioni presenti nel file ciclo finche non viene letta 
			// l'istanza della classe SignalEOF
			System.out.println(ausString);
			try {
				ausString = bw.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[]args) {
//		Archivio r = new Archivio("reg", 6);
//		r.visualizza();
//
//		r.ordinaPerFusioneACoppia(2);
//		
//		System.out.println();
//		r.visualizza();
	
		
		Archivio a = new Archivio("reg", "registro.txt");
		a.visualizza();
		a.toFileTxt("reg");
		a.stampaTxt("reg");
	}
	
	
}
