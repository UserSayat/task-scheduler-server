package com.example.task_scheduler_server.service.task;

import com.example.task_scheduler_server.common.TaskStage;
import com.example.task_scheduler_server.common.TaskStatus;
import com.example.task_scheduler_server.domain.Task;
import com.example.task_scheduler_server.domain.User;
import com.example.task_scheduler_server.dto.task.TaskRequest;
import com.example.task_scheduler_server.dto.task.TaskResponse;
import com.example.task_scheduler_server.exception.EntityNotFoundException;
import com.example.task_scheduler_server.repository.ITaskRepository;
import com.example.task_scheduler_server.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class TaskService implements ITaskService {

    private final ITaskRepository taskRepository;
    private final IUserRepository userRepository;

    public TaskService(ITaskRepository taskRepository, IUserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TaskResponse createTask(TaskRequest request) {
        //TODO add validation

        User executor = userRepository.findById(request.getExecutorId())
                .orElseThrow(() -> new EntityNotFoundException("Executor: " + request.getExecutorId() + " not found"));

        Task task = new Task(request.getName(),
                request.getDescription(),
                executor,
                request.getDeadline(),
                request.getPriority(),
                request.getStatus(),
                request.getComment(),
                request.getStages(),
                request.getCurrentStage(),
                ZonedDateTime.now(ZoneOffset.UTC),
                ZonedDateTime.now(ZoneOffset.UTC));

        return toTaskResponse(taskRepository.save(task));
    }

    @Override
    public void addStage(long id, TaskStage stage) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task: " + id + " not found"));

        List<TaskStage> stages = task.getStages();
        stages.add(stage);
        task.setStages(stages);
        task.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));

        taskRepository.save(task);

    }

    @Override
    public void assignExecutor(long taskId, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User: " + userId + " not found"));

        if (!user.isFree())
            throw new IllegalArgumentException("User: " + userId + " is busy");

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task: " + taskId + " not found"));

        if (!task.getExecutor().equals(user)) {
            task.setExecutor(user);
            task.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
            taskRepository.save(task);
        }
    }

    @Override
    public TaskResponse editTask(long id, TaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task: " + id + " not found"));

        User executor = userRepository.findById(request.getExecutorId())
                .orElseThrow(() -> new EntityNotFoundException("Executor: " + request.getExecutorId() + " not found"));

        //TODO add validation
        task.setName(request.getName());
        task.setDescription(request.getDescription());
        task.setExecutor(executor);
        task.setDeadline(request.getDeadline());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());
        task.setComment(request.getComment());
        task.setStages(request.getStages());
        task.setCurrentStage(request.getCurrentStage());
        task.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));

        return toTaskResponse(taskRepository.save(task));
    }

    @Override
    public List<TaskStage> checkProgress(long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task: " + id + " not found"));

        return task.getStages().stream()
                .filter(taskStage -> taskStage.getSequenceNumber() < task.getCurrentStage().getSequenceNumber())
                .toList();
    }

    @Override
    public List<TaskResponse> getTaskById(long id) {
        return taskRepository.findTasksByExecutorId(id).stream()
                .map(this::toTaskResponse)
                .toList();
    }

    @Override
    public void changeStage(long id, TaskStage stage) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task: " + id + " not found"));

        task.setCurrentStage(stage);
        task.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));

        // Manually changing the status to "DONE" is prohibited.
        if (task.getStages().getLast().equals(stage))
            task.setStatus(TaskStatus.DONE);

        taskRepository.save(task);
    }

    @Override
    public void addComment(long id, String comment) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task: " + id + " not found"));

        String newComment = task.getComment() + "\n\n" + comment + ZonedDateTime.now(ZoneOffset.UTC);
        task.setComment(newComment);

        taskRepository.save(task);
    }

    private TaskResponse toTaskResponse(Task task) {

        return new TaskResponse(task.getId(),
                task.getName(),
                task.getDescription(),
                task.getExecutor().getId(),
                task.getDeadline(),
                task.getPriority(),
                task.getStatus(),
                task.getComment(),
                task.getStages(),
                task.getCurrentStage());
    }
}
