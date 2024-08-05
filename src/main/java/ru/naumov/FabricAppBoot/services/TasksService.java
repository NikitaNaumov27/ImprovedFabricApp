package ru.naumov.FabricAppBoot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.naumov.FabricAppBoot.models.Person;
import ru.naumov.FabricAppBoot.models.Task;
import ru.naumov.FabricAppBoot.repositories.TasksRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TasksService {

    private final TasksRepository tasksRepository;

    @Autowired
    public TasksService(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public List<Task> findAll() {
        return tasksRepository.findAll();
    }

    public Task findById(int id) {
        return tasksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Task task) {
        tasksRepository.save(task);
    }

    @Transactional
    public void update(int id, Task updatedTask) {
        Task taskToBeUpdated = tasksRepository.findById(id).get();

        updatedTask.setId(id);
        updatedTask.setOwner(taskToBeUpdated.getOwner()); // чтобы не терялась связь при обновлении

        tasksRepository.save(updatedTask);
    }

    @Transactional
    public void delete(int id) {
        tasksRepository.deleteById(id);
    }

    public Optional<Person> getTaskOwner(int id) {
        // Здесь Hibernate.initialize() не нужен, так как владелец (сторона One) загружается не лениво
        return tasksRepository.findById(id).map(Task::getOwner);
    }


    @Transactional
    public void release(int id) {
        tasksRepository.findById(id).ifPresent(book -> {
            book.setOwner(null);
        });
    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        tasksRepository.findById(id).ifPresent(book -> {
                    book.setOwner(selectedPerson);
                }
        );
    }
}
