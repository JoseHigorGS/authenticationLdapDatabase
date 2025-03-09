package com.example.app.security.authentication;

import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

//import javax.naming.Context;
//import javax.naming.NamingEnumeration;
//import javax.naming.NamingException;
//import javax.naming.directory.DirContext;
//import javax.naming.directory.InitialDirContext;
//import javax.naming.directory.SearchControls;
//import javax.naming.directory.SearchResult;
//import javax.naming.ldap.InitialLdapContext;
//import java.util.Hashtable;

@Component
public class LdapAuthenticationStrategy implements AuthenticateStrategyInterface {
    private static final String INITIAL_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";

    private AuthenticationManager authenticationManager;


    public LdapAuthenticationStrategy(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

//    @Override
//    public Authentication authenticate(String username, String password) {
//        Hashtable<String, String> env = new Hashtable<>();
//        env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
//        env.put(Context.PROVIDER_URL, "ldap://localhost:389");
//        env.put(Context.SECURITY_AUTHENTICATION, "simple");
//        env.put(Context.SECURITY_PRINCIPAL, "cn="+ username+",ou=admin,ou=users,dc=josehigor,dc=me");
//        env.put(Context.SECURITY_CREDENTIALS, password);
//
//        try {
//            DirContext dirContext = new InitialDirContext(env);
//            System.out.println("DEBUG: Conexão bem sucedida");
//
//            String base = "ou=admin,ou=users,dc=josehigor,dc=me";
//            String filtro = "cn=" + username;
//            SearchControls searchControls = new SearchControls();
//            searchControls.setSearchScope(searchControls.SUBTREE_SCOPE);
//
//            NamingEnumeration<SearchResult> results = dirContext.search(base, filtro, searchControls);
//            SearchResult searchResult = results.next();
//            dirContext.close();
//            System.out.println(searchResult);
//            return new UsernamePasswordAuthenticationToken(username,password);
//        }catch (NamingException ex){
//            return null;
//        }
//    }
//    @Bean
//    public AuthenticationManager authManager(BaseLdapPathContextSource source) {
//        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(source);
//        factory.setUserDnPatterns("cn={0}");
//        return factory.createAuthenticationManager();
//    }


//    @Bean
//    public LdapTemplate ldapTemplate() {
//        return new LdapTemplate(contextSource());
//    }

    //@Bean
    public LdapContextSource contextSource() {
        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrl("ldap://localhost:389");
        ldapContextSource.setUserDn("cn=root,ou=admin,ou=users,dc=josehigor,dc=me");
        ldapContextSource.setPassword("12345");
        return ldapContextSource;
    }

    //@Bean
    AuthenticationManager authenticationManager(BaseLdapPathContextSource source) {
        System.out.println(source.toString());
        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(source);
        factory.setUserDnPatterns("cn={0},ou=admin,ou=users,dc=josehigor,dc=me");


        return factory.createAuthenticationManager();
    }


    @Override
    public Authentication authenticate(String username, String password) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(username, password);
        var auth = authenticationManager(contextSource()).authenticate(usernamePassword);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return auth;
//        var usernamePassword = new UsernamePasswordAuthenticationToken(username, password);
//        var auth = this.authenticationManager.authenticate(usernamePassword);
//        return auth;
    }

}

