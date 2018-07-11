package com.petstore.web.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.petstore.model.PetStoreUser;

@RestController
public class UserController {
    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    @Qualifier(value = "restTemplateLb")
    private RestTemplate restTemplateLb;

    @Autowired
    private DiscoveryClient dc;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
    	
    	List<ServiceInstance> instances=dc.getInstances("SERVICE-PetStore");
		ServiceInstance serviceInstance=instances.get(0);
		
		String baseUrl=serviceInstance.getUri().toString();
		
		LOGGER.info("Spring Service Consumer try to call instance with url: "+baseUrl);
		PetStoreUser user =  restTemplateLb.getForObject(baseUrl+"/userservice/user/1", PetStoreUser.class);
		LOGGER.info("service returned : user : "+user.getName());
        return "returned";
    }
    @RequestMapping(value = "/helloex", method = RequestMethod.GET)
    public ResponseEntity<PetStoreUser> helloEx() {
    	
    	List<ServiceInstance> instances=dc.getInstances("SERVICE-PetStore");
		ServiceInstance serviceInstance=instances.get(0);
		
		String baseUrl=serviceInstance.getUri().toString();
		
		LOGGER.info("Spring Service Consumer try to call instance with url: "+baseUrl);
		//return restTemplateLb.getForEntity(baseUrl+"/userservice/user/1", PetStoreUser.class);
		PetStoreUser user =  restTemplateLb.getForObject(baseUrl+"/userservice/user/1", PetStoreUser.class);
		return new ResponseEntity<PetStoreUser>(user, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/helloM", method = RequestMethod.GET)
    public ResponseEntity<List<PetStoreUser>> helloM() {
    	
    	List<ServiceInstance> instances=dc.getInstances("SERVICE-PetStore");
		ServiceInstance serviceInstance=instances.get(0);
		
		String baseUrl=serviceInstance.getUri().toString();
		
		LOGGER.info("Spring Service Consumer try to call instance with url: "+baseUrl);
		//return restTemplateLb.getForEntity(baseUrl+"/userservice/user/1", PetStoreUser.class);
		PetStoreUser[] users =  restTemplateLb.getForObject(baseUrl+"/userservice/user/", PetStoreUser[].class);
		return new ResponseEntity<List<PetStoreUser>>(Arrays.asList(users), HttpStatus.OK);
    }
}
