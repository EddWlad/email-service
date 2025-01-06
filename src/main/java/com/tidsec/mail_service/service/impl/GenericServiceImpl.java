package com.tidsec.mail_service.service.impl;

import com.tidsec.mail_service.exception.ModelNotFoundException;
import com.tidsec.mail_service.repositories.IGenericRepository;
import com.tidsec.mail_service.service.IGenericService;

import java.util.List;
import java.util.Optional;

public abstract class GenericServiceImpl<T, ID> implements IGenericService<T, ID> {

    protected abstract IGenericRepository<T, ID> getRepo();
    @Override
    public List<T> getAll() {
        return getRepo().findByStatusNot(0);    }

    @Override
    public Optional<T> findById(ID id) {
        return getRepo().findById(id)
                .filter(t -> {
                    try {
                        return (Integer) t.getClass().getMethod("getStatus").invoke(t) != 0;
                    } catch (Exception e) {
                        throw new IllegalStateException("The class does not have a 'getStatus' method or an error occurred: " + e.getMessage());
                    }
                })
                .or(() -> {
                    throw new ModelNotFoundException("ID NOT FOUND: " + id);
                });
    }

    @Override
    public T save(T t) {
        return getRepo().save(t);
    }

    @Override
    public T update(ID id, T t) {
        Optional<T> tb = getRepo().findById(id)
                .filter(data -> {
                    try {
                        return (Integer) data.getClass().getMethod("getStatus").invoke(data) != 0;
                    } catch (Exception e) {
                        throw new IllegalStateException("The class does not have a 'getStatus' method or an error occurred: " + e.getMessage());
                    }
                })
                .or(() -> {
                    throw new ModelNotFoundException("ID NOT FOUND: " + id);
                });
        return getRepo().save(t);
    }

    @Override
    public boolean delete(ID id) {
        T entity = getRepo().findById(id)
                .filter(data -> {
                    try {
                        return (Integer) data.getClass().getMethod("getStatus").invoke(data) != 0;
                    } catch (Exception e) {
                        throw new IllegalStateException("The class does not have a 'getStatus' method or an error occurred: " + e.getMessage());
                    }
                })
                .orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND OR INACTIVE: " + id));

        try {
            entity.getClass().getMethod("setStatus", Integer.class).invoke(entity, 0);
        } catch (Exception e) {
            throw new IllegalStateException("The class does not have a 'setStatus' method or an error occurred: " + e.getMessage());
        }
        getRepo().save(entity);
        return true;
    }

    @Override
    public Long count() {
        return null;
    }
}
