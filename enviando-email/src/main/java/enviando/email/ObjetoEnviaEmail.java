package enviando.email;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ObjetoEnviaEmail {

	private String userName = "kedertokazutosupremous@gmail.com";
	private String senha = "poderio13";
	private String listaDestinatarios="";
	private String nomeRemetente="";
	private String assuntoEmail="";
	private String textoEmail="";
	
	public ObjetoEnviaEmail(String listaDestinatarios, String nomeRemetente, String assuntoEmail, String textoEmail) {
		this.listaDestinatarios=listaDestinatarios;
		this.nomeRemetente=nomeRemetente;
		this.assuntoEmail=assuntoEmail;
		this.textoEmail=textoEmail;
	}
	
	public void enviarEmail(boolean envioHtml) throws Exception {

		// olhar as configurações smtp do email especifico a ser utilizado, aqui sera
		// estudado para gmail
		Properties properties = new Properties();
		properties.put("mail.smtp.ssl.trust", "*");// autenticacao
		properties.put("mail.smtp.auth", "true");// autorização
		properties.put("mail.smtp.starttls", "true");// autenticação
		properties.put("mail.smtp.host", "smtp.gmail.com");// servidor gmail google
		properties.put("mail.smtp.port", "465");// porta do servidor
		properties.put("mail.smtp.socketFactory.port", "465");// especifica a porta a ser conectada pelo socket
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");// classe socket de conexão ao
																							// SMTP

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, senha);
			}
		});

		Address[] toUser = InternetAddress
				.parse(listaDestinatarios);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente));// quem esta enviando
		message.setRecipients(Message.RecipientType.TO, toUser);// email de destino
		message.setSubject(assuntoEmail);// asunto do email
		if(envioHtml) {
			message.setContent(textoEmail, "text/html; charset=utf-8");
		}else {
			message.setText(textoEmail);// corpo do email
		}

		Transport.send(message);

	}

}
