package com.mshz.service.specification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import com.mshz.domain.Task;
import com.mshz.domain.TaskStatusTraking;
import com.mshz.domain.TaskStatusTraking_;
import com.mshz.domain.TaskUser;
import com.mshz.domain.TaskUser_;
import com.mshz.domain.Task_;
import com.mshz.domain.enumeration.TaskStatus;
import com.mshz.domain.enumeration.TaskUserRole;
import com.mshz.domain.Process;

public class TaskSpecification {
    
    public static Specification<Task> processIsIntanceAndValid(){
        return (root, query, cb) ->{
            query.distinct(true);
            Subquery<Process> subquery = query.subquery(Process.class);
            Root<Process> subRoot = subquery.from(Process.class);

            Predicate validPred = cb.isTrue(subRoot.get("valid"));
            Predicate isInstancePred = cb.isNotNull(subRoot.get("modelId"));
            Predicate joinIdPred =  cb.equal(root.get(Task_.PROCESS_ID), subRoot.get("id"));

            subquery = subquery.select(subRoot.get("id"))
                .where(validPred,isInstancePred, joinIdPred);
            return cb.exists(subquery);
        };
    }
    
    
    
    public static Specification<Task> hastTrackingPredicates(final List<TaskStatus> status,final Instant start,final Instant end,final Boolean execeed){
        return (root, query, cb) ->{
            List<Predicate> predicates = new ArrayList<>();
            
            Subquery<TaskStatusTraking> subquery = query.subquery(TaskStatusTraking.class);
            Root<TaskStatusTraking> subqueryRoot = subquery.from(TaskStatusTraking.class);
            
            // subquery link predicat
            predicates.add(cb.equal(subqueryRoot.get(TaskStatusTraking_.TASK_ID),root.get(Task_.ID)));
            
            // traking is indicator predicat
            // predicates.add(cb.isTrue(subqueryRoot.get(TaskStatusTraking_.perfIndicator)));
            
            // date interval and status predicates 
            if(start != null || end != null){
                // interval date predicates
                if(start != null && end == null)
                    predicates.add(cb.greaterThanOrEqualTo(subqueryRoot.get(TaskStatusTraking_.TRACING_AT), start));
                else if(start == null && end != null)
                    predicates.add(cb.lessThanOrEqualTo(subqueryRoot.get(TaskStatusTraking_.TRACING_AT), end));
                else
                    predicates.add(cb.between(subqueryRoot.get(TaskStatusTraking_.TRACING_AT), start, end));

            }
            // adding status predicat
            if(status != null && status.isEmpty()) {
                predicates.add(subqueryRoot.get(TaskStatusTraking_.STATUS).in(status));
            }
          
            // execeedPredicate
            if(execeed != null) {
                predicates.add(cb.equal(subqueryRoot.get(TaskStatusTraking_.EXECEED), execeed));
            }
            // user filer predicate 
            subquery.select(subqueryRoot.get(TaskStatusTraking_.TASK_ID))
                    .where(predicates.toArray(new Predicate[predicates.size()]));
            return root.get(Task_.id).in(subquery);
        };
        
    }
    
    public static Specification<Task> hasUserAndRole(final List<Long> userIds, final List<TaskUserRole> roles) {
        return (root, query, cb) ->{
            Subquery<TaskUser> subquery = query.subquery(TaskUser.class);
            Root<TaskUser> subqueryRoot = subquery.from(TaskUser.class);
            Predicate rolePredicate  = subqueryRoot.get(TaskUser_.ROLE).in(roles);
            Predicate userIdPredicate = subqueryRoot.get(TaskUser_.USER_ID).in(userIds);
            Predicate taskPredicat = cb.equal(subqueryRoot.get(TaskUser_.task).get(Task_.ID),root.get(Task_.ID));
            subquery.select(subqueryRoot.get(TaskUser_.task).get("id"))
                    .where(taskPredicat,rolePredicate, userIdPredicate);
            return root.get(Task_.id).in(subquery);
        };
    }
    
    public static Specification<Task> hasStatus(final List<TaskStatus> status){
        return (root, query, cb) ->{
            if(status != null && !status.isEmpty())
               return root.get(Task_.STATUS).in(status);
            return cb.conjunction();
        };
    }
    
    public static Specification<Task> hasExceceed(final Boolean exceceed){
        return (root, query, cb) ->{
            Predicate equalPred = cb.equal(root.get(Task_.EXCECEED), exceceed);
            Predicate nullPred = cb.isNull(root.get(Task_.EXCECEED));
            if(Boolean.FALSE.equals(exceceed))
                return cb.or(equalPred, nullPred);
            return equalPred;
        };
    }

    public static Specification<Task> hasValid(final Boolean valid){
        return (root, query, cb) ->{
            return cb.equal(root.get(Task_.VALID), valid);
        };
    }
}
