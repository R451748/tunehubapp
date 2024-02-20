package com.example.demo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entites.Songs;
import com.example.demo.entites.Users;
import com.example.demo.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Controller
public class UsersController {
	@Autowired
	UserService userv; 
	@PostMapping("/register")
    public String addUser(@ModelAttribute Users user) 
	{ 
		boolean userstatus=userv.emailExists(user.getEmail());
	  if(userstatus== false) {
	  userv.addUser(user);
	  return "registersuccess";
	  }
	  else {
		  return "registerfail";
	  }
    }
	@PostMapping("/login")
    public String validateUser(@RequestParam String email,@RequestParam String password,HttpSession session){
	    if(userv.validateUser(email, password) == true) {
	    session.setAttribute("email",email);
	    if(userv.getRole(email).equals("admin")) {
    	return"adminhome";
	    }
	    else {
    	return"customerhome";
    }
	    }
	    else {
	    	return "loginfail";
	    }
	}
	
	@GetMapping("/exploreSongs")
	public String exploreSongs(Model model,HttpSession session) {
		String email=(String) session.getAttribute("email");
		Users user = userv.getUser(email);
		boolean userStatus=user.isPremium();
		if(userStatus == true) {
			 List<Songs> songslist = userv.fetchAllSongs();// fetch the list of songs from your service, e.g., userv.getSongsList();
		     model.addAttribute("songslist", songslist);
			return "displaysongs";
		}
		else {
			return "pay";
		}
	}
}
