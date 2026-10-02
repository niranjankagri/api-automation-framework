package com.typicode.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.typicode.builder.RequestBuilder;
import com.typicode.builder.ResponseBuilder;
import com.typicode.constants.StatusCode;
import com.typicode.constants.URL;
import com.typicode.response.Comment;
import com.typicode.response.Post;
import com.typicode.utils.Util;

import io.qameta.allure.Step;

/**
 * Requests for the /comments resource, plus the email format check on comments.
 */
public class CommentManager extends RequestBuilder {

	/**
     * Get the list of all comments on all posts
     * @return List<Comment>   the list of Comment class object
     */
	@SuppressWarnings("unchecked")
	@Step("Get all comments on post")
	public List<Comment> getAllComments() {
		ResponseBuilder response = getApiConnection().get(URL.COMMENTS);
		Util.logInfoMessage("Actual Status Code : " + response.getStatusCode() + ", Expected Status Code :  : " + StatusCode.OK);
		Assert.assertEquals(response.getStatusCode(), StatusCode.OK, "Response code should be 200");
		return  (List<Comment>) response.getResponseAsObjectList(new Comment[]{});
	}
	
	/**
     * Get the list of all comments done on a particular post
     * @param  postId          the id of the post.
     * @return List<Comment>   the list of Comment class object
     */
	@SuppressWarnings("unchecked")
	@Step("Retrieves all the comments on post with postId: {0} created by user, validate response code and get comments")
	public List<Comment> getAllCommentsOnPostByPostId(int postId) {
		ResponseBuilder response = getApiConnection().get(URL.COMMENTS_BY_POSTID + postId);
		Util.logInfoMessage("Actual Status Code : " + response.getStatusCode() + ", Expected Status Code :  : " + StatusCode.OK);
		Assert.assertEquals(response.getStatusCode(), StatusCode.OK, "Response code should be 200");
		return  (List<Comment>) response.getResponseAsObjectList(new Comment[]{});
	}
	
	/**
     * Get the list of all comments done on posts (one request per post)
     * @param  userPosts       the list of Post class object
     * @return List<Comment>   the comments of all the posts, in one list
     */
	public List<Comment> getAllCommentsOnPostsByPostId(List<Post> userPosts) {
		List<Comment> listOfCommentsOnPosts =  new ArrayList<Comment>();
		for (Iterator<Post> iterator = userPosts.iterator(); iterator.hasNext();) {
			Post post = (Post) iterator.next();
			listOfCommentsOnPosts.addAll(getAllCommentsOnPostByPostId(post.getId()));
		}
		return listOfCommentsOnPosts;
	}
	
	/**
     * Validate email format on all comments
     * Uses soft asserts, so every invalid email is collected; call softAssert.assertAll() afterwards.
     * @param   softAssert       the soft assert that collects the results
     * @param   commentsOnPosts  the list of Comment class object
     * @return  SoftAssert       the assert value of all emails.
     */
	@Step("Validate emails in the comment section are in the proper format")
	public SoftAssert validateEmailInAllComments(SoftAssert softAssert, List<Comment> commentsOnPosts) {
		boolean validateEmailFormat = false;
		for (Iterator<Comment> iterator = commentsOnPosts.iterator(); iterator.hasNext();) {
			Comment comment = (Comment) iterator.next();
			validateEmailFormat = Util.isStringPresentWithoutCase(Util.EMAIL_REGEX, comment.getEmail());
			Util.logInfoMessage("Is email in correct format : " + comment.getEmail() + " : " + validateEmailFormat);
			softAssert.assertTrue(validateEmailFormat);
		}
		return softAssert;
	}
}
