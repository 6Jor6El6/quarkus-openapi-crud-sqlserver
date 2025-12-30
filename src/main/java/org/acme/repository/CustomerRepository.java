package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.CustomerEntity;

import java.util.Optional;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<CustomerEntity> {

    public Optional<CustomerEntity> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
}