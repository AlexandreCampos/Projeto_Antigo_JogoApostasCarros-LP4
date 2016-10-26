/**
 * Classe Persistência contém os métodos que lidam com banco de dados e arquivos 
 * Esta classe implementa a interface IPersistencia
 * @author Alexandre
 * @version 1.0
 */

import java.io.*;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;


public class Persistencia implements IPersistencia{

	private String dadosJogador[];
	private Carro carro[];	
	private String arquivo;
	private boolean configurado = false; 

	private int motor[];
	private int freio[];
	private int rodas[];
	private int cheat[];

	private String pistaFacil[];
	private String pistaMedia[];
	private String pistaDificil[];


	/**
	 * Construtor inicializa atributos dos objetos Pista e Carro
	 * @author Alexandre  
	 */
	public Persistencia(){

		motor = new int[4];
		freio = new int[4];
		rodas = new int[4];
		cheat = new int[4];

		pistaFacil = new String[8];
		pistaMedia = new String[8];
		pistaDificil = new String[8];	

	}


	/**
	 * Método envia atributos recebidos pelo BD ao Objeto Carro
	 * @author Alexandre
	 * @param carro Carro
	 * @return carro
	 */
	public Object getCarros(Carro carro[]){

		carro[0] = new Carro(motor[0],freio[0],rodas[0],cheat[0]);		
		carro[1] = new Carro(motor[1],freio[1],rodas[1],cheat[1]);
		carro[2] = new Carro(motor[2],freio[2],rodas[2],cheat[2]);
		carro[3] = new Carro(motor[3],freio[3],rodas[3],cheat[3]);

		return carro;
	}


	/**
	 *  Método que envia pista fácil
	 *  @author Alexandre
	 *  @param i - variável de controle de cada trecho da pista
	 *  @return pistaFacil - atributos (caracteristicas) da pista fácil
	 */
	public String getPistaFacil(int i){				
		return pistaFacil[i];	
	}

	/**
	 * Método que envia pista média
	 *  @author Alexandre
	 *  @param i - variável de controle de cada trecho da pista
	 *  @return pistaMedia - atributos (caracteristicas) da pista média
	 */	 
	public String getPistaMedia(int i){
		return pistaMedia[i];	
	}

	/**
	 * Método que envia pista difícil
	 *  @author Alexandre
	 *  @param i - variável de controle de cada trecho da pista
	 *  @return pistaDificil - atributos (caracteristicas) da pista difícil
	 */	
	public String getPistaDificil(int i){		
		return pistaDificil[i];
	}




	/**
	 * Método que armazena dados recebidos pelo arquivo texto para Objeto jogador
	 * @author Alexandre
	 * @param jogador Jogador
	 */
	public void armazenarDadosJogador(Jogador jogador){

		jogador.nome = dadosJogador[0];		
		jogador.numeroCorridas = Integer.parseInt(dadosJogador[1]);
		jogador.numeroVitorias = Integer.parseInt(dadosJogador[2]);
		//jogador.idJogador = Integer.parseInt(dadosJogador[3]);

	}



	/**
	 * Método que carrega os dados do jogador salvos no arquivo texto
	 * @author Alexandre
	 * @param arquivo String
	 */
	public void carregarDadosJogador(String arquivo) {

		File file = null;		
		boolean repetir = false;
		this.arquivo = arquivo;
		// Faz leitura do nome do arquivo
		do {
			repetir = false;			
			file = new File(this.arquivo);
			if (!file.exists()) { // Verifica se o qruivo existe
				String sn = JOptionPane.showInputDialog("Arquivo:" + this.arquivo + " inexistente, deseja tentar de novo?(s/n): ");
				if (sn.toUpperCase().charAt(0) == 'S') {					
					this.arquivo = JOptionPane.showInputDialog("Digite o arquivo do seu jogador: ");
					repetir = true;
				} else {
					System.exit(0);
				}
			}
		} while(repetir);

		// Classes para leitura de dados
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(arquivo);
			br = new BufferedReader(fr);
		} catch(FileNotFoundException ex) {
			System.out.println("Arquivo inexistente: " + this.arquivo);
			return;
		} catch(Exception ex) {
			System.out.println("Erro inesperado ao tentar abrir o arquivo: " + this.arquivo);
			ex.printStackTrace();
			return;
		}


		dadosJogador = new String[3];
		int i = 0;

		// Leitura de dados
		try {
			String line = br.readLine();
			while(line != null) { // Enquanto existir linha no arquivo
				String separadores = "\t\n\r\f"+ "|";
				StringTokenizer st = new StringTokenizer(line, separadores);

				// String	
				if (st.hasMoreTokens()) {

					dadosJogador[i] = st.nextToken();
					//System.out.println("Leu o String: " + dadosJogador[i] + " do disco");						
				} else {
					throw new DadosIncorretosException();
				}

				i = i+1;
				line = br.readLine(); // Le a proxima linha
			}

		} catch(DadosIncorretosException ex) {
			System.out.println("Erro: " + ex.getMessage());
			return;
		} catch(EOFException ex) {
			System.out.println("Atingiu prematuramente o final do arquivo: " + this.arquivo);
			return;
		} catch(IOException ex) {
			System.out.println("Nao conseguiu ler do arquivo: " + this.arquivo);
			return;
		} catch(Exception ex) {
			System.out.println("Erro inesperado ao tentar ler o arquivo: " + this.arquivo);
			ex.printStackTrace();
			return;
		} finally {
			try {
				br.close();
			} catch(Exception ex) {
				// Nao faz nada !
			}
			try {
				fr.close();
			} catch(Exception ex) {
				// Nao faz nada !
			}
		}

	}





	/**
	 * Método que cria um novo arquivo para salvar os dados, caso o usuário escolha um Novo Jogador
	 * @author Alexandre
	 */
	public void criarJogador(){

		File file = null;
		String arquivo = null;
		String sn = "S";

		do {
			arquivo = JOptionPane.showInputDialog("Arquivo: ");
			file = new File(arquivo);
		} while(sn.toUpperCase().charAt(0) == 'N');

		char separador = '|'; // caractere que sera utilizado para separar os dados

		// Classes de gravacao de dados
		PrintWriter pw = null;
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
		} catch(IOException ex) {
			System.out.println("Nao conseguiu abrir o arquivo: " + arquivo);
			return;
		} catch(Exception ex) {
			System.out.println("Erro inesperado ao tentar abrir o arquivo: " + arquivo);
			return;
		}

		// Dados a serem gravados
		String a = JOptionPane.showInputDialog("Forneça seu nome: ");
		String b = "0";
		String s = "0";

		// Gravacao dos dado no arquivo
		try {

			pw.println(a);				
			pw.println(b);				
			pw.println(s);

		} catch(Exception ex) {
			System.out.println("Erro inesperado ao tentar escrever no arquivo: " + arquivo);
			return;
		} finally {
			try {
				pw.close();
			} catch(Exception ex) {
				// Nao faz nada!
			}
			try {
				fos.close();
			} catch(Exception ex) {
				// Nao faz nada!
			}
		}
	}


	// ao fim do jogo, o programa salva os dados atualizados do usuarios no arquivo texto
	//(nome, numero de corridas e numero de vitorias)
	/**
	 * Método que salva dados atualizados do usuário no arquivo
	 * @author Alexandre
	 * @param jogador Jogador
	 */
	public void salvarJogador(Jogador jogador){

		File file = null;		
		String sn = "S";

		do {
			file = new File(arquivo);
		} while(sn.toUpperCase().charAt(0) == 'N');

		char separador = '|'; // caractere que sera utilizado para separar os dados

		// Classes de gravacao de dados
		PrintWriter pw = null;
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
		} catch(IOException ex) {
			System.out.println("Nao conseguiu abrir o arquivo: " + arquivo);
			return;
		} catch(Exception ex) {
			System.out.println("Erro inesperado ao tentar abrir o arquivo: " + arquivo);
			return;
		}

		// Dados a serem gravados
		String a = jogador.nome;
		int b = jogador.numeroCorridas;
		int s = jogador.numeroVitorias;

		// Gravacao dos dado no arquivo
		try {

			pw.println(a);				
			pw.println(b);				
			pw.println(s);

		} catch(Exception ex) {
			System.out.println("Erro inesperado ao tentar escrever no arquivo: " + arquivo);
			return;
		} finally {
			try {
				pw.close();
			} catch(Exception ex) {
				// Nao faz nada!
			}
			try {
				fos.close();
			} catch(Exception ex) {
				// Nao faz nada!
			}
		}
	}



	/**
	 * Método que faz a conexão e acesso a um banco de dados mysql 
	 * @author Alexandre
	 */
	public void acessarBD(){

		Connection con;
		Statement st;
		String url = "jdbc:mysql://localhost:3306/JogoCarros";

		try{
			con=connectDB(url);	
			st = con.createStatement();  
			String SQL = "SELECT motor from carros";			
			ResultSet rs = st.executeQuery(SQL);			
			while(rs.next())				
				for(int i = 0; i < 4; i++ ){								
					motor[i] = rs.getInt(1);
					rs.next();
				}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}      

		try{
			con=connectDB(url);	
			st = con.createStatement();  
			String SQL = "SELECT freio from carros";			
			ResultSet rs = st.executeQuery(SQL);			
			while(rs.next())				
				for(int i = 0; i < 4; i++ ){
					freio[i] = rs.getInt(1);
					rs.next();									
				}			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}  

		try{
			con=connectDB(url);	
			st = con.createStatement();  
			String SQL = "SELECT rodas from carros";			
			ResultSet rs = st.executeQuery(SQL);			
			while(rs.next())				
				for(int i = 0; i < 4; i++ ){									
					rodas[i] = rs.getInt(1);
					rs.next();
				}			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}  

		try{
			con=connectDB(url);	
			st = con.createStatement();  
			String SQL = "SELECT cheat from carros";			
			ResultSet rs = st.executeQuery(SQL);			
			while(rs.next())				
				for(int i = 0; i < 4; i++ ){	
					cheat[i] = rs.getInt(1);
					rs.next();				
				}			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}  

		try{
			con=connectDB(url);	
			st = con.createStatement();  
			String SQL = "SELECT PistaFacil from pistas";			
			ResultSet rs = st.executeQuery(SQL);			
			while(rs.next())				
				for(int i = 0; i < 8; i++ ){
					pistaFacil[i] = rs.getString(1);
					rs.next();
				}			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}  

		try{
			con=connectDB(url);	
			st = con.createStatement();  
			String SQL = "SELECT PistaMedia from pistas";			
			ResultSet rs = st.executeQuery(SQL);			
			while(rs.next())				
				for(int i = 0; i < 8; i++ ){
					pistaMedia[i] = rs.getString(1);
					rs.next();									
				}			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}  

		try{
			con=connectDB(url);	
			st = con.createStatement();  
			String SQL = "SELECT PistaDificil from pistas";			
			ResultSet rs = st.executeQuery(SQL);			
			while(rs.next())				
				for(int i = 0; i < 8; i++ ){
					pistaDificil[i] = rs.getString(1);
					rs.next();										
				}			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}  

	}




	/**
	 * Método que cria o BD 
	 * Este método é executado apenas 1 vez no sistema, caso BD não seja deletado
	 * @author Alexandre
	 */
	public void criarBD(){

		Connection con;
		Statement st;
		String url = "jdbc:mysql://localhost:3306/";

		// criando banco de dados
		try{
			con=connectDB(url);	
			st = con.createStatement();  
			String SQL = "Create Database JogoCarros";
			st.execute(SQL);
			/*ResultSet rs = st.executeQuery(SQL);
			 while(rs.next())
			 System.out.println(rs.getString(1));*/
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}      

	}

	/**
	 * Método que faz a configuração inicial padrão do jogo
	 * Este método é executado apenas 1 vez no sistema, caso BD não seja deletado
	 * @author Alexandre
	 */
	public void inserirBD(){

		Connection con;
		Statement st;
		String url = "jdbc:mysql://localhost:3306/JogoCarros";

		// criando tabela carros no BD
		try{
			con=connectDB(url);	
			st = con.createStatement(); 
			String SQL = ("CREATE TABLE carros " +
			"(Nome VARCHAR(60), Motor INT(3), Freio INT(3), Rodas INT(3), Cheat INT(2))");
			if (st.execute(SQL))
				System.out.println("OK");
			else
				System.out.println("NOK");

		}catch(SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		}catch(Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}      

		// criando tabela pistas no BD
		try{
			con=connectDB(url);	
			st = con.createStatement(); 
			String SQL = ("CREATE TABLE pistas " +
			"(PistaFacil VARCHAR(60), PistaMedia VARCHAR(60), PistaDificil VARCHAR(60))");
			if (st.execute(SQL))
				System.out.println("OK");
			else
				System.out.println("NOK");

		}catch(SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		}catch(Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}   

		// criando tabela jogador no BD
		try{
			con=connectDB(url);	
			st = con.createStatement(); 
			String SQL = ("CREATE TABLE jogador " +	"(Config VARCHAR(60))");
			if (st.execute(SQL))
				System.out.println("OK");
			else
				System.out.println("NOK");

		}catch(SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		}catch(Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}  


		/* Inserindo dados nas tabelas
		 **/ 
		try{
			con=connectDB(url);	
			st = con.createStatement();  
			insertCarro(st, "carro1",100,60,100,0);
			insertCarro(st, "carro2",90,80,90,0);      
			insertCarro(st, "carro3",95,70,80,0);
			insertCarro(st, "carro4",90,80,90,0);

		}catch(SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		}catch(Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}  

		try{
			con=connectDB(url);	
			st = con.createStatement();  
			insertPista(st, "retaTotal","curvaSimples","curvaSimples");
			insertPista(st, "curvaSimples","retaParcial","retaParcial");
			insertPista(st, "retaTotal","curvaMedia","curvaMedia");
			insertPista(st, "curvaSimples","retaParcial","retaTotal");
			insertPista(st, "retaTotal","curvaMedia","curvaFechada");
			insertPista(st, "curvaSimples","retaTotal","retaTotal");
			insertPista(st, "retaTotal","curvaSimples","curvaSuperFechada");
			insertPista(st, "curvaSimples","retaTotal","retaTotal");		

		}catch(SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		}catch(Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}  

		try{
			con=connectDB(url);	
			st = con.createStatement();  
			insertJogador(st, "configurado");

		}catch(SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		}catch(Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}  


	}


	private void insertCarro(Statement st, String nome, int motor, int freio, int rodas, int cheat) throws Exception {
		try {

			String SQL = ("INSERT INTO carros " +
					"VALUES ('" + nome + "', '" + motor + "', '" + freio + "', '" + rodas + "', '" + cheat + "')");
			st.execute(SQL);

		}
		catch(Exception e) {
			throw new Exception(e.getMessage());

		}
	}

	private void insertPista(Statement st, String PistaFacil, String PistaMedia, String PistaDificil) throws Exception {
		try {

			String SQL = ("INSERT INTO pistas " +
					"VALUES ('" + PistaFacil + "', '" + PistaMedia + "', '" + PistaDificil + "')");
			st.execute(SQL);

		}
		catch(Exception e) {
			throw new Exception(e.getMessage());

		}  
	}

	private void insertJogador(Statement st, String Config) throws Exception {
		try {

			String SQL = ("INSERT INTO jogador " +
					"VALUES ('" + Config + "')");
			st.execute(SQL);

		}
		catch(Exception e) {
			throw new Exception(e.getMessage());

		}  
	}


	Connection connectDB(String url) throws Exception {
		try {

			String user, password; 	
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			user="root";password="";
			return DriverManager.getConnection(url,user, password);

		}
		catch(Exception e) {
			throw new Exception(e.getMessage());

		}    	


	}









	/**
	 * Método assegura que o jogo tenha o BD configurado
	 * É selecionado campo "config" na tabela "jogador"... Se BD congirurado, campo possui "configurado" 
	 * se não há BD configurado no PC, dá msg de erro e "boolean configurado" continua false
	 * se há BD condigurado np PC, "boolean configurado" retorna sempre true e não faz mais nada
	 * @author Alexandre
	 * @return configurado Boolean - retorna true, se BD já foi configurado
	 */
	public boolean getConfigurado(){
		Connection con;
		Statement st;
		String url = "jdbc:mysql://localhost:3306/JogoCarros";
		String s="";
		try{
			con=connectDB(url);	
			st = con.createStatement();  
			String SQL = "SELECT config from jogador";			
			ResultSet rs = st.executeQuery(SQL);			
			while(rs.next())	
				s = rs.getString(1);
			if (s.equals("configurado")){
				configurado = true;
			}

		}catch(Exception e) {
			System.out.println(e.getMessage());
		}  
		return configurado;
	}




}



/* Carros padrões
carro[0] = new Carro(100,60,100,0);
carro[1] = new Carro(90,80,90,0);
carro[2] = new Carro(95,70,80,0);
carro[3] = new Carro(90,80,90,0);	
 */

/* Pistas padrões
"retaTotal","curvaSimples","curvaSimples");
			insertPista(st, "curvaSimples","retaParcial","retaParcial");
			insertPista(st, "retaTotal","curvaMedia","curvaMedia");
			insertPista(st, "curvaSimples","retaParcial","retaTotal");
			insertPista(st, "retaTotal","curvaMedia","curvaFechada");
			insertPista(st, "curvaSimples","retaTotal","retaTotal");
			insertPista(st, "retaTotal","curvaSimples","curvaSuperFechada");
			insertPista(st, "curvaSimples","retaTotal","retaTotal");	
 */