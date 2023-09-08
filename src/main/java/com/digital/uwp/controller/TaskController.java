package com.digital.uwp.controller;

import com.digital.uwp.model.dto.TaskCreateDto;
import com.digital.uwp.model.entity.Status;
import com.digital.uwp.model.entity.Task;
import com.digital.uwp.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/todolist")
public class TaskController {

    private final TaskService service;

    @Operation(description = "API to find task by ID")
    @GetMapping
    public Task findById(@RequestParam(value = "id") @Positive Long id) {
        return service.findByIdOrThrow(id);
    }

    @Operation(description = "API to find all existing tasks")
    @GetMapping("all")
    public Page<Task> findAll(
        @Parameter(example = "0") @RequestParam(value = "page_number") @NotNull @PositiveOrZero Integer pageNumber,
        @Parameter(example = "10") @RequestParam(value = "page_size") @NotNull @Positive Integer pageSize) {
        return service.findAll(PageRequest.of(pageNumber, pageSize));
    }

    @Operation(description = "API to create new task")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Task create(@RequestBody @NotNull @Valid TaskCreateDto task) {
        return service.save(task.fromDto());
    }

    @Operation(description = "API to change status of existing task")
    @PutMapping("status")
    public void updateStatus(
        @RequestParam(value = "id") @NotNull @Positive Long id, 
        @RequestParam(value = "status") @NotNull Status status) {
        service.updateStatus(id, status);
    }

    @Operation(description = "API to change description of existing task")
    @PutMapping("description")
    public void updateDescription(
        @RequestParam(value = "id") @NotNull @Positive Long id, 
        @RequestParam(value = "description") @NotNull String description) {
        service.updateDescription(id, description);
    }
}
