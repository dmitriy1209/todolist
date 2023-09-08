package com.digital.uwp.service;

import com.digital.uwp.model.entity.Status;
import com.digital.uwp.model.entity.Task;
import com.digital.uwp.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService extends BaseService<Task, Long> {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        super(taskRepository, Task.class);
        this.taskRepository = taskRepository;
    }

    public void updateStatus(Long id, Status status) {
        taskRepository.updateStatus(existByIdOrThrow(id), status);
    }

    public void updateDescription(Long id, String description) {
        taskRepository.updateDescription(existByIdOrThrow(id), description);
    }
}
