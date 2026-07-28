package com.example.task_scheduler_server.service.task;

import com.example.task_scheduler_server.common.TaskStage;
import com.example.task_scheduler_server.common.TaskStatus;
import com.example.task_scheduler_server.domain.Task;
import com.example.task_scheduler_server.domain.User;
import com.example.task_scheduler_server.dto.task.TaskRequest;
import com.example.task_scheduler_server.dto.task.TaskResponse;
import com.example.task_scheduler_server.dto.task.TaskStageRequest;
import com.example.task_scheduler_server.exception.EntityNotFoundException;
import com.example.task_scheduler_server.repository.ITaskRepository;
import com.example.task_scheduler_server.repository.IUserRepository;
import jakarta.transaction.Transactional;
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

    private TaskResponse toTaskResponse(Task task) {
        Long executorId = null;

        if (task.getExecutor() != null)
            executorId = task.getExecutor().getId();


        return new TaskResponse(task.getId(),
                task.getName(),
                task.getDescription(),
                executorId,
                task.getDeadline(),
                task.getPriority(),
                task.getStatus(),
                task.getComment(),
                task.getStages(),
                task.getCurrentStage());
    }

    @Override
    public TaskResponse createTask(TaskRequest request) {
        //TODO add validation

        User executor = null;

        if (request.getExecutorId() != null) {
            executor = userRepository.findById(request.getExecutorId())
                    .orElseThrow(() -> new EntityNotFoundException("Executor: " + request.getExecutorId() + " not found"));
        }

        Task task = new Task(request.getName(),
                request.getDescription(),
                executor,
                request.getDeadline(),
                request.getPriority(),
                request.getStatus(),
                request.getComment(),
                request.getStages(),
                // Upon creation, the current stage is determined in the constructor
                ZonedDateTime.now(ZoneOffset.UTC),
                ZonedDateTime.now(ZoneOffset.UTC));

        return toTaskResponse(taskRepository.save(task));
    }

    @Override
    public void addStage(long id, TaskStageRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task: " + id + " not found"));

        List<TaskStage> stages = task.getStages();
        stages.add(new TaskStage(request.getName(), request.getSequenceNumber()));
        task.setStages(stages);
        task.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));

        taskRepository.save(task);

    }

    @Override
    @Transactional
    public void assignExecutor(long taskId, long executorId) {
        User user = userRepository.findById(executorId)
                .orElseThrow(() -> new EntityNotFoundException("User: " + executorId + " not found"));

        if (!user.isFree())
            throw new IllegalArgumentException("User: " + executorId + " is busy");

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task: " + taskId + " not found"));

        if ((task.getExecutor() == null) || (!task.getExecutor().equals(user))) {
            task.setExecutor(user);
            task.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
            List<Task> tasks = user.getTasks();
            tasks.add(task);
            user.setTasks(tasks);
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
                .filter(taskStage -> taskStage.getSequenceNumber() <= task.getCurrentStage())
                .toList();
    }

    @Override
    public List<TaskResponse> getTaskById(long id) {
        return taskRepository.findTasksByExecutorId(id).stream()
                .map(this::toTaskResponse)
                .toList();
    }

    @Override
    public void changeStage(long id, long taskStageSequenceNumber) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task: " + id + " not found"));

        task.setCurrentStage(taskStageSequenceNumber);
        task.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));

        // Manually changing the status to "DONE" is prohibited.
        if (task.getStages().getLast().getSequenceNumber() == taskStageSequenceNumber)
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
}
