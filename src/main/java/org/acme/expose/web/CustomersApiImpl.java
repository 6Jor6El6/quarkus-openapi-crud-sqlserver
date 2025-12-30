package org.acme.expose.web;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.api.CreateCustomerRequest;
import org.acme.model.api.Customer;
import org.acme.model.api.UpdateCustomerRequest;
import org.acme.service.CustomerService;
import org.acme.expose.web.CustomersApi;

import java.util.List;


@ApplicationScoped
public class CustomersApiImpl implements  CustomersApi {

    @Inject
    CustomerService service;


    @Override
    public Customer createCustomer(CreateCustomerRequest createCustomerRequest) {
        return service.create(createCustomerRequest);
    }

    @Override
    public void deleteCustomer(Long id) {
        service.delete(id);
    }

    @Override
    public Customer getCustomer(Long id) {
        return service.get(id);
    }

    @Override
    public List<Customer> listCustomers() {
        return service.list();
    }

    @Override
    public Customer updateCustomer(Long id, UpdateCustomerRequest updateCustomerRequest) {
        return service.update(id, updateCustomerRequest);
    }

/*
    // Demo en memoria (luego lo cambiamos por DB)
    private final Map<Long, Customer> store = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(0);

    @Override
    public Customer createCustomer(CreateCustomerRequest req) {
        // @NotNull lo valida Quarkus Validator si está activo, pero igual protegemos:
        if (req == null) {
            throw new WebApplicationException("Body is required", Response.Status.BAD_REQUEST);
        }

        long id = seq.incrementAndGet();

        Customer c = new Customer();
        c.setId(id);
        c.setName(req.getName());
        c.setEmail(req.getEmail());

        store.put(id, c);
        return c;
    }

    @Override
    public void deleteCustomer(Long id) {
        if (id == null) {
            throw new WebApplicationException("id is required", Response.Status.BAD_REQUEST);
        }

        Customer removed = store.remove(id);
        if (removed == null) {
            throw new WebApplicationException("Customer not found", Response.Status.NOT_FOUND);
        }
        // void -> 204 sin body si no lanzas excepción
    }

    @Override
    public Customer getCustomer(Long id) {
        if (id == null) {
            throw new WebApplicationException("id is required", Response.Status.BAD_REQUEST);
        }

        Customer c = store.get(id);
        if (c == null) {
            throw new WebApplicationException("Customer not found", Response.Status.NOT_FOUND);
        }

        return c;
    }

    @Override
    public List<Customer> listCustomers() {
        return store.values().stream().toList();
    }


    @Override
    public Customer updateCustomer(Long id, UpdateCustomerRequest req) {
        if (id == null) {
            throw new WebApplicationException("id is required", Response.Status.BAD_REQUEST);
        }
        if (req == null) {
            throw new WebApplicationException("Body is required", Response.Status.BAD_REQUEST);
        }

        Customer existing = store.get(id);
        if (existing == null) {
            throw new WebApplicationException("Customer not found", Response.Status.NOT_FOUND);
        }

        existing.setName(req.getName());
        existing.setEmail(req.getEmail());
        store.put(id, existing);

        return existing;
    }*/
}
