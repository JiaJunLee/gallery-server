package com.oocl.ita.gallery.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.oocl.ita.gallery.common.constants.ErrorMsgConstants
import com.oocl.ita.gallery.security.AuthenticationIgnore
import com.oocl.ita.gallery.security.utils.HMAC
import com.oocl.ita.gallery.security.utils.JWT
import com.solacesystems.jms.SolConnectionFactory
import com.solacesystems.jms.SolJmsUtility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.jms.core.JmsTemplate
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer

import javax.jms.Connection
import javax.jms.Message
import javax.jms.MessageProducer
import javax.jms.Session
import javax.jms.Topic
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping('/user')
class UserController {

    @Autowired
    JWT jwt

    @Autowired
    UserService userService

    @Autowired
    JavaMailSender javaMailSender

    @Autowired
    private FreeMarkerConfigurer configurer

    @Autowired
    private JmsTemplate jmsTemplate

    @Value('${solace.jms.queues.user.register}')
    private String userRegisterQueueName

    @RequestMapping('/denied')
    @AuthenticationIgnore
    ResponseEntity denied() {
        return new ResponseEntity<String>(ErrorMsgConstants.AUTH_DENIED, HttpStatus.UNAUTHORIZED)
    }

    @RequestMapping('/test')
    @AuthenticationIgnore
    ResponseEntity test() {
        SolConnectionFactory connectionFactory = SolJmsUtility.createConnectionFactory()
        connectionFactory.setHost('127.0.0.1')
        connectionFactory.setVPN('default')
        connectionFactory.setUsername('admin')
        connectionFactory.setPassword('admin')

        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic('test_topic')
        Topic topic2 = session.createTopic('endpoint_test')
        session.createDurableSubscriber(topic, 'endpoint_test')

        MessageProducer producer = session.createProducer(topic2)
        Message message = session.createTextMessage('TEST')
        message.setStringProperty("NumberOfOrders", "5")
        producer.send(message)
        producer.send(session.createTextMessage('TEST'))
        producer.send(session.createTextMessage('TEST'))
        producer.send(session.createTextMessage('TEST'))
        producer.send(session.createTextMessage('TEST'))
        producer.send(session.createTextMessage('TEST'))
        return new ResponseEntity('success', HttpStatus.OK)
    }

    @PostMapping("/register")
    @AuthenticationIgnore
    ResponseEntity register(@RequestBody User user) {
        String hsKey = HMAC.generateKey(HMAC.HMAC_SHA512)
        user = new User(
                username: user.username,
                nickname: user.nickname,
                gender: user.gender,
                hsKey: hsKey,
                password: HMAC.digest(user.password, hsKey, HMAC.HMAC_SHA512),
                type: User.UserType.PUBLIC,
                email: user.email
        )
        userService.createUser(user)
        this.jmsTemplate.convertAndSend(userRegisterQueueName, new ObjectMapper().writeValueAsString([
                username: user.username,
                email: user.email,
                nickname: user.nickname,
                gender: user.gender
        ]))
        return new ResponseEntity<User>(user, HttpStatus.CREATED)
    }

    @PostMapping("/login")
    @AuthenticationIgnore
    ResponseEntity login(@RequestBody User user, HttpServletResponse httpServletResponse) {
        User userDB = userService.findByUsername(user.username)
        if (userDB == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND)
        }

        if (!HMAC.validate(user.password, userDB?.hsKey, HMAC.HMAC_SHA512, userDB?.password)) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED)
        }

        if (!userDB.isActive()) {
            return new ResponseEntity(HttpStatus.FORBIDDEN)
        }

        String jwtToken = jwt?.sign(['id': userDB?.id, 'username': userDB?.username, 'hsKey': userDB?.hsKey, 'hsPassword': userDB?.password])

        Cookie cookie = new Cookie('token', jwtToken)
        cookie.setHttpOnly(true)
        cookie.setPath('/')
        cookie.setMaxAge(JWT.TOKEN_EXPIRE_TIME)
        httpServletResponse.addCookie(cookie)

        return new ResponseEntity<Map>([id: userDB.id, username: userDB.username], HttpStatus.OK)
    }
}