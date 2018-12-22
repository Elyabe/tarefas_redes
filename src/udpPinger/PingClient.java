/*
 * Para rodar, execute o main do servidor e em seguida o main do Cliente
 * 
 */

package udpPinger;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Random;

class PingClient
{
   public static int TEMPO_MAXIMO_ESPERA = 100, QTD_DATAGRAMAS = 10;
   
   public static void main(String args[]) throws Exception
   {
      BufferedReader entradaUsuario = new BufferedReader(new InputStreamReader(System.in));
      DatagramSocket socket = new DatagramSocket();
      Date dataAtual = new Date();
      byte[] cargaUtilEnviar;
      byte[] cargaUtilReceber;
      long tempoEnvio, tempoRecebido;
      Random valorAleatorio = new Random( 1000 );
      
//	  Considerando que estamos rodando Servidor no mesmo computador
      InetAddress enderecoIP = InetAddress.getByName("localhost");
      
      for ( int idDatagrama = 0; idDatagrama < QTD_DATAGRAMAS; idDatagrama++ )
      {
//        Monta o datagrama
    	  cargaUtilEnviar = new byte[1024];
//    	  Descomente a proxima linha para definir a carga util do datagrama
//          String carga = entradaUsuario.readLine();
    	  String carga = "Dados-" + Double.toString( valorAleatorio.nextDouble() );
          cargaUtilEnviar = carga.getBytes();
          
          DatagramPacket PacoteEnviar = new DatagramPacket(cargaUtilEnviar, cargaUtilEnviar.length, enderecoIP, 12345);
          
    	  tempoEnvio = dataAtual.getTime();
    	  socket.send(PacoteEnviar);
          
          try 
          {
        	  	cargaUtilReceber = new byte[1024];
        	  	DatagramPacket receivePacket = new DatagramPacket(cargaUtilReceber, cargaUtilReceber.length);
        	  	socket.setSoTimeout( TEMPO_MAXIMO_ESPERA );
        	  	socket.receive(receivePacket);
        	  	tempoRecebido = dataAtual.getTime();
        	  	
        	  	// Extrai os dados do datagrama recebido
        	  	String cargaRecebida = new String(receivePacket.getData());
        	    System.out.println("Reposta do servidor:" + cargaRecebida + "\r\n"
        	    				  + "Tempo de resposta: " + (tempoRecebido - tempoEnvio) );
          } catch (IOException e) 
          {
    			System.err.println("TimeOut para pacote " + idDatagrama );
    	  }
          Thread.sleep(1000);
      }
      
      socket.close();
   }
}