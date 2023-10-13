package br.com.zotasys.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.zotasys.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TaskFilterAuth extends OncePerRequestFilter{

  @Autowired
  private IUserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
      
        String path = request.getRequestURI();
        System.out.println(path);
      
        if (!path.contains("tasks")){
          filterChain.doFilter(request, response);
          return;
        };
      
        var auth = request.getHeader("Authorization");
        System.out.println(auth);

        if(auth == null){
          response.sendError(401);
          return;
        }
      
        var authEncoded = auth.substring("Basic".length()).trim();
        System.out.println(authEncoded);
        byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

        var authString = new String(authDecoded);
        System.out.println(authString);
        String[] credentials = authString.split(":");
        String username = credentials[0];
        String password = credentials[1];
      
        var user = this.userRepository.findByUsername(username);
      
        if (user == null) {
          response.sendError(404);
          return;
        }

        var checkPassword = BCrypt.verifyer().verify(password.toCharArray(),user.getPassword());
        
        System.out.println("checkPassword => " + checkPassword.verified);

        if(!checkPassword.verified){
          response.sendError(401);
          return;
        }

        request.setAttribute("idUser",user.getId());
        filterChain.doFilter(request, response);
  }  
}
