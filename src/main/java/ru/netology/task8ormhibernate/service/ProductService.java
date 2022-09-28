package ru.netology.task8ormhibernate.service;

import org.springframework.stereotype.Service;
import ru.netology.task8ormhibernate.repository.IRepository;

import java.util.List;

@Service
public class ProductService {

    private final IRepository repository;

    public ProductService(IRepository repository) {
        this.repository = repository;
    }

    public List<String> getProductName(String name) {
        return repository.getProductName(name);
    }
}
