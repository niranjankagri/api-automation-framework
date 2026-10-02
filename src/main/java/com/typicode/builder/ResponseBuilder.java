package com.typicode.builder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

/**
 * Wraps a REST Assured response: gives the status code and body, and maps the JSON onto response POJOs.
 */
public class ResponseBuilder {

	// The raw REST Assured response
	private Response response;

	/**
     * Constructor: Wrap the response of a request
     * @param  response  the REST Assured response.
     */
	public ResponseBuilder(Response response) {
		setResponse(response);
	}

	/**
     * Get the raw REST Assured response
     * @return Response  the response.
     */
	public Response getResponse() {
		return response;
	}

	/**
     * Set the raw REST Assured response
     * @param  response  the response.
     */
	public void setResponse(Response response) {
		this.response = response;
	}

	/**
     * Get status code from HTTP_Response
     * @return int       the status code.
     */
    public int getStatusCode() {
        return getResponse().getStatusCode();
    }
    
    /**
     * Get body from HTTP_Response (also pretty-prints it to the console)
     * @return String       the body.
     */
    public String getBody() {
        return getResponse().getBody().prettyPrint();
    }
	
    /**
     * Converting HTTP_Response into respective class object
     * JSON fields without a matching POJO field are ignored.
     * @param responseClass    the corresponding class
     * @return Object          the class object, or null if the JSON could not be mapped.
     */
    public Object getResponseAsObject(Class<?> responseClass) {
        Object object = null;
    	ObjectMapper mapper = new ObjectMapper();
    	// Ignore JSON fields that the POJO does not have
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
			object = mapper.readValue(getResponse().asString(), responseClass);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        return object;
    }
    
    /**
     * Converting a JSON array HTTP_Response into a list of class objects
     * Pass an empty array of the element type, e.g. new User[]{}, so the mapper knows what to create.
     * @param responseObject    an empty array of the element type
     * @return List<?>          the list of object.
     */
    public List<?> getResponseAsObjectList(Object[] responseObject) {
    	Object[] object = null;
    	ObjectMapper mapper = new ObjectMapper();
    	// Ignore JSON fields that the POJO does not have
    	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
			object = mapper.readValue(getResponse().asString(), responseObject.getClass());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Arrays.asList(object);
    }
}
