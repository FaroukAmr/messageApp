package com.FAM.messageApp.service;

import com.FAM.messageApp.model.CustomerRep;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;


@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class CustomerRepService {
    private static final String HASH_KEY = "customer_rep_queue";

    @Autowired
    private RedisTemplate<String, CustomerRep> redisTemplate;



    public CustomerRep save(CustomerRep customerRep){
        ZSetOperations<String, CustomerRep> zSetOps = redisTemplate.opsForZSet();
        zSetOps.add(HASH_KEY, customerRep, customerRep.getNoOfChats());
        log.info("Representative is added to the cache");
        return customerRep;
    }
    public CustomerRep findCustomerRep(){
        ZSetOperations<String, CustomerRep> zSetOps = redisTemplate.opsForZSet();
        Set<CustomerRep> highestPriorityElements = zSetOps.range(HASH_KEY, 0, 0);
        if (!highestPriorityElements.isEmpty()) {
            CustomerRep best = highestPriorityElements.iterator().next();
            log.info("Representative should take this" + best.getUsername());
            return best;
        } else {
            log.info("No Available Representatives");
            return null;
        }
    }
    public List<CustomerRep> findAll(){
        ZSetOperations<String, CustomerRep> zSetOps = redisTemplate.opsForZSet();
        Long size = zSetOps.size(HASH_KEY);
        if(size!=null) {
            Set<CustomerRep> list = zSetOps.range(HASH_KEY, 0, size);
            List<CustomerRep> theList = new ArrayList<>(list);
            return theList;
        }
        return null;
    }
    public CustomerRep removeRep(){
        ZSetOperations<String, CustomerRep> zSetOps = redisTemplate.opsForZSet();
        CustomerRep removed = findCustomerRep();
        zSetOps.remove(HASH_KEY, removed);
        log.info("Representative " + removed.getUsername() + " is removed.");
        return removed;
    }
    public void takeCustomerRep(){
        CustomerRep rep = removeRep();
        rep.setNoOfChats(rep.getNoOfChats()+1);
        save(rep);
        log.info("Representative " + rep.getUsername() + " is handling that inquiry");
    }

    public void customerRepEndChat(CustomerRep customerRep){
        ZSetOperations<String, CustomerRep> zSetOps = redisTemplate.opsForZSet();
        zSetOps.remove(HASH_KEY, customerRep);
        customerRep.setNoOfChats(customerRep.getNoOfChats()-1);
        save(customerRep);
        log.info("Representative " + customerRep.getUsername() + " ended his chat");
    }
    public CustomerRep findById(int id){
        ZSetOperations<String, CustomerRep> zSetOps = redisTemplate.opsForZSet();
        Long size = zSetOps.size(HASH_KEY);
        if(size!=null){
            Set<CustomerRep> elements = zSetOps.range(HASH_KEY, 0, size);
            ArrayList<CustomerRep> list = new ArrayList<>(elements);
            for(CustomerRep rep: list){
                if(id == rep.getId()){
                    return rep;
                }
            }
        }
        return null;
    }
    public void removeAll(){
        ZSetOperations<String, CustomerRep> zSetOps = redisTemplate.opsForZSet();
        Long size = zSetOps.size(HASH_KEY);
        if(size!=null)
            zSetOps.removeRange(HASH_KEY, 0, size);
    }

//    public CustomerRep save(CustomerRep customerRep){
//        redisTemplate.opsForHash().put(HASH_KEY,customerRep.getId(),customerRep);
//
//        return customerRep;
//    }
//
//    public List<CustomerRep> findAll(){
//        List<CustomerRep> list = new ArrayList<CustomerRep>();
//        redisTemplate.opsForHash().values(HASH_KEY).forEach( i -> {
//            list.add((CustomerRep) i);
//        });
//        return list;
//    }

//    public CustomerRep findCustomerRepByID(int id){
//        return (CustomerRep) redisTemplate.opsForHash().get(HASH_KEY,id);
//    }
//    public String deleteCustomerRepById(int id){
//        redisTemplate.opsForHash().delete(HASH_KEY, id);
//        return "Removed";
//    }
//    @Cacheable
//    public CustomerRep findSuitableCustomerRep(){
//        List<CustomerRep> customerReps = findAll();
//        int minChats = Integer.MAX_VALUE;
//        CustomerRep theOne = null;
//        for(int i =0;i<customerReps.size();i++){
//            if(customerReps.get(i).getNoOfChats()<minChats){
//                minChats = customerReps.get(i).getNoOfChats();
//                theOne = customerReps.get(i);
//            }
//        }
//        return theOne;
//    }
//    public CustomerRep findPerfectCustomerRep(){
//
//    }

}