package com.typicode.manager;

public abstract class Manager {

	private static UserManager userManager = new UserManager();
	private static CommentManager commentManager = new CommentManager();
	private static PostManager postManager = new PostManager();

	/**
	 * Get the instance of class
	 * 
	 * @return userManager the class object.
	 */
	public static UserManager getUserManager() {
		return userManager;
	}
	
	/**
     * Get the instance of class 
     * @return commentManager  the class object.
     */
	public static CommentManager getCommentManager() {
		return commentManager;
	}

	/**
     * Get the instance of class 
     * @return postManager  the class object.
     */
	public static PostManager getPostManager() {
		return postManager;
	}

}
