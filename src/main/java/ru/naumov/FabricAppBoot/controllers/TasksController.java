package ru.naumov.FabricAppBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.naumov.FabricAppBoot.models.Person;
import ru.naumov.FabricAppBoot.models.Task;

import jakarta.validation.Valid;
import ru.naumov.FabricAppBoot.services.PeopleService;
import ru.naumov.FabricAppBoot.services.TasksService;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TasksController {

    private final PeopleService peopleService;
    private final TasksService tasksService;

    @Autowired
    public TasksController(PeopleService peopleService, TasksService tasksService) {
        this.peopleService = peopleService;
        this.tasksService = tasksService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("tasks", tasksService.findAll());
        model.addAttribute("localDate", LocalDate.now());
        return "tasks/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("task", tasksService.findById(id));

        Optional<Person> taskOwner = tasksService.getTaskOwner(id);

        if (taskOwner.isPresent())
            model.addAttribute("owner", taskOwner.get());
        else
            model.addAttribute("people", peopleService.findAll());

        model.addAttribute("localDate", LocalDate.now());

        return "tasks/show";
    }

    @GetMapping("/new")
    public String newTask(@ModelAttribute("task") Task Task) {
        return "tasks/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("task") @Valid Task Task,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "tasks/new";

        tasksService.save(Task);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("task", tasksService.findById(id));
        return "tasks/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("task") @Valid Task task, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "tasks/edit";

        tasksService.update(id, task);
        return "redirect:/tasks";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        tasksService.delete(id);
        return "redirect:/tasks";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        tasksService.release(id);
        return "redirect:/tasks/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        tasksService.assign(id, selectedPerson);
        return "redirect:/tasks/" + id;
    }
}