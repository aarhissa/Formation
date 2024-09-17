package com.gkemayo.library.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gkemayo.library.customer.Customer;
import com.gkemayo.library.customer.CustomerDTO;
import com.gkemayo.library.customer.CustomerServiceImpl;

public class LibraryUserDetailsService implements UserDetailsService{

	@Autowired
	private CustomerServiceImpl customerService;
	


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Customer existingCustomer = customerService.findCustomerByEmail(username);
		if (existingCustomer == null) {
			throw new UsernameNotFoundException("No user found");
		}
		
		
		return new LibraryUserDetails (existingCustomer);
                
		
	}

}
