package com.typicode.constants;

/**
 * Base URL of the API under test and the endpoint paths used by the managers.
 * Paths ending in "=" expect the query value to be appended, e.g. USERS_BY_USERNAME + "Samantha".
 */
public class URL {

	// JSONPlaceholder, a free fake REST API
	public static final String BASE_URL = "https://jsonplaceholder.typicode.com";
	// All posts; append "/{id}" for one post
	public static final String POSTS = "/posts";
	// All comments
	public static final String COMMENTS = "/comments";
	// All users
	public static final String USERS = "/users";
	// Users filtered by username
	public static final String USERS_BY_USERNAME = "/users?username=";
	// Posts filtered by the id of the user who wrote them
	public static final String POSTS_BY_USERID = "/posts?userId=";
	// Comments filtered by the id of the post they belong to
	public static final String COMMENTS_BY_POSTID = "/comments?postId=";

}
