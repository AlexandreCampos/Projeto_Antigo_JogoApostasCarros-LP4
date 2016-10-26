/**
 * Classe que define o Objeto Carro
 * Esta classe implementa a interface ICarro
 * @author Alexandre
 * @version 1.0
 */


public class Carro implements ICarro {

	int quilometragem = 0; 
	
	// caracteristicas do carro, que afetam desempenho
	private int motor;
	private int freio;
	private int rodas;
	private int cheat; // "cheats" nos jogos são "trapaças"
	
	// essas caracteristicas podem ser acrescentadas no código em uma versão posterior
	private int aceleracao;
	private int suspensao;
	private String cor;	
	private String nome;
	
	
	/**
	 * Construtor incializa atributos do carro
	 * @author Alexandre
	 * @param m int - motor do carro, definido no BD
	 * @param f int - freio do carro, definido no BD
	 * @param r int - rodas do carro, definido no BD
	 * @param c int - cheat (trapacear no jogo), definido no BD
	 */
	Carro(int m, int f, int r, int c){	
						
		motor = m;
		freio = f;
		rodas = r;
		cheat = c;
	}
		
	
	// gets ...
	public int getMotor(){
		return motor;
	}
	
	public int getFreio(){		
		return freio;
	}
		
	public int getRodas(){
		return rodas;
	}
	
	public int getCheat(){		
		return cheat;
	}
	
	

	/*Eu li no livro do Deitel que em alguns casos é útil utilizar sets para deixar o código mais robusto e seguro,
	 * no sentido de assegurar que as variáveis sejam inicializadas com valores válidos corretamente
	 * 
	 * Por isso, eu cheguei a fazer os sets abaixo, mas não deu tempo de perguntar ao professor se estava correto,
	 * então não cheguei a implementá-los, mas deixei-os aí como exemplo do que eu li no livro
	 * 
	 * 
	 * sets - verificador dos ints
	 * 
	 * private void setMotor(int m){
		if (m >= 0){
			motor = m;
		}
	}
	
	private void setFreio(int f){
		if (f >= 0){
			freio = f;
		}
	}
	
	private void setRodas(int r){
		if (r >= 0){
			rodas = r;
		}
	}
	
	private void setCheat(int c){
		if (c >= 0 && c <10){
			cheat = c;
		}
	}
	 * 
	 * 
	 */
	
	

}
