package enviando.email;

import static org.junit.Assert.assertTrue;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

	private String userName = "kedertokazutosupremous@gmail.com";
	private String senha = "poderio13";

	@Test
	public void testeEmail() throws Exception{
		
		StringBuilder stringBuilderTextoEmail=new StringBuilder();
		stringBuilderTextoEmail.append("Olá, <br/><br/>");
		stringBuilderTextoEmail.append("Enviando email por javamail <br/><br/>");
		stringBuilderTextoEmail.append("Clique no botão pra acessar o youtube <br/><br/>");
		stringBuilderTextoEmail.append("<b>Login:</b> saddsadsa@gmaildasdsa <br/><br/>");
		stringBuilderTextoEmail.append("<b>Senha:</b> dsadsa<br/><br/><br/>");
		stringBuilderTextoEmail.append("<a target=\"_blank\" href=\"https://www.youtube.com/\" style=\"color:#2525a7; padding: 14px 25px; text-align:center; text-decoration: none; display: inline-block; border-radius:30px; font-size:20px; font-family:courier; border:3px solid green;background-color:#99DA39;\">Acessar yt</a><br/><br/>");
		stringBuilderTextoEmail.append("<span style=\"font-size:10px\">Ass.: Luan Gomes</span>");
		ObjetoEnviaEmail enviaEmail=
				new ObjetoEnviaEmail("kederto@gmail.com", 
									"Luan Gomes", 
									"Testando e-mail com java", 
									stringBuilderTextoEmail.toString());
		enviaEmail.enviarEmailAnexo(true);
		
			/*caso o email nao esteja sendo enviado
			 * colocar tempo de espera, porem, so da
			 * pra usar para testes*/
		Thread.sleep(5000);//+5s antes de finalizar o programa, para de fato enviar...

	}
}
