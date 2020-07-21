package com.oocl.ita.gallery.user_register_validate

import com.fasterxml.jackson.databind.ObjectMapper
import com.oocl.ita.gallery.api_versions.ApiVersion
import com.oocl.ita.gallery.api_versions.ApiVersions
import com.oocl.ita.gallery.security.AuthenticationIgnore
import com.oocl.ita.gallery.user.User
import com.oocl.ita.gallery.user.UserService
import freemarker.template.Template
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.jms.core.JmsTemplate
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Controller
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer

import javax.mail.internet.MimeMessage;

@Controller
@RequestMapping('/user-register-validate')
class UserRegisterValidateController {

    @Autowired
    UserRegisterValidateService userRegisterValidateService

    @Autowired
    UserService userService

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
    private JmsTemplate jmsTemplate

    @Value('${solace.jms.queues.user.register}')
    private String userRegisterQueueName

    @PostMapping
    @AuthenticationIgnore
    @ApiVersion(ApiVersions.VERSION_1)
    ResponseEntity<User> validate(@RequestBody UserRegisterValidate userRegisterValidate) {
        userRegisterValidate = userRegisterValidateService.findById(userRegisterValidate?.id)
        User registerUser = userRegisterValidate.getRegisterUser()
        User user = new User(
                id: registerUser.id,
                username: registerUser.username
        )
        if (registerUser.isActive()) {
            return new ResponseEntity<User>(user, HttpStatus.MULTI_STATUS)
        }
        if (userRegisterValidate.expirationTime > System.currentTimeMillis()) {
            registerUser.setActive(true)
            userService.save(registerUser)
            return new ResponseEntity<User>(user, HttpStatus.OK)
        }
        return new ResponseEntity<User>(null, HttpStatus.BAD_REQUEST)
    }

    @PutMapping
    @AuthenticationIgnore
    @ApiVersion(ApiVersions.VERSION_1)
    ResponseEntity resendEmail(@RequestBody UserRegisterValidate userRegisterValidate) {
        userRegisterValidate = userRegisterValidateService.findById(userRegisterValidate.id)
        this.jmsTemplate.convertAndSend(userRegisterQueueName, new ObjectMapper().writeValueAsString([
                username: userRegisterValidate.getRegisterUser().username,
                email: userRegisterValidate.getRegisterUser().email,
                nickname: userRegisterValidate.getRegisterUser().nickname,
                gender: userRegisterValidate.getRegisterUser().gender
        ]))
        return new ResponseEntity(HttpStatus.OK)
    }

}
