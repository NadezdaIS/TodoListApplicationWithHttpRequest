package controllers;

import entity.Priority;
import entity.Todo;
import entity.TodoStatus;
import servicies.TodoService;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

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

        //String[] priorityList = EnumUtils.getEnumList(Priority.class).toArray();
        //String[] priorityList = Arrays.stream(Priority.values()).map(Priority::name).toArray()

        String userSelection = this.getUserInputFromDropDown(
                Arrays.stream(Priority.values()).map(Priority::name).toArray(),
                "Choose priority",
                "How important is this item?"
        ).toString();
        todo.setPriority(Priority.valueOf(userSelection));

        String todoStatus = this.getUserInputFromDropDown(
                Arrays.stream(TodoStatus.values()).map(TodoStatus::name).toArray(),
                "Choose Status",
                "What is the status of this item? "
        ).toString();
        todo.setStatus(TodoStatus.valueOf(todoStatus));
        todo.setDueDate(this.getUserInput("What date is this task due? (2022-04-23)"));
        return todo;
    }

    private String getUserInput(String info) {
        return JOptionPane.showInputDialog(info);
    }

    private Object getUserInputFromDropDown(Object[] dropdownOptions, String title, String message) {
        return JOptionPane.showInputDialog(
                null,
                message,
                title,
                JOptionPane.QUESTION_MESSAGE,
                null,
                dropdownOptions,
                dropdownOptions[0]
        );
    }

    public void viewAllTodo() {
        try {
            StringBuilder todoListAsString = new StringBuilder();

            for (Todo todo: this.todoService.getAllTodoItems()){
                todoListAsString.append(todo.toString());
            }

            this.displayMessage(todoListAsString.toString());
        }catch (Exception exception){
            this.displayMessage(exception.getMessage());
        }
    }

    public void viewTodo() {
        try {
            List<Todo> existingTodoItems = this.todoService.getAllTodoItems();

            Todo selectedTodo = (Todo) this.getUserInputFromDropDown(
                    existingTodoItems.toArray(),
                    "View Item",
                    "choose the item to view"
            );

            Todo todo = this.todoService.getTodoItem(selectedTodo.get_id());
            this.displayMessage(
                    new StringBuilder()
                            .append("Description: \t").append(todo.getDescription()).append("\n")
                            .append("Owner: \t").append(todo.getOwner()).append("\n")
                            .append("Due Date: \t").append(todo.getDueDate()).append("\n")
                            .append("Status: \t").append(todo.getStatus()).append("\n")
                            .append("Priority: \t").append(todo.getPriority()).append("\n")
                            .toString()
            );
        } catch (Exception exception){
            this.displayMessage(exception.getMessage());
        }
    }

    public void removeTodo() {
        try {
            String todoId = this.getUserInput("Enter the id of todo item to remove");
            this.todoService.deleteTodoItem(todoId);
            this.displayMessage("Todo item deleted successfully");
        } catch (Exception exception){
            this.displayMessage(exception.getMessage());
        }
    }

    public void updateTodo() {
        try {
            String todoId = this.getUserInput("Enter the id of todo item to update");
            Todo todo = this.collectTodoInfo();
            this.todoService.updateTodoItem(todo, todoId);
            this.displayMessage("Todo updated successful");
        } catch (Exception exception){
            this.displayMessage(exception.getMessage());
        }
    }
}
