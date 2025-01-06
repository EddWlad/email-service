package com.tidsec.mail_service.service;

import com.tidsec.mail_service.entities.Supplier;

import java.util.List;
import java.util.Optional;

public interface IGenericService<T, ID> {
    List<T> getAll();
    Optional<T> findById(ID id);
    T save(T t);
    T update(ID id, T t);
    public boolean delete(ID id);
    Long count();
}
