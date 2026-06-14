package dto;

public class Appuntamento {
	private int idPaziente;
	private java.sql.Date dataGiorno;
	private String oraInizio;
	private String oraFine;
	private String modalità;
	private String pagato;
	
	public Appuntamento(int idPaz, java.sql.Date dataGiorno, String oraInizio, String oraFine, String modalità){
		this.idPaziente = idPaz;
		this.dataGiorno = dataGiorno;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.modalità = modalità;
	}
	
//GETTERS:
	public int getIdPaz() {
		return idPaziente;
	}
	public java.sql.Date getDataGiorno() {
		return dataGiorno;
	}
	public String getOraInizio() {
		return oraInizio;
	}
	public String getOraFine() {
		return oraFine;
	}
	public String getModalità() {
		return modalità;
	}
	public String getPagato() {
		return pagato;
	}

//SETTERS:
	public void setIdPaz(int idPaz) {
		this.idPaziente = idPaz;
	}
	public void setDataGiorno(java.sql.Date dataGiorno) {
		this.dataGiorno = dataGiorno;
	}
	public void setOraInizio(String oraInizio) {
		this.oraInizio = oraInizio;
	}
	public void setOraFine(String oraFine) {
		this.oraFine = oraFine;
	}
	public void setModalità(String mod) {
		this.modalità = mod;
	}
	public void setPagato(String pagato) {
		this.pagato = pagato;
	}
	
}
