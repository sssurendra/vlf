package com.jpa_curd.curdDemo.controller;

import com.jpa_curd.curdDemo.model.Customer;
import com.jpa_curd.curdDemo.service.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerRestController {

    @Autowired
    private CustomerServices customerServices;

  /*  @GetMapping("/allCustomers")
    public List<Customer> findAll() {
        return customerServices.findAll();
    }

   */

  @GetMapping("/allCustomers")
    public ResponseEntity<List<Customer>> findAll() {
        List<Customer> customerList = customerServices.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerList);
    }

    @GetMapping("/getCustommer/")
    public ResponseEntity<Customer> getCustomer(@RequestParam Long id) {
        Customer customer = customerServices.findById(id);
        //   return new ResponseEntity<>(customer,HttpStatus.OK);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customer);
    }

    @PostMapping("/createCustomer")
    public ResponseEntity create(@RequestBody Customer customer) {
        Optional<Customer> customerEmail = customerServices.findByEmail(customer.getEmail());
        if (customerEmail.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Customer Email already exist");
        }
        Optional<Customer> customerPhone = customerServices.findByPhone(customer.getPhone());
        if (customerPhone.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Customer phone already exist");
        }
        customerServices.save(customer);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Customer create Successful");
    }

    @PutMapping("/updateCustomer/")
    public ResponseEntity updateCustomer(@Valid @RequestBody Customer customer) {
        Customer customerResult = customerServices.findById(customer.getId());

        Optional<Customer> customerEmail = customerServices.findByEmail(customer.getEmail());
        if (customerEmail.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Customer Email already exist");
        }

        Optional<Customer> customerPhone = customerServices.findByPhone(customer.getPhone());
        if (customerPhone.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Customer phone already exist");
        }

        customerServices.save(customerResult);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("update Successful");

    }

  /*  @PatchMapping("/updateCustomer/{id}")
    public ResponseEntity updatePartially(@PathVariable int id, @RequestBody Map<Object, Object> fields) {
        Customer customer = customerServices.findById(id);

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Customer.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, customer, value);
        });
        customerServices.save(customer);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("update Successful id= " + id);
    }

   */

    @DeleteMapping("/deleteCustomer/")
    public ResponseEntity deleteCustomer(@RequestParam Long id) {
        customerServices.findById(id);
        customerServices.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("delete successful");
    }

}
