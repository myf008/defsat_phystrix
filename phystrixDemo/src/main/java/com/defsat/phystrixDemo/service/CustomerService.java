package com.defsat.phystrixDemo.service;


import com.defsat.phystrixDemo.dao.AddressDao;
import com.defsat.phystrixDemo.dao.ContactDao;
import com.defsat.phystrixDemo.model.Address;
import com.defsat.phystrixDemo.model.Customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("customerService")
public class CustomerService {
    private Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private ContactDao contactDao;
    
    @Autowired
    private AddressDao addressDao;

    public Customer getCustomer(String customerId) {
        logger.info("Get Customer {}", customerId);
        try {
            Customer customer = new Customer(customerId, "phystrix");
            Address adress = addressDao.getAddress(customerId);
            customer.setAddress(adress);
            customer.setContact(contactDao.getContact(customerId));
            return customer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
   
}
