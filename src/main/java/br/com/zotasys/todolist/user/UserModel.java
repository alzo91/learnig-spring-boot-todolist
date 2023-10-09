package br.com.zotasys.todolist.user;

public class UserModel {
  String name;
  String username;
  String password;

  public String toString(){
    return "name : " + this.name + ", username: " + this.username ;
  }
}
