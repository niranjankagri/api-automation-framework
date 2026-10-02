package com.typicode.response;

/**
 * A comment from GET /comments, mapped from JSON by ResponseBuilder.
 */
public class Comment {

	// Id of the comment
	private int id;
	// Id of the post the comment belongs to
	private int postId;
	// Title of the comment
	private String name;
	// Email address of the commenter (checked by the email format test)
	private String email;
	// Text of the comment
	private String body;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
