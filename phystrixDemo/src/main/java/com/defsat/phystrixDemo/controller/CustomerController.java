package com.defsat.phystrixDemo.controller;

import java.util.Map;

import com.google.common.collect.Maps;
import com.defsat.metric.AgentFactory;
import com.defsat.metric.IMetricAgent;
import com.defsat.phystrixDemo.common.Result;
import com.defsat.phystrixDemo.model.Customer;
import com.defsat.phystrixDemo.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    public Result<Customer> getCustomer(@PathVariable String customerId) {
        Customer customer = customerService.getCustomer(customerId);
        Result<Customer> result = new Result<Customer>();
        boolean success = customer==null? false : true;
        String messages = customer==null? "get customer failed" : "OK"; 
        result.setMessages(messages);
        result.setSuccess(success);
        result.setResult(customer);
        
//        IMetricAgent agent = AgentFactory.getAgent();
//        String measureName = "CallTimes";
//        Map<String,String> tags = Maps.newConcurrentMap();
//        //tags
//        tags.put("controller", "getCustomer");
//        agent.log(measureName, "times", tags, 1);
        return result;
    }

}
