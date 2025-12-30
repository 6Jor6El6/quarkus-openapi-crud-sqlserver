package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import org.acme.entity.CustomerEntity;
import org.acme.model.api.CreateCustomerRequest;
import org.acme.model.api.Customer;
import org.acme.model.api.UpdateCustomerRequest;
import org.acme.repository.CustomerRepository;

import java.util.List;

@ApplicationScoped
public class CustomerService {

    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public List<Customer> list() {
        return repo.listAll().stream().map(this::toApi).toList();
    }

    public Customer get(Long id) {
        CustomerEntity e = repo.findById(id);
        if (e == null) throw new WebApplicationException("Customer not found", Response.Status.NOT_FOUND);
        return toApi(e);
    }

    @Transactional
    public Customer create(CreateCustomerRequest req) {
        // opcional: validar email único
        repo.findByEmail(req.getEmail()).ifPresent(x -> {
            throw new WebApplicationException("Email already exists", Response.Status.BAD_REQUEST);
        });

        CustomerEntity e = new CustomerEntity();
        e.name = req.getName();
        e.email = req.getEmail();
        repo.persist(e);

        return toApi(e);
    }

    @Transactional
    public Customer update(Long id, UpdateCustomerRequest req) {
        CustomerEntity e = repo.findById(id);
        if (e == null) throw new WebApplicationException("Customer not found", Response.Status.NOT_FOUND);

        // opcional: si cambia email, validar único
        if (req.getEmail() != null && !req.getEmail().equalsIgnoreCase(e.email)) {
            repo.findByEmail(req.getEmail()).ifPresent(x -> {
                throw new WebApplicationException("Email already exists", Response.Status.BAD_REQUEST);
            });
        }

        e.name = req.getName();
        e.email = req.getEmail();

        return toApi(e);
    }

    @Transactional
    public void delete(Long id) {
        CustomerEntity e = repo.findById(id);
        if (e == null) throw new WebApplicationException("Customer not found", Response.Status.NOT_FOUND);
        repo.delete(e);
    }

    private Customer toApi(CustomerEntity e) {
        Customer c = new Customer();
        c.setId(e.id);
        c.setName(e.name);
        c.setEmail(e.email);
        return c;
    }
}
