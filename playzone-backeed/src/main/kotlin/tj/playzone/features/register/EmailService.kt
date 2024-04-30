package tj.playzone.features.register
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class EmailService(private val smtpServer: String, private val smtpPort: Int, private val username: String, private val password: String) {

    fun sendOtpEmail(email: String, otpCode: String) {
        val properties = System.getProperties()
        properties["mail.smtp.host"] = smtpServer
        properties["mail.smtp.port"] = smtpPort
        properties["mail.smtp.auth"] = "true"
        properties["mail.smtp.starttls.enable"] = "true"

        val session = Session.getDefaultInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(username, password)
            }
        })

        try {
            val message = MimeMessage(session)
            message.setFrom(InternetAddress(username))
            message.addRecipient(Message.RecipientType.TO, InternetAddress(email))
            message.subject = "Your OTP Code"
            message.setText("Your OTP code is: $otpCode")
            Transport.send(message)
        } catch (e: MessagingException) {
            e.printStackTrace()
        }
    }


    fun sendCheckPasswordForReset(email: String, checkCode: String) {
        val properties = System.getProperties()
        properties["mail.smtp.host"] = smtpServer
        properties["mail.smtp.port"] = smtpPort
        properties["mail.smtp.auth"] = "true"
        properties["mail.smtp.starttls.enable"] = "true"

        val session = Session.getDefaultInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(username, password)
            }
        })

        try {
            val message = MimeMessage(session)
            message.setFrom(InternetAddress(username))
            message.addRecipient(Message.RecipientType.TO, InternetAddress(email))
            message.subject = "Your check code"
            message.setText("Your check code is: $checkCode")
            Transport.send(message)
        } catch (e: MessagingException) {
            e.printStackTrace()
        }
    }
}
