package lista.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

@Autowired(required = false)
private HttpServletRequest request;

//@Autowired
//UserService userService;

@Override
public Authentication authenticate(Authentication authentication) 
{
   System.out.println("request testing= " + request.getParameter("codigoCasaOracao"));
   return null;
}

@Override
public boolean supports(Class<?> authentication) {
 return authentication.equals(UsernamePasswordAuthenticationToken.class);
}
}