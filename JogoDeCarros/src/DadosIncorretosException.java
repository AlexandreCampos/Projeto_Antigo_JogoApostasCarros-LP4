/**
 * Classe "pronta" que faz o tratamento de erro na manipulação de arquivos
 * @author Alexandre
 * @version 1.0
 */


public class DadosIncorretosException extends Exception {

	private static final long serialVersionUID = 1L;
	
	DadosIncorretosException() {
        super("Arquivo texto com dados incorretos.");
    }
    DadosIncorretosException(String msg) {
        super(msg);
    }
}
