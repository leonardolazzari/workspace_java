package pArchivioLazzariHysesani;

import java.io.Serializable;

public class Studente implements Serializable{
	
	String nome, cognome, classe, luogoNascita;
	String dataNascita;
	
	Studente(String nome, String cognome, String classe, String luogoNascita, String dataNascita){
		this.nome = nome;
		this.cognome = cognome;
		this.classe = classe;
		this.luogoNascita = luogoNascita;
		this.dataNascita = dataNascita;
	}
	
	public String toString(){
		return "Nome: " + nome + ", Cognome: " + cognome + ", Classe: " + classe + ", Luogo Nascita: " + luogoNascita +
				", Data Nascita: " + dataNascita;
	}
	

	public String getNome() {
		return nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public String getClasse() {
		return classe;
	}
	
	public String getLuogoNascita() {
		return luogoNascita;
	}
	
	public String getDataNascita() {
		return dataNascita;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public void setClasse(String classe) {
		this.classe = classe;
	}
	
	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}
	
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	
}
