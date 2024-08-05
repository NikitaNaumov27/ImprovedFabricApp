package ru.naumov.FabricAppBoot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "ФИО сотрудника не должно быть пустым")
    @Size(min = 2, max = 50, message = "ФИО сотрудника должно быть от 2 до 50 символов длиной")
    @Column(name = "full_name")
    private String fullName;

    @Min(value = 1900, message = "Год рождения должен быть больше, чем 1900")
    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @NotEmpty(message = "Должность сотрудника не должна быть пустой")
    @Size(min = 2, max = 50, message = "Должность сотрудника должна быть от 2 до 50 символов длиной")
    @Column(name = "position")
    private String position;

    @OneToMany(mappedBy = "owner")
    private List<Task> tasks;

    // Конструктор по умолчанию нужен для Spring
    public Person() {
    }

    public Person(String fullName, int yearOfBirth, String position) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<Task> getTasks() {return tasks;}

    public void setTasks(List<Task> tasks) {this.tasks = tasks;}
}