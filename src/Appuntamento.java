
public class Appuntamento {
	private int idAgenda;
	private java.sql.Date dataGiorno;
	private String oraInizio;
	private String oraFine;
	private String modalità;
	
	Appuntamento(java.sql.Date dataGiorno, String oraInizio, String oraFine, String modalità){
		this.dataGiorno = dataGiorno;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.modalità = modalità;
	}
	
//GETTERS:
	public int getIdAgenda() {
		return idAgenda;
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

//SETTERS:
	public void setIdAgenda(int idAgenda) {
		this.idAgenda = idAgenda;
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
}
