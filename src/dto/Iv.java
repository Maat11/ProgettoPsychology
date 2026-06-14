package dto;

public class Iv {
	private int idPaz;
	private String iv_codice_fiscale;
	private String iv_telefono;
	private String iv_email;
	
//COSTRUTTORE:
	public Iv(int idPaz, String iv_codice_fiscale, String ivTelefono) {
		this.idPaz = idPaz;
		this.iv_codice_fiscale = iv_codice_fiscale;
		this.iv_telefono = ivTelefono;
	}
	
//GETTERS:
	public int getIdPaz() {
		return idPaz;
	}
	public String getIv_codice_fiscale() {
		return iv_codice_fiscale;
	}
	public String getIv_telefono() {
		return iv_telefono;
	}
	public String getIv_email() {
		return iv_email;
	}

//SETTERS:
	public void setIdPaz(int idPaz) {
		this.idPaz = idPaz;
	}
	public void setIv_codice_fiscale(String iv) {
		this.iv_codice_fiscale = iv;
	}
	public void setIv_telefono(String ivTel) {
		this.iv_telefono = ivTel;
	}
	public void setIv_email(String ivEmail) {
		this.iv_email = ivEmail;
	}
	
}
