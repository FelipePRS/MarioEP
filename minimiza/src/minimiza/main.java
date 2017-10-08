package minimiza;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class main {
	
	public static int numEstados;
	public static int numSimbolosAlfabeto;
	public static int estadoInicial;	
	public static int[][] matriz;	
	public static boolean estadosAcessiveis[];
	public static boolean estadosAceitacao[];
	public static boolean mark[][];
	public static boolean estadoVisitado[];

	public static void main(String[] args) {
		
			File arquivo = new File("C:\\Users\\Felipe souza\\eclipse-workspace\\minimiza\\Arquivos\\Teste.txt");			
			
			StringBuffer stringBuffer = lerArquivo(arquivo);
			
			buscarInformacoesPrincipais(stringBuffer);			
			
			montarMatriz(stringBuffer);	
			
			procurarEstadosInacessiveis();
			comparar();		
	}
	
		
	public static StringBuffer lerArquivo(File file) {
		
		StringBuffer stringBuffer = new StringBuffer();
		
		try {
			
			FileReader fileReader = new FileReader(file);
			
			BufferedReader bufferedReader = new BufferedReader(fileReader);			
			String line;
			
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			
			fileReader.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return stringBuffer;
		
	}
	
	public static void buscarInformacoesPrincipais(StringBuffer stringBuffer) {
		
		String[] linhas = stringBuffer.toString().split("\n");
		
		String linhaPrincipal = linhas[0];
		
		String[] splitLinhaPrincipal = linhaPrincipal.split(" ");		
		
		numEstados = Integer.valueOf(splitLinhaPrincipal[0]); 
		numSimbolosAlfabeto = Integer.valueOf(splitLinhaPrincipal[1]); 
		estadoInicial = Integer.valueOf(splitLinhaPrincipal[2]); 
		
		String linhaEstadosAceitacao = linhas[1];
		
		String[] splitLinhaEstadosAceitacao = linhaEstadosAceitacao.split(" ");	
		
		estadosAceitacao = new boolean[numEstados];
		
		for(int i = 0; i < numEstados; i++) {
			
			int aceitacao = Integer.valueOf(splitLinhaEstadosAceitacao[i]);
			if(aceitacao == 1)
				estadosAceitacao[i] = true;
		}
	
	}
	
	public static void montarMatriz(StringBuffer stringBuffer) {
		
		matriz = new int[numEstados][numSimbolosAlfabeto];
		
		String[] linhas = stringBuffer.toString().split("\n");
						
		for(int i = 2; i < numEstados + 2; i++){
		    
			String [] colunas = linhas[i].split(" ");
			
			for(int j = 0; j < numSimbolosAlfabeto; j++) {
				
				int mLinha = i - 2;
				
				matriz[mLinha][j] = Integer.valueOf(colunas[j]);
				
			}			
		}
	}
	
	public static void procurarEstadosInacessiveis() {
		
		estadoVisitado = new boolean[numEstados];
		estadosAcessiveis = new boolean[numEstados];
		
		boolean continuar = true;
		
		
		estadosAcessiveis[estadoInicial] = true;
		
		while ( continuar )	{
			continuar = false;
			for ( int estado = 0; estado < numEstados; estado++ )	{
				if ( estadosAcessiveis[estado] && !estadoVisitado[estado] )	{
					estadoVisitado[estado] = true;
					continuar = true;
					for ( int simbolo = 0; simbolo < numSimbolosAlfabeto; simbolo++ )	{

						if(matriz[estado][simbolo] != -1) {
							estadosAcessiveis[ matriz[estado][simbolo] ] = true;
						}
					}
				}
			}
		}
	}
	
	
	public static void comparar() {
						
		mark = new boolean[numEstados][numEstados];
		
		for ( int statenum = 0; statenum < numEstados; statenum++ )	{
			if ( !estadosAcessiveis[statenum] )	continue;
			for ( int statenum2 = 0; statenum2 < numEstados; statenum2++ )	{
				if ( !estadosAcessiveis[statenum2] )	continue;
				if ( estadosAceitacao[statenum] != estadosAceitacao[statenum2] ) {
					mark[statenum][statenum2] = true;
					System.out.println("---" + statenum + " " + statenum2);
				}
				else {
					mark[statenum][statenum2] = false;					
				}
			}
		}
		
		
	}
		
}
