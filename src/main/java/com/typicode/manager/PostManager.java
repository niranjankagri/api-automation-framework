package com.typicode.manager;

import java.util.List;

import org.testng.Assert;

import com.typicode.builder.RequestBuilder;
import com.typicode.builder.ResponseBuilder;
import com.typicode.constants.StatusCode;
import com.typicode.constants.URL;
import com.typicode.response.Post;
import com.typicode.utils.Util;

import io.qameta.allure.Step;

/**
 * Requests for the /posts resource: read, create, update and delete posts.
 * Each method checks the status code and maps the JSON onto Post objects.
 * JSONPlaceholder fakes the write requests: they return a realistic response but nothing is stored.
 */
public class PostManager extends RequestBuilder {

	/**
     * Get the list of all posts
     * @return List<Post>   the list of Post class object
     */
	@SuppressWarnings("unchecked")
	@Step("Get all posts and validate response code")
	public List<Post> getAllPosts() {
		ResponseBuilder response = getApiConnection().get(URL.POSTS);
		Util.logInfoMessage("Actual Status Code : " + response.getStatusCode() + ", Expected Status Code :  : " + StatusCode.OK);
		Assert.assertEquals(response.getStatusCode(), StatusCode.OK, "Response code should be 200");
		return  (List<Post>) response.getResponseAsObjectList(new Post[]{});
	}
	
	/**
     * Get the post by postId
     * @param  postId   the id of the post.
     * @return Post     the Post class object.
     */
	@Step("Get all posts and validate response code")
	public Post getPostByPostId(int postId) {
		ResponseBuilder response = getApiConnection().get(URL.POSTS + "/" + postId);
		Util.logInfoMessage("Actual Status Code : " + response.getStatusCode() + ", Expected Status Code :  : " + StatusCode.OK);
		Assert.assertEquals(response.getStatusCode(), StatusCode.OK, "Response code should be 200");
		return  (Post) response.getResponseAsObject(Post.class);
	}
	
	/**
     * Get the list of all posts written by a user
     * @param  userId       the id of the user.
     * @return List<Post>   the list of Post class object
     */
	@SuppressWarnings("unchecked")
	@Step("Retrieves all the posts created by user with userId : {0}, validate response code and get posts")
	public List<Post> getAllPostsOfUserByUserId(int userId) {
		ResponseBuilder response = getApiConnection().get(URL.POSTS_BY_USERID + userId);
		Util.logInfoMessage("Actual Status Code : " + response.getStatusCode() + ", Expected Status Code :  : " + StatusCode.OK);
		Assert.assertEquals(response.getStatusCode(), StatusCode.OK, "Response code should be 200");
		return  (List<Post>) response.getResponseAsObjectList(new Post[]{});
	}
	
	/**
     * Create post of user
     * @param  post     the new post as JSON (userId, title, body).
     * @return String   the response body: the created post including its new id.
     */
	@Step("Create post and validate response code")
	public String createPost(String post) {
		ResponseBuilder response = getApiConnection().post(URL.POSTS, post);
		Util.logInfoMessage("Actual Status Code : " + response.getStatusCode() + ", Expected Status Code :  : " + StatusCode.CREATED);
		Assert.assertEquals(response.getStatusCode(), StatusCode.CREATED, "Response code should be 201");
		return  response.getBody();
	}
	
	/**
     * Update post by put method (replaces the whole post)
     * @param  post     the complete post as JSON.
     * @param  postId   the id of the post to update.
     * @return Post     the updated post.
     */
	@Step("Update post by put and validate response code")
	public Post updatePostByPut(String post, int postId) {
		ResponseBuilder response = getApiConnection().put(URL.POSTS + "/" + postId, post);
		Util.logInfoMessage("Actual Status Code : " + response.getStatusCode() + ", Expected Status Code :  : " + StatusCode.OK);
		Assert.assertEquals(response.getStatusCode(), StatusCode.OK, "Response code should be 200");
		return  (Post) response.getResponseAsObject(Post.class);
	}
	
	/**
     * Update post by patch method (changes only the given fields)
     * @param  post     the fields to change as JSON.
     * @param  postId   the id of the post to update.
     * @return Post     the updated post.
     */
	@Step("Update post by patch and validate response code")
	public Post updatePostByPatch(String post, int postId) {
		ResponseBuilder response = getApiConnection().patch(URL.POSTS + "/" + postId, post);
		Util.logInfoMessage("Actual Status Code : " + response.getStatusCode() + ", Expected Status Code :  : " + StatusCode.OK);
		Assert.assertEquals(response.getStatusCode(), StatusCode.OK, "Response code should be 200");
		return  (Post) response.getResponseAsObject(Post.class);
	}
	
	/**
     * Delete post of user
     * @param  postId   the id of the post to delete.
     */
	@Step("Delete post and validate response code")
	public void deletePostOfUser(int postId) {
		ResponseBuilder response = getApiConnection().delete(URL.POSTS + "/" + postId);
		Util.logInfoMessage("Actual Status Code : " + response.getStatusCode() + ", Expected Status Code :  : " + StatusCode.OK);
		Assert.assertEquals(response.getStatusCode(), StatusCode.OK, "Response code should be 200");
	}
	
}
