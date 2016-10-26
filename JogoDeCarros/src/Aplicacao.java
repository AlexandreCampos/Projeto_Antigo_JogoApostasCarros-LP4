/**
 * Classe Aplica��o
 * @author Alexandre
 * @version 1.0
 */

import javax.swing.JOptionPane;


public class Aplicacao {

	private Persistencia p;
	private String arquivo;


	/** 
	 * Construtor da classe aplica��o, que instancia a classe Persistencia
	 * @author Alexandre
	 */
	public Aplicacao(){
		p = new Persistencia();
	}


	/** 
	 * M�todo que inicia aplica��o
	 * @author Alexandre
	 */
	public void iniciarAplicacao(){					

		// Configura o BD automaticamente, caso n�o tenha sido configurado no PC
		System.out.println(p.getConfigurado());
		if (p.getConfigurado() != true){			
			p.criarBD();
			p.inserirBD();		
		}

		// se j� ha BD configurado, basta acess�-lo
		p.acessarBD();


		TelaCarregar telaC = new TelaCarregar(this);
		telaC.selecionarJogador(); //carrega Tela de selecionar novo jogador
		telaC.iniciarTelaCarregar(); // carrega Tela de inicio do jogo

	}


	/**
	 * M�todo que pega o arquivo onde est�o os dados do Jogador
	 * @author Alexandre
	 * @param arquivo String
	 */
	public void getArquivoJogador(String arquivo){
		this.arquivo = arquivo; // armazena o arquivo do jogador
	}


	/**
	 * M�todo que organiza os dados para iniciar o Jogo de Carros
	 * @author Alexandre
	 */
	public void iniciarJogo(){

		Jogador jogador = new Jogador();

		p.carregarDadosJogador(arquivo); // carrega dados salvos do jogador		
		p.armazenarDadosJogador(jogador); // envia dados para objeto jogador	

		Jogo jogo = new Jogo(jogador, p);
		jogo.carregarTelaEscolha();		

	}




}
