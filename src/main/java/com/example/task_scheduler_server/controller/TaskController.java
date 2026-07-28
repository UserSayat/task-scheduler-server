package com.example.task_scheduler_server.controller;

import com.example.task_scheduler_server.common.CommonResponse;
import com.example.task_scheduler_server.common.TaskStage;
import com.example.task_scheduler_server.dto.task.TaskRequest;
import com.example.task_scheduler_server.dto.task.TaskResponse;
import com.example.task_scheduler_server.dto.task.TaskStageRequest;
import com.example.task_scheduler_server.service.task.ITaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final ITaskService taskService;

    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<TaskResponse>> createTask(@RequestBody TaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse<>(taskService.createTask(request)));
    }

    @PatchMapping("/{id}/addStage")
    public ResponseEntity<CommonResponse<Void>> addStage(@PathVariable long id,
                                                         @RequestBody TaskStageRequest stage) {
        taskService.addStage(id, stage);
        return ResponseEntity.ok()
                .build();
    }

    @PatchMapping("/{taskId}/executor")
    public ResponseEntity<CommonResponse<Void>> assignExecutor(@PathVariable long taskId,
                                                               @RequestParam long executorId) {
        taskService.assignExecutor(taskId, executorId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<TaskResponse>> editTask(@PathVariable long id,
                                                                 @RequestBody TaskRequest request) {
        return ResponseEntity.ok(new CommonResponse<>(taskService.editTask(id, request)));
    }

    @GetMapping("/{id}/progress")
    public ResponseEntity<CommonResponse<List<TaskStage>>> checkProgress(@PathVariable long id) {
        return ResponseEntity.ok(new CommonResponse<>(taskService.checkProgress(id)));
    } // returns completed stages

//    //List<Statistics> getReport(long id);

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<List<TaskResponse>>> getTaskById(@PathVariable long id) {
        return ResponseEntity.ok(new CommonResponse<>(taskService.getTaskById(id)));
    }

    @PatchMapping("/{id}/changeStage")
    public ResponseEntity<CommonResponse<Void>> changeStage(@PathVariable long id,
                                                            @RequestParam long taskStageSequenceNumber) {
        taskService.changeStage(id, taskStageSequenceNumber);
        return ResponseEntity.ok()
                .build();
    } // Manually changing the status to "DONE" is prohibited.

    @PatchMapping("/{id}/comment")
    public ResponseEntity<CommonResponse<Void>> addComment(@PathVariable long id,
                                                           @RequestBody String comment) {
        taskService.addComment(id, comment);
        return ResponseEntity.ok()
                .build();
    }
}
