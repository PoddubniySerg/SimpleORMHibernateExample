package ru.netology.task8ormhibernate.repository;

import java.util.List;

public interface IRepository {

    List<String> getProductName(String name);
}
