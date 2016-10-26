/**
 * Classe que define o Objeto Pista
 * @author Alexandre
 * @version 1.0
 */


public class Pista {

	Persistencia persistencia;

	String trecho[];
	int limite = 8;

	/**
	 * Construtor da classe pista define a pista escolhida
	 * Dados e características (atributos) da pista são armazenados e retirados do BD 
	 * @author Alexandre
	 * @param id - identificação da pista escolhida pelo usuário
	 * @param p
	 */
	public Pista(int id, Persistencia p)  {

		persistencia = p;

		// pista facil		
		if (id == 1){			
			trecho = new String[limite];						
			for (int i = 0; i < limite; i++){
				trecho[i] = persistencia.getPistaFacil(i);				
			}			
		}

		// pista media
		if (id == 2){
			trecho = new String[limite];								
			for(int i = 0; i < limite; i++){							
				trecho[i] = persistencia.getPistaMedia(i); 
			}
		}

		// pista dificil
		if (id == 3){
			trecho = new String[limite];								
			for(int i = 0; i < limite; i++){							
				trecho[i] = persistencia.getPistaDificil(i); 
			}
		}

	}








}
