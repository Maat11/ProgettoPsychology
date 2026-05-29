
public class NotaRapida {
	private int idNota;
	private int idAppuntamento;
	private int idPaziente;
	private String parolaChiave;
	private String nota;
	private java.sql.Date date;
	
//COSTRUTTORE 
	public NotaRapida(int idAppuntamento, int idPaziente, String nota) {
		this.idAppuntamento = idAppuntamento;
		this.idPaziente = idPaziente;
		this.nota = nota;
	}
	
// GETTERS: 
	public int getIdNota() {
		return idNota;
	}
	public int getIdAppuntamento() {
		return idAppuntamento;
	}
	public int getIdPaziente() {
		return idPaziente;
	}
	public String getParolaChiave() {
		return parolaChiave;
	}
	public String getNota() {
		return nota;
	}
	public java.sql.Date getDate() {
		return date;
	}
	
//SETTERS: 
	public void setIdNota(int idNota) {
		this.idNota = idNota;
	}
	public void setIdAppuntamento(int idAppuntamento) {
		this.idAppuntamento = idAppuntamento;
	}
	public void setIdPaziente(int idPaziente) {
		this.idPaziente = idPaziente;
	}
	public void setParolaChiave(String parolaChiave) {
		this.parolaChiave = parolaChiave;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	public void setDate(java.sql.Date date) {
		this.date = date;
	}
	
}
