/**
 * Classe que gera a interface gr�fica da Simula��o
 * Codigo da TelaSimula��o mais simples, gerado "� m�o"
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
		jLabel1 = new JLabel("N�mero de Corridas:");
		jLabel2 = new JLabel("N�mero de Vit�rias");		
		jTextField1.setText(Integer.toString(jogo.getNumeroCorridas()));
		jTextField2.setText(Integer.toString(jogo.getNumeroVitorias()));
		jTextArea1.setText(jogo.getTextoFinal());


		// Adiciona os elementos � janela		
		c.add(jLabel1);
		c.add(jTextField1);
		c.add(jLabel2);		
		c.add(jTextField2);
		c.add(jTextArea1);


		setSize(750, 850);
		setTitle("Tela de Simula��o da Corrida");
		setVisible(true);
	}

	/**
	 * M�todo que mostra a Tela ao usu�rio
	 * @author Alexandre 
	 */
	public void iniciarTelaSimulacao(){
		TelaSimulacao app = new TelaSimulacao(jogo);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}