package mailClient;

public class Email 
{
	private String endDestinatario;
	private String assunto;
	private StringBuilder corpoMsg;
	
	public Email( String destinatario, String assunto, String cargaUtil )
	{
		this.endDestinatario = destinatario;
		this.assunto = assunto;
		this.corpoMsg = new StringBuilder( cargaUtil );
	}

	public String getEndDestinatario() 
	{
		return endDestinatario;
	}

	public void setEndDestinatario(String endDestinatario) 
	{
		this.endDestinatario = endDestinatario;
	}

	public String getAssunto() 
	{
		return assunto;
	}

	public void setAssunto(String assunto) 
	{
		this.assunto = assunto;
	}

	public StringBuilder getCorpoMsg() 
	{
		return corpoMsg;
	}

	public void setCorpoMsg(StringBuilder corpoMsg) 
	{
		this.corpoMsg = corpoMsg;
	}
	
	
}
