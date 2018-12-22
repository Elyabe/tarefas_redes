package webServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class WebServer {

	public static void main(String[] args) throws IOException {
		int porta = 8080;
		String request;
		String arquivo;
		
		System.err.println("AGUARDANDO REQUISICAO..");
		
		// socket para servidor  e cliente
		ServerSocket server = new ServerSocket(porta);
		Socket client = server.accept();
		
		//inicia buffer para  ler input e output do client
		BufferedReader recebeClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
		DataOutputStream enviaClient = new DataOutputStream(client.getOutputStream());
		
		
		
		//Salva tokens da requisicao
		request = recebeClient.readLine();
		StringTokenizer tokenizedLine = new StringTokenizer(request);

		//Se requisicao for do tipo Get, que eh unico que deve ser aceito, 
		//procura o arquivo e manda em bytes 
		if (tokenizedLine.nextToken().equals("GET")){
		    
		    arquivo = tokenizedLine.nextToken();
		    
		    if (arquivo.startsWith("/") == true )
		    	arquivo  = arquivo.substring(1);
		    System.out.println("Arquivo encontrado em " + arquivo);
		    try {
		    File file = new File(arquivo);
		    int numOfBytes = (int) file.length();
		    
		    FileInputStream inFile  = new FileInputStream (arquivo);
		    
		    byte[] fileInBytes = new byte[numOfBytes];
		    inFile.read(fileInBytes);
		    
		    enviaClient.writeBytes("HTTP/1.1 200 Conexao Ok\r\n");
		    
		    if (arquivo.endsWith(".jpg"))
		    	enviaClient.writeBytes("Content-Type: image/jpeg\r\n");
		    /*if (arquivo.endsWith(".gif"))
		    	enviaClient.writeBytes("Content-Type: image/gif\r\n");*/
		    if (arquivo.endsWith(".html"))
		    	enviaClient.writeBytes("Content-Type: txt/html; charset=utf-8\r\n");
		   /*  Em teste
		    if (arquivo.endsWith(".mp3"))
		    	enviaClient.writeBytes("Content-Type: audio/mp3\r\n");
		    if (arquivo.endsWith(".mp4"))
		    	enviaClient.writeBytes("Content-Type: video/mp4\r\n");*/
		    
		    enviaClient.writeBytes("Content-Length: " + numOfBytes + "\r\n");
		    enviaClient.writeBytes("\r\n");
		    
		    enviaClient.write(fileInBytes, 0, numOfBytes);
		    inFile.close(); 
			}catch(FileNotFoundException e) {
				enviaClient.writeBytes( "HTTP/1.1 404 NOT FOUND"+ "\r\n");
				enviaClient.writeBytes("Content-type: text/html" + "\r\n");
				enviaClient.writeBytes( "<HTML>" + 
					"<HEAD><TITLE>Arquivo nao encontrado</TITLE></HEAD>" +
					"<BODY>404 Pagina nao encontrada</BODY></HTML>"+ "\r\n");
			}
		    client.close();
		    server.close();
		    
		}
		
		else System.out.println("403 - Nao eh GET");
	
		System.err.println("FINALIZADO");
	}

}
