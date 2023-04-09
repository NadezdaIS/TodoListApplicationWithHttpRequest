package entity;


public class Todo {
    private String _id;
    private String description;
    String dueDate;
    private TodoStatus status;
    private String owner;

    private Priority priority;

    public Todo() {
    }

    public Todo(String description, String dueDate, TodoStatus status, String owner, Priority priority) {
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.owner = owner;
        this.priority = priority;
    }

    public Todo(String _id, String description, String dueDate, TodoStatus status, String owner, Priority priority) {
        this._id = _id;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.owner = owner;
        this.priority = priority;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public void setStatus(TodoStatus status) {
        this.status = status;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return  owner + " need to "  + description +
                " by " + dueDate +
                ", current status is " + status +
                ". priority: " + priority + "\n";
    }
}
