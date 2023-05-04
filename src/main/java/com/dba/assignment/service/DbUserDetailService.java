package com.dba.assignment.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dba.assignment.entity.Role;
import com.dba.assignment.entity.UserCredentials;
import com.dba.assignment.repository.UserCredentialsRepository;

@Service
public class DbUserDetailService implements UserDetailsService{
	@Autowired
	UserCredentialsRepository userCredentialsRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserCredentials userCredentials = userCredentialsRepository.findUserByUserName(username);

        if (userCredentials != null) {
        	java.util.Set<Role> roles = userCredentials.getEmp().getRoles();
            return new org.springframework.security.core.userdetails.User(userCredentials.getUserName(),
            		userCredentials.getPassword(),
                    mapRolesToAuthorities(roles));
            
        }else{
            throw new UsernameNotFoundException("Invalid username or password.");
        }
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		Collection<? extends GrantedAuthority> mapRoles = roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		return mapRoles;
	}

}
