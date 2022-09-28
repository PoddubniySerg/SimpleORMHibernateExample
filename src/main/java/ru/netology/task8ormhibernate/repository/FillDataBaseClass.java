package ru.netology.task8ormhibernate.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.netology.task8ormhibernate.repository.dbintities.Customers;
import ru.netology.task8ormhibernate.repository.dbintities.Orders;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Component
public class FillDataBaseClass implements CommandLineRunner {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) {
        final var names = List.of("Misha", "Vasya", "Petya", "Harry", "Vitaliy", "Vadim");
        final var surnames = List.of("Oganesov", "Petrov", "Drokin", "Pankratov", "Zubkov", "Gibert");
        final var productNames = List.of("Fridge", "TV", "Oven", "Bicycle", "Cola", "Spoon", "Table", "Book");

        final var minDate = LocalDate.of(2019, 1, 1).toEpochDay();
        final var maxDate = LocalDate.now().toEpochDay();

        final var random = new Random();

        IntStream.range(0, 100).forEach(i -> {
            final var customer = Customers.builder()
                    .name(names.get(random.nextInt(names.size())))
                    .surname(surnames.get(random.nextInt(surnames.size())))
                    .phoneNumber("XXXXXXXXXXX")
                    .build();
            entityManager.merge(customer);
        });

        final var customers =
                entityManager.createQuery("select c from Customers c", Customers.class).getResultList();

        IntStream.range(0, 100).forEach(i -> {
            final var order = Orders.builder()
                    .date(LocalDate.ofEpochDay(random.nextLong(minDate, maxDate)))
                    .amount(random.nextInt(100, 2000000000))
                    .customer(customers.get(random.nextInt(customers.size())))
                    .productName(productNames.get(random.nextInt(productNames.size())))
                    .build();
            entityManager.merge(order);
        });
    }
}
