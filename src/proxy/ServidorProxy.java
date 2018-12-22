package proxy;

import java.net.*;

public class ServidorProxy 
{
	public static final boolean threadUnica = false;

	public static void main(String[] args) throws Exception 
	{
		int porta = 8080;
		boolean escutando = true;
		ServerSocket s = new ServerSocket(porta);
		
		System.out.println("Escutando na porta " + porta );
		while ( escutando ) 
		{
			Socket channel = s.accept();
			ClienteHandler handler = new ClienteHandler(channel);

			if ( threadUnica ) 
			{
				handler.run();
			} else {
				Thread t = new Thread(handler);
				t.start();
			}
		}
		System.err.println("Finalizado");
	}
}