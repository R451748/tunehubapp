package com.example.demo.services;

import java.util.List;

import com.example.demo.entites.Songs;
import com.example.demo.entites.Users;

public interface UserService {

	public String addUser(Users user);
    
	public boolean emailExists(String email);

	boolean validateUser(String email, String password);

	public String getRole(String email);

	public Users getUser(String email);

	public void updateUser(Users user);

	public List<Songs> fetchAllSongs();
}
