package com.typicode.builder;

import com.typicode.constants.URL;
import com.typicode.utils.Util;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class RequestBuilder {
	
	private static RequestSpecification apiConnection;
	private static RequestBuilder requestBuilder = new RequestBuilder();
	
	/**
     * Get the instance of class 
     * @return requestBuilder  the class object.
     */
	public static RequestBuilder getApiConnection() {
		RestAssured.baseURI = URL.BASE_URL;
		apiConnection = RestAssured.given();
		return requestBuilder;
	}
	
	/**
     * Constructs a GET Request using provided URI.
     * @param  url       the URL of the request.
     * @return response  the response of HTTP request.
     */
	public ResponseBuilder get(String uri) {
		Util.logInfoMessage("Base url : " + URL.BASE_URL + ", URI : " + uri);
		return new ResponseBuilder(apiConnection.get(uri));
	}

	/**
     * Constructs a POST Request using provided URI.
     * @param  url       the URL of the request.
     * @return response  the response of HTTP request.
     */
	public ResponseBuilder post(String uri, String data) {
		Util.logInfoMessage("Base url : " + URL.BASE_URL + ", URI : " + uri);
		return new ResponseBuilder(apiConnection.body(data).post(uri));
	}
	
	/**
     * Constructs a PUT Request using provided URI.
     * @param  url       the URL of the request.
     * @return response  the response of HTTP request.
     */
	public ResponseBuilder put(String uri, String data) {
		Util.logInfoMessage("Base url : " + URL.BASE_URL + ", URI : " + uri);
		return new ResponseBuilder(apiConnection.body(data).put(uri));
	}
	
	/**
     * Constructs a PATCH Request using provided URI.
     * @param  url       the URL of the request.
     * @return response  the response of HTTP request.
     */
	public ResponseBuilder patch(String uri, String data) {
		Util.logInfoMessage("Base url : " + URL.BASE_URL + ", URI : " + uri);
		return new ResponseBuilder(apiConnection.body(data).patch(uri));
	}
	
	/**
     * Constructs a DELETE Request using provided URI.
     * @param  url       the URL of the request.
     * @return response  the response of HTTP request.
     */
	public ResponseBuilder delete(String uri) {
		Util.logInfoMessage("Base url : " + URL.BASE_URL + ", URI : " + uri);
		return new ResponseBuilder(apiConnection.delete(uri));
	}
	
}
