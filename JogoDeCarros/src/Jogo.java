import javax.swing.JOptionPane;

/**Classe Jogo, onde fica o "jogo" em si.
 * @author Alexandre 
 * @version 1.0
 */


public class Jogo {

	private int idPista;	
	private int idCarro;	
	private int numeroCarros = 4;		
	private Carro carro[];	 	
	private Pista pista;
	private Jogador jogador;
	private Persistencia persistencia;	
	private String textoFinal = "";


	/**
	 * Construtor da classe Jogo, que inicializa os objetos Jogador e Persistencia
	 * @author Alexandre
	 * @param j 
	 * @param p
	 */
	public Jogo(Jogador j, Persistencia p){
		persistencia = p;
		jogador = j;
	}


	/**
	 * Método que carrega interface gráfica de Escolha de Carros e Pistas
	 * @author Alexandre
	 */
	public void carregarTelaEscolha(){		
		TelaEscolha telaE = new TelaEscolha(this);	
		telaE.iniciarTelaEscolha();		
	}


	/**
	 * Método que recebe o ID da pista selecionada pelo usuário na Tela de Escolha
	 * @author Alexandre
	 * @param id int - id da pista selecionada pelo usuário
	 */
	public void setPista(int id){
		idPista = id;		
	}
	 
	/** Método que recebe o ID do carro selecionado pelo usuário na Tela de Escolha
	 * @author Alexandre
	 * @param id2 - id do carro selecionado pelo usuário 	 
	 */
	public void setCarro(int id2){
		idCarro = id2;				
	}


	/**
	 * Método que define os carros e a pista selecionada e prepara a corrida
	 * @author Alexandre
	 */
	public void iniciarCorrida(){

		// carregando pistas		
		pista = new Pista(idPista, persistencia);		

		// carregando carros		
		carro = new Carro[numeroCarros];
		carro = (Carro[]) persistencia.getCarros(carro);

		// personalizando... (caso houvesse a personalização dos carros na tela de escolha)
		//if (idPersonalizar){
		//personalizarCarros();		
		//}

		// iniciando simulação da corrida			
		simularCorridaThreads(carro, pista);		
		jogador.numeroCorridas = jogador.numeroCorridas + 1;			


		// loop de jogar novamente...
		String c = JOptionPane.showInputDialog("Deseja jogar de novo? ");
		if (c.equals("s")){
			iniciarCorrida();
		}else{
			System.out.println(jogador.nome + jogador.numeroCorridas + jogador.numeroVitorias);
			persistencia.salvarJogador(jogador);	
		}

	}


	/**
	 * Método que inicia a simulação da corrida com Threads
	 * @author Alexandre
	 * @param carro[] Carro
	 * @param pista Pista
	 */
	public void simularCorridaThreads(Carro[] carro, Pista pista){

		SimulacaoThreads simulacao;
		for(int i = 0; i < 4; i++){
			simulacao = new SimulacaoThreads(this, pista, carro, i);
			simulacao.start();
		}
	}





	/**
	 * Método que mostra resultado (quem ganhou)
	 * Retorna 1 para ganhou e 0 para perdeu
	 * @author Alexandre
	 * @param t String
	 */
	public int resultado(String t){

		String t2="";
		// algoritimo que gera o maior
		int maior = 0;
		int ganhou = 0;		
		for (int i = 0; i < numeroCarros; i++){
			if (carro[i].quilometragem > maior){
				maior = carro[i].quilometragem;
				ganhou = i;
			}			
			t2 = t2 + "\nQuilometragem do carro " + i + ":" + carro[i].quilometragem;
			System.out.println("Quilometragem do carro " + i + ":" + carro[i].quilometragem);			
		}		
		System.out.println("Carro " + ganhou + " ganhou.");		
		jogador.numeroVitorias = jogador.numeroVitorias + resultado();
		textoFinal = t + t2 + "\n\nCarro " + ganhou + " ganhou.";

		if(idCarro == ganhou){
			return 1;
		}else {
			return 0;
		}

	}


	/**
	 * Método que mostra resultado (quem ganhou)
	 * Retorna 1 para ganhou e 0 para perdeu
	 * @author Alexandre
	 */
	public int resultado(){

		// algoritimo que gera o maior
		int maior = 0;
		int ganhou = 0;
		for (int i = 0; i < numeroCarros; i++){
			if (carro[i].quilometragem > maior){
				maior = carro[i].quilometragem;
				ganhou = i;
			}
			//System.out.println("Quilometragem do carro " + i + ":" + carro[i].quilometragem);			
		}		
		//System.out.println("Carro " + ganhou + " ganhou.");		
		//textoFinal = t + textoFinal + "Carro " + ganhou + " ganhou.";

		if(idCarro == ganhou){
			return 1;
		}else {
			return 0;
		}

	}





	
	public String getTextoFinal(){		
		return textoFinal;
	}

	public int getNumeroCorridas(){ 
		return jogador.numeroCorridas;	
	}

	public int getNumeroVitorias(){ 
		return jogador.numeroVitorias;
	}










	void personalizarCarros(){
		TelaPersonalizar telaP = new TelaPersonalizar();		
		//codigo de personalizar carros...
	}




}
