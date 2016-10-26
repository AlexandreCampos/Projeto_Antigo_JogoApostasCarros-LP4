/**
 * Classe que gera a interface gráfica da Simulação
 * Codigo da TelaSimulação mais simples, gerado "à mão"
 * @author Alexandre
 * @version 1.0
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class TelaSimulacao extends JFrame{

	JTextArea jTextArea1;
	JTextField jTextField1;
	JTextField jTextField2;
	JLabel jLabel1;
	JLabel jLabel2;

	Jogo jogo;  
	
	
	/**
	 * Construtor define os elementos das telas  	
	 * @author Alexandre
	 * @param j
	 */
	public TelaSimulacao(Jogo j) {
		super("TelaSimulacao Swing");
		jogo = j;    

		Container c = getContentPane();
		c.setLayout(new FlowLayout(FlowLayout.LEFT));


		// Cria JTextField, JText Area e JLabel
		jTextArea1 = new JTextArea(100,100);
		jTextField1 = new JTextField(10);
		jTextField2 = new JTextField(10);
		jLabel1 = new JLabel("Número de Corridas:");
		jLabel2 = new JLabel("Número de Vitórias");		
		jTextField1.setText(Integer.toString(jogo.getNumeroCorridas()));
		jTextField2.setText(Integer.toString(jogo.getNumeroVitorias()));
		jTextArea1.setText(jogo.getTextoFinal());


		// Adiciona os elementos à janela		
		c.add(jLabel1);
		c.add(jTextField1);
		c.add(jLabel2);		
		c.add(jTextField2);
		c.add(jTextArea1);


		setSize(750, 850);
		setTitle("Tela de Simulação da Corrida");
		setVisible(true);
	}

	/**
	 * Método que mostra a Tela ao usuário
	 * @author Alexandre 
	 */
	public void iniciarTelaSimulacao(){
		TelaSimulacao app = new TelaSimulacao(jogo);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}