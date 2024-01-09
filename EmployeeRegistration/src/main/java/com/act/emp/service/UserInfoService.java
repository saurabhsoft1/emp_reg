package com.act.emp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.act.emp.entity.UserInfo;
import com.act.emp.repository.UserInfoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

	@Autowired
	private UserInfoRepository repository;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<UserInfo> userDetail = repository.findByName(username);

		// Converting userDetail to UserDetails
		return userDetail.map(UserInfoDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
	}

    public String addUser(UserInfo userInfo) { 
        userInfo.setPassword(encoder.encode(userInfo.getPassword())); 
        repository.save(userInfo); 
        return "User Added Successfully"; 
    } 

	public List<UserInfo> getAllUsers() {
		return repository.findAll();
	}

	public UserInfo getUserById(int id) {
		return repository.findById(id).orElse(null);
	}

	public UserInfo updateUser(int id, UserInfo updatedUser) {
		if (repository.existsById(id)) {
			updatedUser.setId(id);
			updatedUser.setPassword(encoder.encode(updatedUser.getPassword())); 
			return repository.save(updatedUser);
		} else {
			return null;
		}
	}

	public boolean deleteUser(int id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

}
