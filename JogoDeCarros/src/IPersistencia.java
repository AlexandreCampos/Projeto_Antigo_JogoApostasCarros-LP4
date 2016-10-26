/**
 * Interface IPersistencia
 * @author Alexandre
 * @version 1.0
 */

public interface IPersistencia {
	
	String getPistaFacil(int i);
	String getPistaMedia(int i);
	String getPistaDificil(int i);
	
	void armazenarDadosJogador(Jogador jogador);
	void carregarDadosJogador(String arquivo) ;
	void criarJogador();
	void salvarJogador(Jogador jogador);	
	
	void acessarBD(); 
	void criarBD();
	void inserirBD();
	
	boolean getConfigurado();
}
