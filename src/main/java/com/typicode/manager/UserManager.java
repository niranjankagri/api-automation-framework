package com.typicode.manager;

import java.util.List;
import org.testng.Assert;

import com.typicode.builder.RequestBuilder;
import com.typicode.builder.ResponseBuilder;
import com.typicode.constants.StatusCode;
import com.typicode.constants.URL;
import com.typicode.response.User;
import com.typicode.utils.Util;

import io.qameta.allure.Step;

/**
 * Requests for the /users resource. Each method checks the status code and maps the JSON onto User objects.
 */
public class UserManager extends RequestBuilder {

	/**
     * Get the detail of all users
     * @return List<User>   the list of User class object.
     */
	@SuppressWarnings("unchecked")
	@Step("Get all users and validate response code")
	public List<User> getAllUsers() {
		ResponseBuilder response = getApiConnection().get(URL.USERS);
		Util.logInfoMessage("Actual Status Code : " + response.getStatusCode() + ", Expected Status Code :  : " + StatusCode.OK);
		Assert.assertEquals(response.getStatusCode(), StatusCode.OK, "Response code should be 200");
		return (List<User>) response.getResponseAsObjectList(new User[]{});
	}
	
	/**
     * Get the detail of user by username
     * Fails the test if not exactly one user has this username.
     * @param username   the username of user.
     * @return User      the User class object.
     */
	@SuppressWarnings("unchecked")
	@Step("Search for user : {0}, validate response code and duplicacy of user")
	public User getUserByUserName(String username) {
		ResponseBuilder response = getApiConnection().get(URL.USERS_BY_USERNAME + username);
		Util.logInfoMessage("Actual Status Code : " + response.getStatusCode() + ", Expected Status Code :  : " + StatusCode.OK);
		Assert.assertEquals(response.getStatusCode(), StatusCode.OK, "Response code should be 200");
		
		List<User> usersList = (List<User>) response.getResponseAsObjectList(new User[]{});
		// Usernames are unique, so the search must return exactly one user
		Util.logInfoMessage("Username should not be duplicate : " + usersList.size());
		Assert.assertEquals(usersList.size(), 1);
		
		return usersList.get(0);
	}
	
}
