package com.example.app.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationContext {

    private DatabaseAuthenticationStrategy databaseAuthenticationStrategy;
    private LdapAuthenticationStrategy ldapAuthenticationStrategy;

    @Autowired
    public AuthorizationContext(DatabaseAuthenticationStrategy databaseAuthenticationStrategy, LdapAuthenticationStrategy ldapAuthenticationStrategy) {
        this.databaseAuthenticationStrategy = databaseAuthenticationStrategy;
        this.ldapAuthenticationStrategy = ldapAuthenticationStrategy;
    }

    public Authentication decideAuthentication(String username, String password, AuthenticationSource source){

       if(source == AuthenticationSource.DATABASE){
           return databaseAuthenticationStrategy.authenticate(username,password);
       }
       if(source == AuthenticationSource.LDAP){
            return ldapAuthenticationStrategy.authenticate(username,password);
       }
       return null;
    }
}
