package com.typicode.builder;

import com.typicode.constants.URL;
import com.typicode.utils.Util;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

/**
 * Sends HTTP requests to the API with REST Assured and wraps each response in a ResponseBuilder.
 * The managers extend this class and call getApiConnection().get(...) etc.
 * Note: the request specification is static and RestAssured.baseURI is global, so this is not thread-safe.
 */
public class RequestBuilder {

	// Request specification for the next request (recreated by getApiConnection)
	private static RequestSpecification apiConnection;
	// Shared instance returned by getApiConnection
	private static RequestBuilder requestBuilder = new RequestBuilder();

	/**
     * Get the instance of class, with a fresh request specification for the base URL
     * @return requestBuilder  the class object.
     */
	public static RequestBuilder getApiConnection() {
		RestAssured.baseURI = URL.BASE_URL;
		apiConnection = RestAssured.given();
		return requestBuilder;
	}
	
	/**
     * Constructs a GET Request using provided URI.
     * @param  uri       the path of the request, relative to the base URL.
     * @return response  the response of HTTP request.
     */
	public ResponseBuilder get(String uri) {
		Util.logInfoMessage("Base url : " + URL.BASE_URL + ", URI : " + uri);
		return new ResponseBuilder(apiConnection.get(uri));
	}

	/**
     * Constructs a POST Request using provided URI.
     * @param  uri       the path of the request, relative to the base URL.
     * @param  data      the request body (JSON).
     * @return response  the response of HTTP request.
     */
	public ResponseBuilder post(String uri, String data) {
		Util.logInfoMessage("Base url : " + URL.BASE_URL + ", URI : " + uri);
		return new ResponseBuilder(apiConnection.body(data).post(uri));
	}
	
	/**
     * Constructs a PUT Request using provided URI.
     * @param  uri       the path of the request, relative to the base URL.
     * @param  data      the request body (JSON).
     * @return response  the response of HTTP request.
     */
	public ResponseBuilder put(String uri, String data) {
		Util.logInfoMessage("Base url : " + URL.BASE_URL + ", URI : " + uri);
		return new ResponseBuilder(apiConnection.body(data).put(uri));
	}
	
	/**
     * Constructs a PATCH Request using provided URI.
     * @param  uri       the path of the request, relative to the base URL.
     * @param  data      the request body (JSON).
     * @return response  the response of HTTP request.
     */
	public ResponseBuilder patch(String uri, String data) {
		Util.logInfoMessage("Base url : " + URL.BASE_URL + ", URI : " + uri);
		return new ResponseBuilder(apiConnection.body(data).patch(uri));
	}
	
	/**
     * Constructs a DELETE Request using provided URI.
     * @param  uri       the path of the request, relative to the base URL.
     * @return response  the response of HTTP request.
     */
	public ResponseBuilder delete(String uri) {
		Util.logInfoMessage("Base url : " + URL.BASE_URL + ", URI : " + uri);
		return new ResponseBuilder(apiConnection.delete(uri));
	}
	
}
