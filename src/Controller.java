
public class Controller {

//CLASSI:
	private Paziente paziente;
	private PazienteSqlDAO pazienteSqlDAO;
		
//PAGINE
	public PaginaPrincipale paginaPrincipale;
		
//FINESTRE
	public FinestraInserisciAppuntamento finestrainserisciAppuntamento;
		
//COSTRUTTORE:	
	Controller(){
		paginaPrincipale = new PaginaPrincipale(this);
		paginaPrincipale.setVisible(true);
	}
	
//MAIN	
	public static void main(String[] args) {
		Controller theController = new Controller();
	}
	
	
	
}
