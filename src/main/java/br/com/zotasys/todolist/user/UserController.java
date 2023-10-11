package br.com.zotasys.todolist.user;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  
  @GetMapping("/")
  public String getTodoList(){
    return "Testing ... message";
  }

  @PostMapping("/")
  public ResponseEntity<UserModel> create(@RequestBody UserModel userModel){
    
    System.out.println(userModel.toString());
    return ResponseEntity.status(HttpStatus.CREATED.value()).body(userModel);
  }
}
