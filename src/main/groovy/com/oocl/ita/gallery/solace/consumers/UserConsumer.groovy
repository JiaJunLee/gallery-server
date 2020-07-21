package com.oocl.ita.gallery.solace.consumers

import com.fasterxml.jackson.databind.ObjectMapper
import com.oocl.ita.gallery.user.User
import com.oocl.ita.gallery.user.UserService
import com.oocl.ita.gallery.user_register_validate.UserRegisterValidate
import com.oocl.ita.gallery.user_register_validate.UserRegisterValidateService
import freemarker.template.Template
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.jms.annotation.JmsListener
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHeaders
import org.springframework.stereotype.Component
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer

import javax.mail.internet.MimeMessage

@Component
class UserConsumer {

    private static final Logger logger = LoggerFactory.getLogger(UserConsumer.class)

    @Autowired
    JavaMailSender javaMailSender

    @Autowired
    private FreeMarkerConfigurer configurer

    @Value('${spring.mail.username}')
    private String emailFrom
    
    @Value('${email.user.register.subject}')
    private String userRegisterEmailSubject

    @Value('${email.user.register.template}')
    private String userRegisterEmailTemplate

    @Value('${website.baseurl}')
    private String websiteBaseURL

    @Autowired
    private UserRegisterValidateService userRegisterValidateService

    @Autowired
    private UserService userService

    @JmsListener(destination = '${solace.jms.queues.user.register}')
    void processMsg(Message msg) {
        StringBuffer msgAsStr = new StringBuffer("============= Received \nHeaders:")
        MessageHeaders hdrs = msg.getHeaders()
        msgAsStr.append("\nUUID: "+hdrs.getId())
        msgAsStr.append("\nTimestamp: "+hdrs.getTimestamp())
        Iterator<String> keyIter = hdrs.keySet().iterator()
        while (keyIter.hasNext()) {
            String key = keyIter.next()
            msgAsStr.append("\n"+key+": "+hdrs.get(key))
        }
        msgAsStr.append("\nPayload: "+msg.getPayload())
        logger.info(msgAsStr.toString())

        // send email to user
        Map<String, String> msgMap = new ObjectMapper().readValue(msg.getPayload().toString(), Map.class)
        User user = userService.findByUsername(msgMap?.username)
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage()
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true)
        mimeMessageHelper.setFrom(emailFrom)
        mimeMessageHelper.setTo(user?.email)
        mimeMessageHelper.setSubject(userRegisterEmailSubject)

        UserRegisterValidate userRegisterValidate = new UserRegisterValidate(
                registerUser: user,
                expirationTime: System.currentTimeMillis() + 1000 * 60 * 15
        )
        userRegisterValidateService.save(userRegisterValidate)

        Map<String, Object> params = [
                username: user?.username,
                confirmURL: "${websiteBaseURL}/register-confirm/${userRegisterValidate.id}/${user.id}"
        ]
        Template template = configurer.getConfiguration().getTemplate(userRegisterEmailTemplate)
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, params)
        mimeMessageHelper.setText(text, true)

        javaMailSender.send(mimeMailMessage)
    }

}
