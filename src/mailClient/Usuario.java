package mailClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;

public class Usuario 
{
	private String login;
	private String senha;
	
	
	public Usuario( String login, String senha ) throws Exception
	{
		this.login = login;
		this.senha = senha;
		
	}
	
    public void enviarEmail( ConexaoTCP conexao, Email mensagem ) throws Exception
    {
    	final BufferedReader bufferLeitura = new BufferedReader(new InputStreamReader(conexao.getSocket().getInputStream()));
         ( new Thread(new Runnable()
         {
              public void run()
              {
                   try
                   {
                        String linhaMsg;
                        while((linhaMsg = bufferLeitura.readLine()) != null)
                        {
                        	escreverLog("SERVIDOR: "+ linhaMsg + "\r\n", 0 );
                        	
                        	/*if ( linhaMsg.contains("220") )
                        	{
                        		escreverLog( "Erro de Autenticação!", 0);
                        		break;
                        	}*/
                        }
                   }
                   catch (IOException e)
                   {
                        e.printStackTrace();
                   }
              }
         })).start();
         
         
         int time = conexao.getTime();
         ClienteImeiu.txtLog.setText("Tentando Enviar seu e-mail\n\n");
         String s = "HELO "+ conexao.getServicoEmail().getUrl() +"\r\n";
	         escreverLog(s, time );
	         conexao.write(s);
         Thread.sleep(time);
         s = "AUTH LOGIN\r\n";
         	escreverLog(s, time );
         	conexao.write(s);
         Thread.sleep(time);
         s = this.loginCodificado() + "\r\n";
	         escreverLog( s, time );
	         conexao.write(s);
         Thread.sleep(time);
         s = this.senhaCodificada() + "\r\n";
	         escreverLog(s , time );
	         conexao.write(s);
         Thread.sleep(time);
         s = "MAIL FROM: <" + this.getLogin() + ">\r\n";
	         escreverLog( s, time );
	         conexao.write(s);
         Thread.sleep(time);
         s = "RCPT TO: <" +  mensagem.getEndDestinatario() + ">\r\n";
	         escreverLog( s, time );
	         conexao.write(s);
         Thread.sleep(time);
         s = "DATA\r\n";
	         escreverLog(s, time );
	         conexao.write(s);
         Thread.sleep(time);
         s = "SUBJECT: "+ mensagem.getAssunto() +"\r\n";
	         escreverLog( s, time );
	         conexao.write(s);
         Thread.sleep(time);
         s = mensagem.getCorpoMsg().toString() + "\r\n";
	         escreverLog( s, time );
	         conexao.write(s);
         Thread.sleep(time);
         s = ".\r\n";
	         escreverLog(s, time );
	         conexao.write(s);
         Thread.sleep(time);
         s = "QUIT\r\n";
         escreverLog( s, time );
         
    }
	
	public String loginCodificado() throws Exception
	{
		byte[] userByte = login.getBytes("UTF-8");
		String username = Base64.getEncoder().encodeToString(userByte);
		return username;
	}
	
	public String senhaCodificada() throws Exception
	{
		byte[] passByte = senha.getBytes("UTF-8");
        String password = Base64.getEncoder().encodeToString(passByte);
        return password;
	}

	public void escreverLog( String s, int time )
	{
		ClienteImeiu.out.print(s);
		ClienteImeiu.txtLog.setText( ClienteImeiu.txtLog.getText() + s );
	}
	
	public String getLogin() 
	{
		return login;
	}

	public void setLogin(String login) 
	{
		this.login = login;
	}

	public void setSenha(String senha) 
	{
		this.senha = senha;
	}
	
	
}
