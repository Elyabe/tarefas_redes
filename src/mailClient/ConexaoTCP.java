package mailClient;

import java.io.*;
import javax.net.ssl.*;

public class ConexaoTCP  
{
     private DataOutputStream dos;
     private int time;
     private SSLSocket socket;
     private ServicoSMTP servicoEmail;
   
    public ConexaoTCP( ServicoSMTP servico ) throws Exception 
    {
    	 this.time  = 1000;
    	 this.socket = (SSLSocket)((SSLSocketFactory)SSLSocketFactory.getDefault())
    			 			.createSocket( servico.getUrl(), servico.getPorta() );
    	 this.dos = new DataOutputStream(this.socket.getOutputStream());
    	 this.servicoEmail = servico;
    }
     

    public void write(String comando) throws Exception
    {
          this.dos.writeBytes(comando);
          System.out.println("CLIENT: "+ comando );
    }

	public DataOutputStream getDos() 
	{
		return dos;
	}

	public void setDos(DataOutputStream dos) 
	{
		this.dos = dos;
	}

	public int getTime() 
	{
		return time;
	}

	public void setTime(int time) 
	{
		this.time = time;
	}

	public SSLSocket getSocket() 
	{
		return socket;
	}

	public void setSocket(SSLSocket socket) 
	{
		this.socket = socket;
	}


	public ServicoSMTP getServicoEmail() 
	{
		return servicoEmail;
	}


	public void setServicoEmail(ServicoSMTP servicoEmail) 
	{
		this.servicoEmail = servicoEmail;
	}
     
     
}