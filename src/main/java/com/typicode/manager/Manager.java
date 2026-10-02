package com.typicode.manager;

/**
 * Entry point for the tests: gives access to one shared manager per API resource.
 * Tests call e.g. Manager.getUserManager().getAllUsers() and never send requests themselves.
 */
public abstract class Manager {

	// One shared instance per resource
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
