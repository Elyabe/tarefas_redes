package mailClient;

enum enumServidorSMTP 
{
	GMAIL, OUTLOOK
}

public class ServicoSMTP
{
	private String url;
	private int porta;
	
	public ServicoSMTP( enumServidorSMTP servico, int porta )
	{
		this.url = ServicoSMTP.getDescricaoURL( servico );
		this.porta = porta;
	}
	
	public static String getDescricaoURL( enumServidorSMTP nomeServico )
	{
		String url;
		switch( nomeServico )
		{
			case GMAIL:
				url = "smtp.gmail.com";
				break;
			case OUTLOOK:
				url = "smtp-mail.outlook.com";
				break;
			default:
				url = "undefined.mail.com";
		}
		return url;
	}

	public String getUrl() 
	{
		return url;
	}

	public void setUrl(String url) 
	{
		this.url = url;
	}

	public int getPorta() 
	{
		return porta;
	}

	public void setPorta(int porta) 
	{
		this.porta = porta;
	}
	
	
}