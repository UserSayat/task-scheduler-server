package com.example.task_scheduler_server.service.task;

import com.example.task_scheduler_server.common.TaskStage;
import com.example.task_scheduler_server.domain.User;
import com.example.task_scheduler_server.dto.task.TaskRequest;
import com.example.task_scheduler_server.dto.task.TaskResponse;

import java.util.List;

public interface ITaskService {
    TaskResponse createTask(TaskRequest request);
    void addStage(long id, TaskStage stage);
    void assignExecutor(long taskId, long userId);
    TaskResponse editTask(long id, TaskRequest request);
    List<TaskStage> checkProgress(long id); // returns completed stages
    //List<Statistics> getReport(long id);
    List<TaskResponse> getTaskById(long id); //
    void changeStage(long id, TaskStage stage); // Manually changing the status to "DONE" is prohibited.
    void addComment(long id, String comment);
}
