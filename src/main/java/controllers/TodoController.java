package controllers;

import entity.Priority;
import entity.Todo;
import entity.TodoStatus;
import servicies.TodoService;

import javax.swing.*;

public class TodoController {
    private final TodoService todoService = new TodoService();
    public void addTodo() {
        try {
            Todo todo = this.collectTodoInfo();
            this.todoService.createTodo(todo);
            this.displayMessage("Todo created successful");
        } catch (Exception exception){
            this.displayMessage(exception.getMessage());
        }
    }

    private void displayMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private Todo collectTodoInfo() {
        Todo todo = new Todo();
        todo.setDescription(this.getUserInput("What would you like to do"));
        todo.setOwner(this.getUserInput("What owns this item"));
        todo.setPriority(Priority.valueOf(
                this.getUserInput("How important is this item?" + "( LOW, MEDIUM, HIGH, VERY_HIGH )")
        ));
        todo.setStatus(TodoStatus.valueOf(this.getUserInput(
                "What is the status of this item? " +
                        "(PENDING, IN_PROGRESS, POSTPONED, COMPLETED, DELETED)"))
        );
        todo.setDueDate(this.getUserInput("What date is this task due? (2022-04-23)"));
        return todo;
    }

    private String getUserInput(String info) {
        return JOptionPane.showInputDialog(info);
    }

    public void viewAllTodo() {
    }

    public void viewTodo() {
    }

    public void removeTodo() {
    }

    public void updateTodo() {
    }
}
