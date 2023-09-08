package com.digital.uwp.repository;

import com.digital.uwp.model.entity.Status;
import com.digital.uwp.model.entity.Task;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TaskRepository extends BaseRepository<Task, Long> {

    @Modifying
    @Transactional
    @Query("update Task task set task.status=:status where task.id=:id")
    void updateStatus(@Param("id") Long id, @Param("status") Status status);

    @Modifying
    @Transactional
    @Query("update Task task set task.description=:description where task.id=:id")
    void updateDescription(@Param("id") Long id, @Param("description") String description);

}
