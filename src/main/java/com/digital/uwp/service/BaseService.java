package com.digital.uwp.service;

import com.digital.uwp.exception.ApiException;
import com.digital.uwp.repository.BaseRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class BaseService<E, ID> {

    private final BaseRepository<E, ID> repository;

    private final Class<E> entityClass;

    public Optional<E> findById(ID id) {
        return repository.findById(id);
    }

    public E findByIdOrThrow(ID id) {
        return findById(id).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST,
            entityClass.getSimpleName() + " with id " + id + " not exist"));
    }

    public ID existByIdOrThrow(ID id) {
        if (repository.existsById(id)) {
            return id;
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                entityClass.getSimpleName() + " with id " + id + " not exist");
        }
    }

    public E save(E e) {
        return repository.save(e);
    }

    public Page<E> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

}
