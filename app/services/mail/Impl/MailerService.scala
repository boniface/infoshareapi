package services.mail.Impl

import domain.util.EmailMessage
import org.apache.commons.mail.{DefaultAuthenticator, HtmlEmail}

import scala.util.Try

@Deprecated
object MailerService {

  def send(emailMessage: EmailMessage) = Try({
    val email = new HtmlEmail()
    email.setStartTLSEnabled(true)
    email.setStartTLSRequired(true)
    email.setBounceAddress(emailMessage.recipient)
    email.setSmtpPort(emailMessage.smtpConfig.port)
    email.setHostName(emailMessage.smtpConfig.host)
    email.setAuthenticator(new DefaultAuthenticator(
      emailMessage.smtpConfig.user,
      emailMessage.smtpConfig.password
    ))
    email.setHtmlMsg(emailMessage.html)
      .setTextMsg(emailMessage.text)
      .addTo(emailMessage.recipient)
      .setFrom(emailMessage.from)
      .setSubject(emailMessage.subject)
      .send()
  })

}
