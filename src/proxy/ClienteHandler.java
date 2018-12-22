package proxy;

import java.net.*;
import java.io.*;

public class ClienteHandler implements Runnable 
{

	Socket clientSocket;

	public ClienteHandler(Socket clientSocket) 
	{
        this.clientSocket = clientSocket;
    }

    @Override
	public void run() 
    {
		try 
		{
			String processedRequestString = desmontarRequisicao(clientSocket);
			if(processedRequestString.equals("")) 
				return;

			String returnData = enviarParaHost(clientSocket, processedRequestString);
		}
		catch (IOException ioe) 
		{
			ioe.printStackTrace(System.err);
		}
	}

	String desmontarRequisicao(Socket clientSocket) throws IOException 
	{
		System.out.println("Desmontando a requisicao!");
		InputStream in = clientSocket.getInputStream();
		DataInputStream din = new DataInputStream(in);

		String line = din.readLine();
		String firstLine = line;

		String processedLine = "";
		int content_length = 0;

		while (line!=null && line.length()>0) 
		{

			String helpStringArray[] = line.toLowerCase().split(":");
			if ( !(helpStringArray[0].equals("user-agent") || helpStringArray[0].equals("proxy-connection") || helpStringArray[0].equals("referer")) ) 
			{
				processedLine = processedLine + line + "\r\n";
			}

			if (line.toLowerCase().startsWith("content-length")) 
			{
				int colon = line.indexOf(":");
				String opnd = line.substring(colon+1);
				content_length = Integer.valueOf(opnd.trim());
			}

			line = din.readLine();
		}

		String postData = null;
		if ( firstLine.startsWith("POST") ) 
		{
			postData = read(din, content_length);
			processedLine = processedLine + "\r\n" + postData;
		}

		return processedLine;
	}

	public static String read(DataInputStream in, int n) throws IOException 
	{
		StringBuilder buf = new StringBuilder();
		int c = in.read();

		int i = 1;
		while (c!=-1 && i<n) 
		{
			buf.append((char)c);
			c = in.read();
			i++;
		}
		return buf.toString();
	}
	
	String enviarParaHost(Socket clientSocket, String processedRequestString) throws IOException 
	{
		System.err.println("Enviando de volta");
		String lines[] = processedRequestString.split("\n");
		String firstLine = lines[0];

		String elements[] = firstLine.split(" ");
		String elements2[] = elements[1].split("//");
		String elements3[] = elements2[1].split("/");

		String hostUrl = elements3[0];

		Socket enviarParaHostSocket = new Socket(hostUrl, 80);
		OutputStream enviarParaHostOut = enviarParaHostSocket.getOutputStream();
		PrintStream enviarParaHostPOut = new PrintStream(enviarParaHostOut);
		
		processedRequestString = processedRequestString.replace("HTTP/1.1","HTTP/1.0");
		processedRequestString = processedRequestString.replace("http://" + hostUrl ,"");
		
		enviarParaHostPOut.println(processedRequestString);

		InputStream enviarParaHostIn = enviarParaHostSocket.getInputStream();
		DataInputStream enviarParaHostDIn = new DataInputStream(enviarParaHostIn);

		byte readByte[] = new byte[8];

		String returnData = "";

		OutputStream toClient = clientSocket.getOutputStream();
		BufferedOutputStream bf = new BufferedOutputStream(toClient);
		DataOutputStream streamCliente = new DataOutputStream(bf);
		

		while (enviarParaHostDIn.read(readByte)!=-1) 
		{
			streamCliente.write(readByte);
		}

//		Fechando conexoes s
		enviarParaHostPOut.close();
		enviarParaHostDIn.close();

		streamCliente.close();

		enviarParaHostSocket.close();
		clientSocket.close();

		return returnData;
	}

}