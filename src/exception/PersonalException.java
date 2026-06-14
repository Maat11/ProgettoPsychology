package exception;

public class PersonalException extends Exception{

	public PersonalException(String msg) { 
		//LANCIA SOLO IL MESSAGGIO SENZA DARE I DETTAGLI ALL'UTENTE FINALE
		super(msg);
	}
	
}
