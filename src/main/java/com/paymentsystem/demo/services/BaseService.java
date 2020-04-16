package com.paymentsystem.demo.services;

import java.util.List;

public interface BaseService<T> {
    T getById(Long id);

    List<T> getAll();

    T save(T item);
}
