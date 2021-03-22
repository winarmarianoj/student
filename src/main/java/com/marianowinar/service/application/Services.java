package com.marianowinar.service.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public interface Services<T> {
	public boolean create(T entity);
	public boolean update(T entity);
	public boolean delete(@PathVariable Long id);
	public List<T> viewAll();
	public T take(Long id);
}
