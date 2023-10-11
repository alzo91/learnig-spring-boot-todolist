package br.com.zotasys.todolist.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private IUserRepository userRepository;

  @GetMapping("/")
  public String getTodoList(){
    return "Testing ... message";
  }

  @PostMapping("/")
  public ResponseEntity create(@RequestBody UserModel userModel){
    var user = this.userRepository.findByUsername(userModel.getUsername());

    if(user != null){
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists!");
    }

    var savedUser = this.userRepository.save(userModel);

    return ResponseEntity.status(HttpStatus.CREATED.value()).body(savedUser);
  }
}
