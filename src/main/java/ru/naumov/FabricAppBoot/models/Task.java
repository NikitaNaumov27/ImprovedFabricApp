package ru.naumov.FabricAppBoot.models;

import jakarta.persistence.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Task")
public class Task {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Название задачи не должно быть пустым")
    @Size(min = 2, max = 60, message = "Название задачи должно быть от 2 до 60 символов длиной")
    @Column(name = "task_name")
    private String taskName;

    @Min(value = 1, message = "Срок выполнения должен быть больше, чем 1 день")
    @Column(name = "deadline")
    private int deadline;

    @Min(value = 1, message = "Сложность задачи не может быть меньше 1 (Легкая)")
    @Max(value = 3, message = "Сложность задачи не может быть больше 3 (Сложная)")
    @Column(name = "difficulty")
    private int difficulty;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Task() {
    }

    public Task(String taskName, int deadline, int difficulty) {
        this.taskName = taskName;
        this.deadline = deadline;
        this.difficulty = difficulty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Person getOwner() {return owner;}

    public void setOwner(Person owner) {this.owner = owner;}
}