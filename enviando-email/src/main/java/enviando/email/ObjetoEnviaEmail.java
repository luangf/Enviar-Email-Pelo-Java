package enviando.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ObjetoEnviaEmail {

	private String userName = "kedertokazutosupremous@gmail.com";
	private String senha = "poderio13";
	private String listaDestinatarios = "";
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";

	public ObjetoEnviaEmail(String listaDestinatarios, String nomeRemetente, String assuntoEmail, String textoEmail) {
		this.listaDestinatarios = listaDestinatarios;
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
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

		Address[] toUser = InternetAddress.parse(listaDestinatarios);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente));// quem esta enviando
		message.setRecipients(Message.RecipientType.TO, toUser);// email de destino
		message.setSubject(assuntoEmail);// asunto do email
		if (envioHtml) {
			message.setContent(textoEmail, "text/html; charset=utf-8");
		} else {
			message.setText(textoEmail);// corpo do email
		}

		Transport.send(message);

	}

	public void enviarEmailAnexo(boolean envioHtml) throws Exception {

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

		Address[] toUser = InternetAddress.parse(listaDestinatarios);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente));// quem esta enviando
		message.setRecipients(Message.RecipientType.TO, toUser);// email de destino
		message.setSubject(assuntoEmail);// asunto do email

		// parte 1 do email; texto e descricao
		MimeBodyPart corpoEmail = new MimeBodyPart();

		if (envioHtml) {
			corpoEmail.setContent(textoEmail, "text/html; charset=utf-8");
		} else {
			corpoEmail.setText(textoEmail);// corpo do email
		}

		List<FileInputStream> arquivos = new ArrayList<FileInputStream>();
		arquivos.add(simuladorDePDF());
		arquivos.add(simuladorDePDF());
		arquivos.add(simuladorDePDF());
		arquivos.add(simuladorDePDF());

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(corpoEmail);

		int index=0;
		for (FileInputStream fileInputStream : arquivos) {

			// parte 2; anexos
			MimeBodyPart anexoEmail = new MimeBodyPart();

			// aq seria o simulador de pdf
			anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "application/pdf")));
			anexoEmail.setFileName("anexoemail"+index+".pdf");

			multipart.addBodyPart(anexoEmail);
			index++;
		}

		message.setContent(multipart);

		Transport.send(message);

	}

	// simula o pdf ou qualquer arquivo que possa ser enviado por anexo em email
	// pode pegar o arquivo no banco de dados
	// pode estar em um banco de dados ou em uma pasta
	// retorna pdf com o texto do paragrafo de exemplo
	private FileInputStream simuladorDePDF() throws Exception {
		Document document = new Document();
		File file = new File("fileanexo.pdf");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Counteúdo do PDF anexo com Java Mail"));
		document.close();

		return new FileInputStream(file);
	}

}
