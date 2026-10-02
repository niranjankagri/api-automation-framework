package com.typicode.tests;

import java.util.List;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.typicode.manager.Manager;
import com.typicode.response.Comment;
import com.typicode.response.Post;
import com.typicode.response.User;
import com.typicode.testdata.BaseTest;
import com.typicode.utils.Util;

import io.qameta.allure.Description;

/**
 * API tests for JSONPlaceholder (https://jsonplaceholder.typicode.com): users, posts and comments.
 * The username comes from src/resources/data.properties.
 */
public class TypicodeTest extends BaseTest {
	
	/**
	 * GET /users returns at least one user
	 */
	@Test (description="Validating get all users endpoint")
	@Description("Test Description: Validating get all users endpoint")
	public void validateGetAllUsersEndpoint() {
		List<User> usersList = Manager.getUserManager().getAllUsers();
		Assert.assertNotEquals(usersList.size(), 0);
	}
	
	/**
	 * GET /users?username= finds the user from data.properties, with every field filled
	 */
	@Test (description="Validating get user by username endpoint")
	@Description("Test Description: Validating get user by username endpoint")
	public void validateGetUserByUsernameEndpoint() {
		User user = Manager.getUserManager().getUserByUserName(getUsername());
		Assert.assertEquals(user.getUsername(), getUsername());
		Assert.assertNotNull(user.getId());
		Assert.assertNotNull(user.getName());
		Assert.assertNotNull(user.getEmail());
		Assert.assertNotNull(user.getAddress());
		Assert.assertNotNull(user.getPhone());
		Assert.assertNotNull(user.getWebsite());
		Assert.assertNotNull(user.getCompany());
	}
	
	/**
	 * GET /posts returns at least one post
	 */
	@Test (description="Validating get all posts endpoint")
	@Description("Test Description: Validating get all posts endpoint")
	public void validateGetAllPostsEndpoint() {
		List<Post> postsList = Manager.getPostManager().getAllPosts();
		Assert.assertNotEquals(postsList.size(), 0);
	}
	
	/**
	 * GET /posts?userId= returns the posts of the searched user
	 */
	@Test (description="Validating get all posts of user by userid endpoint")
	@Description("Test Description: Validating get all posts of user by userid endpoint")
	public void validateGetAllPostsOfUserByUserIdEndpoint() {
		User user = Manager.getUserManager().getUserByUserName(getUsername());
		List<Post> userPosts = Manager.getPostManager().getAllPostsOfUserByUserId(user.getId());
		Assert.assertNotEquals(userPosts.size(), 0);
	}
	
	/**
	 * GET /comments returns at least one comment
	 */
	@Test (description="Validating get all comments endpoint")
	@Description("Test Description: Validating get all comments endpoint")
	public void validateGetAllCommentsEndpoint() {
		List<Comment> commentsOnPosts = Manager.getCommentManager().getAllComments();
		Assert.assertNotEquals(commentsOnPosts.size(), 0);
	}
	
	/**
	 * GET /comments?postId= returns comments for the posts of the searched user
	 */
	@Test (description="Validating get all comments on post by postid endpoint")
	@Description("Test Description: Validating get all comments on post by postid endpoint")
	public void validateGetAllCommentsOnPostByPostIdEndpoint() {
		User user = Manager.getUserManager().getUserByUserName(getUsername());
		List<Post> userPosts = Manager.getPostManager().getAllPostsOfUserByUserId(user.getId());
		List<Comment> commentsOnPosts = Manager.getCommentManager().getAllCommentsOnPostsByPostId(userPosts);
		Assert.assertNotEquals(commentsOnPosts.size(), 0);
	}

	/**
	 * Main scenario: user by username -> their posts -> all comments on those posts;
	 * every comment email must be valid (soft asserts, so all invalid emails are reported)
	 */
	@Test (description="Validating emails in the comment section are in the proper format")
	@Description("Test Description: Validating emails in the comment section are in the proper format")
	public void validateEmailFormatOnComments() {
		SoftAssert softAssert = new SoftAssert();
		
		User user = Manager.getUserManager().getUserByUserName(getUsername());
		List<Post> userPosts = Manager.getPostManager().getAllPostsOfUserByUserId(user.getId());
		List<Comment> commentsOnPosts = Manager.getCommentManager().getAllCommentsOnPostsByPostId(userPosts);
		Manager.getCommentManager().validateEmailInAllComments(softAssert, commentsOnPosts);
		
		softAssert.assertAll();
	}
	
	/**
	 * POST /posts creates a post with random title and body; the response contains the new id
	 */
	@Test (description="Validating create post endpoint")
	@Description("Test Description: Validating create post endpoint")
	public void validateCreatePostEndpoint() {
		User user = Manager.getUserManager().getUserByUserName(getUsername());
		
		// Request body for the new post
		JSONObject post = new JSONObject();
		post.put("userId", user.getId());
		post.put("title", Util.getRandomWord());
		post.put("body", Util.getRandomWord());
		
		String responseBody = Manager.getPostManager().createPost(post.toString());
		
		// The created post comes back with an id
		post = new JSONObject(responseBody);
		Assert.assertNotNull(post.get("id"));
	}
	
	/**
	 * PUT /posts/1 replaces post 1 with a new random title and body
	 */
	@Test (description="Validating update post by put method")
	@Description("Test Description: Validating update post by put method")
	public void validateUpdatePostbyPutMethod() {
		String postTitle = Util.getRandomWord();
		String postBody = Util.getRandomWord();
		
		// Read post 1 and change its title and body
		// Note: Post has no toString(), so post.toString() below sends "com.typicode.response.Post@..." instead of JSON;
		// JSONPlaceholder still answers 200
		Post post = Manager.getPostManager().getPostByPostId(1);
		post.setTitle(postTitle);
		post.setBody(postBody);
		
		post = Manager.getPostManager().updatePostByPut(post.toString(), 1);
		
		Assert.assertEquals(post.getId(), 1);
	}
	
	/**
	 * PATCH /posts/1 changes only the title and body of post 1
	 */
	@Test (description="Validating update post by patch method")
	@Description("Test Description: Validating update post by patch method")
	public void validateUpdatePostbyPatchMethod() {
		String postTitle = Util.getRandomWord();
		String postBody = Util.getRandomWord();
		
		// Only the fields to change
		JSONObject body = new JSONObject();
		body.put("title", postTitle);
		body.put("body", postBody);
		
		Post post = Manager.getPostManager().updatePostByPatch(body.toString(), 1);
		
		Assert.assertEquals(post.getId(), 1);
	}
	
	/**
	 * DELETE /posts/1 returns 200 (the status check is in PostManager)
	 */
	@Test (description="Validating delete post endpoint")
	@Description("Test Description: Validating delete post endpoint")
	public void validateDeletePostEndpoint() {
		
		Manager.getPostManager().deletePostOfUser(1);
		
	}
	
	/**
	 * Demo test that always passes, to show the passed layout in the reports
	 */
	@Test (description="Checking pass test layout")
	@Description("Test Description: Pass test layout")
	public void passTest(){
		Assert.assertTrue(true);
	}
	
	/**
	 * Demo test that always fails, to show the failed layout in the reports
	 */
	@Test (description="Checking fail test layout")
	@Description("Test Description: Fail test layout")
	public void failTest(){
		Assert.assertTrue(false);
	}
	
	/**
	 * Demo test that is always skipped, to show the skipped layout in the reports
	 */
	@Test (description="Checking skip test layout")
	@Description("Test Description: Skip test layout")
	public void skipTest(){
		throw new SkipException("Skipping - This is not ready for testing ");
	}
}
