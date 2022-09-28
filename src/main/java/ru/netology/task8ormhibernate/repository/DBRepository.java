package ru.netology.task8ormhibernate.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.netology.task8ormhibernate.exception.NotFoundPersonException;

import java.util.List;

@Repository
public class DBRepository implements IRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> getProductName(String name) {
        final var query
                = entityManager.createQuery(
                "select o.productName from Orders o join Customers c on o.customer.id = c.id  where c.name = :name",
                String.class
        );
        query.setParameter("name", name);
        final var persons = query.getResultList();
        if (persons.isEmpty()) throw new NotFoundPersonException("City not found");
        return persons;
    }
}
