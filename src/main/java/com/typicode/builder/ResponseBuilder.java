package com.typicode.builder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

public class ResponseBuilder {
	
	private Response response;
	
	public ResponseBuilder(Response response) {
		setResponse(response);
	}
	
	public Response getResponse() {
		return response;
	}

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
     * Get body from HTTP_Response
     * @return String       the body.
     */
    public String getBody() {
        return getResponse().getBody().prettyPrint();
    }
	
    /**
     * Converting HTTP_Response into respective class object
     * @param responseClass    the corresponding class
     * @return Object          the class object.
     */
    public Object getResponseAsObject(Class<?> responseClass) {
        Object object = null;
    	ObjectMapper mapper = new ObjectMapper();
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
     * Converting HTTP_Response into respective class object
     * @param responseObject    the corresponding class
     * @return List<?>          the list of object.
     */
    public List<?> getResponseAsObjectList(Object[] responseObject) {
    	Object[] object = null;
    	ObjectMapper mapper = new ObjectMapper();
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
