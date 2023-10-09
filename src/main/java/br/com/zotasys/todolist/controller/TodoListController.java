package br.com.zotasys.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/general")
public class TodoListController {
  /*
   * GET - Getting information
   * POST - Add data or information
   * PUT - Update data / information
   * DELETE - Delete data
   * PATCH - Update just a propriety
   */
  @GetMapping("/")
  public String getTodoList(){
    return "Message from get todoList";
  }
}
