package com.oocl.ita.gallery.security

import com.oocl.ita.gallery.common.constants.ErrorMsgConstants
import com.oocl.ita.gallery.security.utils.JWT
import com.oocl.ita.gallery.user.User
import com.oocl.ita.gallery.user.UserService
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.AuthenticationInfo
import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.authc.SimpleAuthenticationInfo
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.StringUtils

class SmartRealm extends AuthorizingRealm{
    @Autowired JWT jwt
    @Autowired UserService userService

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String jwtToken = (String) authenticationToken?.getCredentials()
        String username = jwt?.getUsername(jwtToken)

        if (StringUtils.isEmpty(username)) {
            throw new AuthenticationException(ErrorMsgConstants.AUTH_TOKEN_UNRECOGNIZED)
        }

        User user = userService.findByUsername(username)

        if (user == null) {
            throw new AuthenticationException(ErrorMsgConstants.AUTH_NOT_FOUND_USER)
        }

        if (! jwt.verify(jwtToken, ['id': user?.id, 'username': user?.username, 'hsKey': user?.hsKey, 'hsPassword': user?.hsPassword])) {
            throw new AuthenticationException(ErrorMsgConstants.AUTH_FAIL)
        }

        return new SimpleAuthenticationInfo(jwtToken, jwtToken, this.getName())
    }

    @Override
    boolean supports(AuthenticationToken token) {
        return token instanceof Token
    }

    @Override
    String getName() {
        return 'SMART-REALM';
    }
}
