package com.mshz.service;

import java.util.List;
import java.util.stream.Collectors;

import com.mshz.model.Privilege;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CustomPermissionEvalutor {
    
    public static final String PRIVILEGE_STRING_PREFIX= "PRIVILEGES_";

    public static final String PRIVILEGE_FIELDS_SEPARATOR = " ";

    public static final String PRIVILEGE_ACTIONS_SEPARATOR=";";

    public static final String ALL_PRIVILEGES_ENTITY = "Application";

    private final Logger log = LoggerFactory.getLogger(CustomPermissionEvalutor.class);

    public boolean hasPermission(String entity, String action){
        extractPrivileges().forEach(p -> log.debug("Privilege entitiy {} action {} authority {}", p.getEntity(), p.getAction(), p.getAuthority()));
        List<Privilege> privileges = extractPrivileges().stream()
                .filter(pv -> (pv.getEntity() !=null && pv.getEntity().equalsIgnoreCase(entity)) 
                        || pv.getAction().toUpperCase().contains(ALL_PRIVILEGES_ENTITY.toUpperCase())
                        || pv.getEntity().toUpperCase().contains(ALL_PRIVILEGES_ENTITY.toUpperCase())
                        )
                .collect(Collectors.toList());
        if(action != null && !action.equalsIgnoreCase("all") && !privileges.isEmpty()){
            return !privileges.stream()
                .filter(pv -> pv.getAction() != null &&
                  (pv.getAction().toLowerCase().contains(action.toLowerCase())
                     || pv.getAction().equalsIgnoreCase("all")
                    || pv.getAction().toUpperCase().contains(ALL_PRIVILEGES_ENTITY.toUpperCase())
                    || pv.getEntity().toLowerCase().contains(ALL_PRIVILEGES_ENTITY.toUpperCase())
                  ))
                .collect(Collectors.toList()).isEmpty();
        }
        return !privileges.isEmpty();
    }
    
    public List<Privilege> extractPrivileges(){
        return SecurityContextHolder
         .getContext()
         .getAuthentication()
         .getAuthorities()
         .stream()
         .filter(authority -> authority.getAuthority().startsWith(PRIVILEGE_STRING_PREFIX))
         .map(authority -> convertPrivilegeFromAuthotityName(authority.getAuthority()))
         .collect(Collectors.toList());
     }

    private Privilege convertPrivilegeFromAuthotityName(String authorityName){
        String privilegeStr = authorityName.substring(PRIVILEGE_STRING_PREFIX.length());
        if(privilegeStr != null){
            String[] split = privilegeStr.split(PRIVILEGE_FIELDS_SEPARATOR);
            if(split != null && split.length >0){
                Privilege privilege = new Privilege();
                for (int i = 0; i < split.length; i++) {
                    if(i == 0) privilege.setId(Long.valueOf(split[i]));
                    else if(i == 1) privilege.setAuthority(split[i]);
                    else if(i == 2) privilege.setEntity(split[i]);
                    else if(i == 3) privilege.setAction(split[i]);
                    else privilege.setConstrained(Boolean.valueOf(split[i]));
                }
                return privilege;
            }
        }
        return null;
    }
}
