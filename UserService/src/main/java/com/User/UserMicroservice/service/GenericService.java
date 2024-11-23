package com.User.UserMicroservice.service;

import com.User.UserMicroservice.persistence.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class GenericService<T, ID extends Serializable> {

    private Repository<T, ID> repository;

    public Optional<T> findAll(ID id) {
        return repository.findById(id);
    }

    public T save(T entity) {
        return repository.save(entity);
    }

    public List<T> saveAll(List<T> entities) {
        return (List<T>) repository.saveAll(entities);
    }

    public T update(ID id, T entity) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Entity not found with ID: " + id);
        }
        return repository.save(entity);
    }

    public void deleteById(ID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Entity not found with ID: " + id);
        }
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

}
