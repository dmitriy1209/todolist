package com.digital.uwp.model.dto;

import com.digital.uwp.model.entity.Status;
import com.digital.uwp.model.entity.Task;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskCreateDto implements BaseDto<Task> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @NotEmpty
    @Size(min = 4, max = 256)
    @JsonProperty("name")
    private String name;

    @Size(max = 1024)
    @JsonProperty("description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JsonProperty("status")
    private Status status;

    @Override
    public Task fromDto() {
        return Task.builder()
            .description(description)
            .name(name)
            .status(status)
            .build();
    }

}
