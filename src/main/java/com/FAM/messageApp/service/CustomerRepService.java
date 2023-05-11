package com.FAM.messageApp.service;

import com.FAM.messageApp.config.RedisConfiguration;
import com.FAM.messageApp.dao.CustomerRepRepository;
import com.FAM.messageApp.model.CustomerRep;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;


//class CustomerRepComparator implements Comparator<CustomerRep> {
//    public int compare(CustomerRep s1, CustomerRep s2) {
//        if (s1.getNoOfChats() < s2.getNoOfChats())
//            return 1;
//        return -1;
//    }
//}


@AllArgsConstructor
@NoArgsConstructor
@Repository
@Slf4j
public class CustomerRepService {
    private static final String HASH_KEY = "CustomerRep";

    @Autowired
    private RedisTemplate redisTemplate;



    public CustomerRep save(CustomerRep customerRep){
        ZSetOperations<String, CustomerRep> zSetOps = redisTemplate.opsForZSet();
        zSetOps.add("customer_rep_queue", customerRep, customerRep.getNoOfChats());
        log.info("Representative is added to the cache");
        return customerRep;
    }
    public CustomerRep findCustomerRep(){
        ZSetOperations<String, CustomerRep> zSetOps = redisTemplate.opsForZSet();
        Set<CustomerRep> highestPriorityElements = zSetOps.range("customer_rep_queue", 0, 0);
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
        Long size = zSetOps.size("customer_rep_queue");
        Set<CustomerRep> list = zSetOps.range("customer_rep_queue", 0, size);
        List<CustomerRep> theList = new ArrayList(list);
        return theList;
    }
    public CustomerRep removeRep(){
        ZSetOperations<String, CustomerRep> zSetOps = redisTemplate.opsForZSet();
        CustomerRep removed = findCustomerRep();
        zSetOps.remove("customer_rep_queue", removed);
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
        zSetOps.remove("customer_rep_queue", customerRep);
        customerRep.setNoOfChats(customerRep.getNoOfChats()-1);
        save(customerRep);
        log.info("Representative " + customerRep.getUsername() + " ended his chat");
    }
    public CustomerRep findById(int id){
        ZSetOperations<String, CustomerRep> zSetOps = redisTemplate.opsForZSet();
        Long size = zSetOps.size("customer_rep_queue");
        Set<CustomerRep> elements = zSetOps.range("customer_rep_queue", 0, size);
        ArrayList<CustomerRep> list = new ArrayList<>(elements);
        for(CustomerRep rep: list){
            if(id == rep.getId()){
                return rep;
            }
        }
        return null;
    }
    public void removeAll(){
        ZSetOperations<String, CustomerRep> zSetOps = redisTemplate.opsForZSet();
        Long size = zSetOps.size("customer_rep_queue");
        zSetOps.removeRange("customer_rep_queue", 0, size);
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
