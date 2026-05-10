
public class Paziente {
	private int id;
	private String nome;
	private String cognome;
	private String codiceFsicale; 
	private java.sql.Date dataNascita;
	private String email;
	private String telefono;
	private double prezzo;
	private double credito;
	
	public Paziente(String nome, String cognome, String codiceFiscale, java.sql.Date dataNascita, String telefono, double prezzo){
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFsicale = codiceFiscale;
		this.dataNascita = dataNascita;
		this.telefono = telefono;
		this.prezzo = prezzo;
	}
	
//GETTERS:
	public int getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public String getCognome() {
		return cognome;
	}
	public String getCodiceFsicale() {
		return codiceFsicale;
	}
	public java.sql.Date getDataNascita() {
		return dataNascita;
	}
	public String getEmail() {
		return email;
	}
	public String getTelefono() {
		return telefono;
	}
	public double getPrezzo() {
		return prezzo;
	}
	public double getCredito() {
		return credito;
	}
	
//SETTERS:
	public void setId(int id) {
		this.id = id;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public void setCodiceFsicale(String codiceFsicale) {
		this.codiceFsicale = codiceFsicale;
	}
	public void setDataNascita(java.sql.Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	public void setCredito(double credito) {
		this.credito = credito;
	}

}
