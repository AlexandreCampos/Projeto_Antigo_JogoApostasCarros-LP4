/**
 * Classe que faz a simulação da Corrida com Threads
 * @author Alexandre
 * @version 1.0
 */

public class SimulacaoThreads extends Thread {

	Jogo jogo;
	Pista pista;
	Carro[] carro;
	int erroPiloto;
	int j;
	static int k = 0;
	static String t = "";	

	/**
	 * Construtor da classe recebe objetos da classe jogo
	 * @author Alexandre
	 * @version 1.0
	 * @param jog Jogo
	 * @param p Pista
	 * @param c Carro
	 * @param j int 
	 */
	public SimulacaoThreads(Jogo jog, Pista p, Carro[] c, int j){
		
		pista = p;
		carro = c;
		jogo = jog;
		this.j = j;
		//System.out.println(pista.limite);
		//System.out.println(pista.trecho[1]);
		//System.out.println(carro[0].getMotor());

	}



	/**
	 * Método executado quando a thread é "iniciada"
	 * @author Alexandre
	 * @version 1.0
	 */
	public void run() {
		correr();
	}


	/**
	 * Iniciando a simulação da corrida com Threads
	 * @author Alexandre
	 * @version 1.0
	 */
	private void correr(){


		for(int i = 0; i < pista.limite; i++){

			if (pista.trecho[i].equals("retaTotal")){
				carro[j].quilometragem = carro[j].quilometragem + carro[j].getMotor();				
			}

			if (pista.trecho[i].equals("retaParcial")){
				carro[j].quilometragem = carro[j].quilometragem -20 + carro[j].getMotor();				
			}				

			if (pista.trecho[i].equals("retaParcial")){
				carro[j].quilometragem = carro[j].quilometragem + carro[j].getFreio();				
			}

			if (pista.trecho[i].equals("curvaSimples")){
				carro[j].quilometragem = carro[j].quilometragem - 25 + carro[j].getFreio();
			}

			if (pista.trecho[i].equals("curvaMedia")){
				carro[j].quilometragem = carro[j].quilometragem - 50 + carro[j].getFreio();
			}

			if(carro[j].quilometragem > 100){
				carro[j].quilometragem = carro[j].quilometragem - 100 + carro[j].getRodas();
			}

			if(carro[j].quilometragem > 400 && carro[j].getCheat() == 9){
				carro[j].quilometragem = carro[j].quilometragem + 600;
			}


			System.out.print("Trecho da Corrida: " + pista.trecho[i] + " - Carro: " + j);
			System.out.print(" - Quilometragem: " + carro[j].quilometragem );

			erroPiloto = calcularErroPiloto(i, j);
			carro[j].quilometragem = carro[j].quilometragem - erroPiloto;

			t = t + "Trecho da Corrida: " + pista.trecho[i] + " - Carro: " + j + "- Quilometragem: " + carro[j].quilometragem + "\n";

			k = k + 1; // variável estatica faz o controle do ultimo loop

		}
		
		if(k == 32){ // termina a simulação, retorna ao jogo e mostra a Tela de Simulação 
			jogo.resultado(t);			
			TelaSimulacao telaS = new TelaSimulacao(jogo);					
			telaS.iniciarTelaSimulacao();
			k = 0;
			t = "";
			telaS.dispose(); // fecha a janela
		}

	}





	// para verificar se a simulação dos carros é feita corretamente,
	// basta retirar o "random" dos erros dos jogadores,
	// assim, os numeros de quilometragem ficam exatos


	/**
	 * Método que calcular erro do piloto
	 * Erro tem variabilidade maior em curvas maiores 
	 * @author Alexandre
	 * @version 1.0
	 * @param i int
	 * @param j int
	 * @return erroPiloto int - quanto maior o inteiro, maior o erro; erro 0 = sem erro
	 */
	int calcularErroPiloto(int i, int j){			

		if (pista.trecho[i].equals("curvaSimples")){
			erroPiloto = (int)((Math.random() * 30));					
		}else if (pista.trecho[i].equals("retaTotal")){
			erroPiloto = 0;
		}else if (pista.trecho[i].equals("retaMedia")){
			erroPiloto = 0;
		}else if (pista.trecho[i].equals("curvaMedia")){
			erroPiloto = (int)((Math.random() * 40));
		}else if (pista.trecho[i].equals("curvaFechada")){
			erroPiloto = (int)((Math.random() * 80));					
		}else if (pista.trecho[i].equals("curvaSuperFechada")){
			erroPiloto = (int)((Math.random() * 160));					
		}		
		System.out.println(" - Erro do piloto: " + erroPiloto);
		return erroPiloto;
	}


}