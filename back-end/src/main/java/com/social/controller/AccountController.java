package com.social.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.social.services.UserService;
import com.social.util.CustomErrorType;
import com.social.entities.User;
/** 
 * @author kamal berriga
 *
 */
@RestController
@RequestMapping("account")
public class AccountController {

	public static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private UserService userService;

	// request method to create a new account by a guest
	@CrossOrigin
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User newUser) {
		if (userService.find(newUser.getUsername()) != null) {
			logger.error("username Already exist " + newUser.getUsername());
			return new ResponseEntity(
					new CustomErrorType("user with username " + newUser.getUsername() + "already exist "),
					HttpStatus.CONFLICT);
		}
		newUser.setRole("USER");
		
		return new ResponseEntity<User>(userService.save(newUser), HttpStatus.CREATED);
	}

	// this is the login api/service
	@CrossOrigin
	@RequestMapping("/login")
	public Principal user(Principal principal) {
		logger.info("user logged "+principal);
		return principal;
	}
	
	@RequestMapping(value="/getPincodeDetails",method=RequestMethod.POST)
	public ResponseEntity<?>getPicodeDetails(@RequestBody String pincode){
		String result=null;
		try{
			RestTemplate rest=new RestTemplate();
			result=rest.getForObject("http://postalpincode.in/api/pincode/"+pincode, String.class);
			logger.info("PIN Code result is : " + result);
			return new ResponseEntity<String>(result,HttpStatus.OK);
		}catch(Exception ex){
			return new ResponseEntity<String>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	
}
