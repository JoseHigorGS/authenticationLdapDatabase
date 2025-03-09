package com.example.app.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationContext {

    private DatabaseAuthenticationStrategy databaseAuthenticationStrategy;
    private LdapAuthenticationStrategy ldapAuthenticationStrategy;

    public AuthorizationContext(DatabaseAuthenticationStrategy databaseAuthenticationStrategy, LdapAuthenticationStrategy ldapAuthenticationStrategy) {
        this.databaseAuthenticationStrategy = databaseAuthenticationStrategy;
        this.ldapAuthenticationStrategy = ldapAuthenticationStrategy;
    }

    public Authentication decideAuthentication(String username, String password){

       if(username.matches("[0-9]+")){
           System.out.println("DATABASE");
           return databaseAuthenticationStrategy.authenticate(username,password);
       }
        System.out.println("LDAP");
        return ldapAuthenticationStrategy.authenticate(username,password);
    }
}
