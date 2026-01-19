package com.surya.taskmanager.service;
import com.surya.taskmanager.model.Task;
import com.surya.taskmanager.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Page<Task> getAllTasks(Pageable pageable){
        return taskRepository.findAll(pageable);
    }

    public Task getTaskById(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }

    public void deleteTaskById(Long id) {
        if(!taskRepository.existsById(id)){
            throw new EntityNotFoundException("Task not found");
        }
        taskRepository.deleteById(id);
    }

    public Task updateTask(Long id, Task task){
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        if(task.getTitle() != null && !task.getTitle().isBlank()){
            existingTask.setTitle(task.getTitle());
        }

        if(task.getDescription() != null && !task.getDescription().isBlank()){
            existingTask.setDescription(task.getDescription());
        }

        if(task.getStatus() != null && !task.getStatus().isBlank()){
            existingTask.setStatus(task.getStatus());
        }

        return taskRepository.save(existingTask);
    }

}
