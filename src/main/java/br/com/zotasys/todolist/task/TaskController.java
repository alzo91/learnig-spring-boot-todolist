package br.com.zotasys.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zotasys.todolist.Utils.Utils;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private ITaskRepository taskRepository;

  @PostMapping("/")
  public ResponseEntity<String> create(@RequestBody TaskModel taskModel, HttpServletRequest request){
    var idUser  = request.getAttribute("idUser");
    taskModel.setIdUser((UUID)idUser);
    
    var currentDate = LocalDateTime.now();

    if (currentDate.isAfter(taskModel.getStartAt())|| currentDate.isAfter(taskModel.getEndAt())){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data 'inicio' ou 'final' deve ser maior que a atual");
    }

    var task = this.taskRepository.save(taskModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(task.getId().toString());
  }

  @GetMapping("/")
  public List<TaskModel> list(HttpServletRequest request){
    var idUser  = request.getAttribute("idUser");
    var tasks = this.taskRepository.findByIdUser((UUID) idUser);
    return tasks;
  }

  @PutMapping("/{id}")
  public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id){
    var idUser  = (UUID) request.getAttribute("idUser");
    taskModel.setIdUser(idUser);
    taskModel.setId(id);
   
    var task = this.taskRepository.findById(id).orElse(null);
    
    if (task == null){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task doesn't find!");
    }

    System.out.println(idUser.toString() + " ->" + task.getIdUser());
    if (!task.getIdUser().equals(idUser)){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task doesn't belong to you");
    }

    Utils.copyNonNullProperties(taskModel,task);

    var taskUpdated = this.taskRepository.save(task);

    return ResponseEntity.status(HttpStatus.ACCEPTED).body(taskUpdated);
  }

}
