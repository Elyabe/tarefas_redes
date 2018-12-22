package udpPinger;

import java.net.*;
import java.util.Random;

class PingServer
{
   private static final double TAXA_PERDA = 0.35;
   private static final int ATRASO_MEDIO = 1000;

  public static void main(String args[]) throws Exception
  {
       DatagramSocket socketServidor = new DatagramSocket(12345);
       byte[] dadosRecebidos;
       byte[] dadosEnviados;
       Random sorteador = new Random( ATRASO_MEDIO );
       float numSortado;
       
       while(true)
       {
          System.err.println("AGUARDANDO REQUISICAO");
          dadosRecebidos = new byte[1024];
          DatagramPacket pacoteReceber = new DatagramPacket(dadosRecebidos, dadosRecebidos.length);
          
    	  socketServidor.receive(pacoteReceber);
          
          String sentence = new String( pacoteReceber.getData());
          System.out.println("RECEBIDO: " + sentence );
          
          InetAddress enderecoIp = pacoteReceber.getAddress();
          int porta = pacoteReceber.getPort();
          
          String dadosResposta = sentence.toUpperCase();
          dadosEnviados = new byte[1024];
          dadosEnviados = dadosResposta.getBytes();
          
          numSortado = sorteador.nextFloat();
          System.out.println(numSortado);
          
//          Simulando um atraso + perda de pacote
          if ( numSortado < TAXA_PERDA )
          {
        	  Thread.sleep( ATRASO_MEDIO );
        	  continue;
          }
          
          DatagramPacket pacoteEnviar = new DatagramPacket( dadosEnviados, dadosEnviados.length, enderecoIp, porta );
          socketServidor.send(pacoteEnviar);
          System.err.println("RESPOSTA ENVIADA");
       }
  }
}