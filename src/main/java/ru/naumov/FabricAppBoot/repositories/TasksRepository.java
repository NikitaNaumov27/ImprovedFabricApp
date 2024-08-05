package ru.naumov.FabricAppBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.naumov.FabricAppBoot.models.Task;

@Repository
public interface TasksRepository extends JpaRepository<Task, Integer> {

}