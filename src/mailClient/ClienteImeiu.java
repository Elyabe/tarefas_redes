package mailClient;

import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ClienteImeiu extends JFrame 
{
	private static String REMETENTE = "elly.ufes@gmail.com";
	private static String DESTINATARIO = "elyabe@outlook.com" ;
	private static String SENHA = "";
	private static String ASSUNTO = "Teste";
	private static String MENSAGEM = "Entendeu? Sim ou Não? Implemente!";
	
	
    private JPanel contentPane;  
    private JTextField txtLogin;  
	private JPasswordField txtSenha;  
	private JButton btnEnviar;  
	private JButton btnLogar;  
	private JTextField txtDestinatario;  
	private JTextField txtAssunto;
	private JTextArea txtMsg;
	public static JTextArea txtLog;
	public static StringWriter sw = new StringWriter();
	public static PrintWriter out = new PrintWriter(sw);
    	
	public static void main(String[] args) 
	{
		ClienteImeiu janela = new ClienteImeiu();
		janela.montarTela();
		janela.setSize(900, 600);
		janela.setVisible(true);
	}

	public void montarTela() 
    {  
       setTitle("Cliente E-mail");  
       setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);  
       setBounds(100, 100, 450, 300);  
       contentPane = new JPanel();  
       contentPane.setBackground(Color.LIGHT_GRAY);  
       contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));  
       setContentPane(contentPane);  
       contentPane.setLayout(null);  
   
       JLabel lblNome = new JLabel("E-mail:");  
       lblNome.setFont(new Font("Arial Black", Font.BOLD, 12));  
       lblNome.setForeground(SystemColor.desktop);  
       lblNome.setBounds(95, 127, 70, 15);  
       contentPane.add(lblNome);  

       txtLogin = new JTextField("elly.ufes@gmail.com");  
       txtLogin.setBounds(183, 125, 200, 19);  
       contentPane.add(txtLogin);  
       txtLogin.setColumns(10);  

       JLabel lblSenha = new JLabel("Senha:");  
       lblSenha.setFont(new Font("Arial Black", Font.PLAIN, 12));  
       lblSenha.setForeground(new Color(165, 42, 42));  
       lblSenha.setBounds(95, 153, 70, 15);  
       contentPane.add(lblSenha);  

       txtSenha = new JPasswordField();  
       txtSenha.setBounds(183, 151, 200, 19);  
       contentPane.add(txtSenha);  
       txtSenha.setColumns(10);
       
       JLabel lblDestinatario = new JLabel("Destinatario:");  
       lblDestinatario.setFont(new Font("Arial Black", Font.PLAIN, 12));  
       lblDestinatario.setForeground(new Color(165, 42, 42));  
       lblDestinatario.setBounds(95, 180, 70, 15);  
       contentPane.add(lblDestinatario);
       
       txtDestinatario = new JTextField();  
       txtDestinatario.setBounds(183, 180, 200, 19);  
       contentPane.add(txtDestinatario);  
       txtDestinatario.setColumns(10);
       
       JLabel lblAssunto = new JLabel("Assunto:");  
       lblAssunto.setFont(new Font("Arial Black", Font.PLAIN, 12));  
       lblAssunto.setForeground(new Color(165, 42, 42));  
       lblAssunto.setBounds(95, 220, 70, 15);  
       contentPane.add(lblAssunto);
       
       txtAssunto = new JTextField();  
       txtAssunto.setBounds(183, 220, 200, 19);  
       contentPane.add(txtAssunto);  
       txtAssunto.setColumns(10);
       
       txtMsg = new JTextArea("Aqui vai o texto.");
       txtMsg.setBounds(183, 250, 200, 100);  
       contentPane.add(txtMsg);  
       txtMsg.setColumns(10);
       
       txtLog = new JTextArea("");
       txtLog.setBounds(400, 127, 400, 400);
       txtLog.setEditable(false);
       contentPane.add(txtLog);  
       txtLog.setColumns(10);
       
       btnEnviar = new JButton("Enviar");
       btnEnviar.setBounds(183, 370, 200, 30);
       contentPane.add(btnEnviar);
       
       btnEnviar.addActionListener( new java.awt.event.ActionListener() 
       {
    	   public void actionPerformed( java.awt.event.ActionEvent evt )
    	   {
    		 try 
    		 {
				Usuario remetente = new Usuario( txtLogin.getText() , txtSenha.getText() );
				ServicoSMTP clienteGmail = new ServicoSMTP( enumServidorSMTP.GMAIL , 465 );
   				ConexaoTCP conexao = new ConexaoTCP( clienteGmail );
   				Email mensagem = new Email( txtDestinatario.getText(), txtAssunto.getText() , txtMsg.getText() );
   				
   				/*Usuario remetente = new Usuario( REMETENTE , SENHA );
				ServicoSMTP clienteGmail = new ServicoSMTP( enumServidorSMTP.GMAIL , 465 );
   				ConexaoTCP conexao = new ConexaoTCP( clienteGmail );
   				Email mensagem = new Email( DESTINATARIO, ASSUNTO , MENSAGEM );*/
   				
  				remetente.enviarEmail( conexao, mensagem );
    		 } catch (Exception e) 
    		 {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	   }
       });
    }  

}
