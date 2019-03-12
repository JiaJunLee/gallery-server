package com.oocl.ita.gallery.security

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator
import org.apache.shiro.mgt.DefaultSubjectDAO
import org.apache.shiro.spring.LifecycleBeanPostProcessor
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator

class ShiroConfigurationTest {

    ShiroConfiguration shiroConfiguration

    @Before
    void setUp() {
        shiroConfiguration = new ShiroConfiguration()
    }

    @Test
    void should_return_DefaultWebSecurityManager_when_getManager() {
        //When
        DefaultWebSecurityManager manager = shiroConfiguration.getManager()

        //Then
        Assert.assertNotNull(manager)
        Assert.assertNotNull(manager.subjectDAO)
        Assert.assertNotNull(((DefaultSubjectDAO) manager.subjectDAO).sessionStorageEvaluator)
        Assert.assertFalse(((DefaultSessionStorageEvaluator) ((DefaultSubjectDAO) manager.subjectDAO).sessionStorageEvaluator).sessionStorageEnabled
        )
    }

    @Test
    void should_return_smartRealm_when_get_smartRealm() {
        //When
        SmartRealm realm = shiroConfiguration.smartRealm()

        //Assert
        Assert.assertNotNull(realm)
    }

    @Test
    void should_return_ShiroFilterFactoryBean_when_factory_give_DefaultWebSecurityManager() {
        //Given
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager()

        //When
        ShiroFilterFactoryBean factoryBean = shiroConfiguration.factory(securityManager)

        //Then
        Assert.assertNotNull(factoryBean)
        Assert.assertNotNull(factoryBean.filters)
        Assert.assertNotNull(factoryBean.filterChainDefinitionMap)
        Assert.assertEquals("AuthenticationFilter", factoryBean.filterChainDefinitionMap.get("/**"))
    }

    @Test
    void should_return_defaultAdvisorAutoProxyCreator_when_get_defaultAdvisorAutoProxyCreator() {
        //When
        DefaultAdvisorAutoProxyCreator creator = shiroConfiguration.defaultAdvisorAutoProxyCreator()

        //Then
        Assert.assertNotNull(creator)
        Assert.assertTrue(creator.proxyTargetClass)
    }

    @Test
    void should_return_lifecycleBeanPostProcessor_when_get_lifecycleBeanPostProcessor() {
        //When
        LifecycleBeanPostProcessor processor = shiroConfiguration.lifecycleBeanPostProcessor()

        //Then
        Assert.assertNotNull(processor)
    }

    @Test
    void should_return_authorizationAttributeSourceAdvisor_with_securityManager_when_authorizationAttributeSourceAdvisor_given_securityManager() {
        //Given
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager()

        //When
        AuthorizationAttributeSourceAdvisor advisor = shiroConfiguration.authorizationAttributeSourceAdvisor(manager)

        //Then
        Assert.assertNotNull(advisor)
        Assert.assertNotNull(advisor.securityManager)
    }
}
