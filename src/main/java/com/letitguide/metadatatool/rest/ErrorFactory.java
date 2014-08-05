package com.letitguide.metadatatool.rest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.server.impl.cdi.Utils;

import flexjson.JSONSerializer;

public class ErrorFactory {
    public static enum Error {
    	// 1XX - GENERAL ERRORS
        MISSING_FIELDS(100, "Request has missing fields", Response.Status.BAD_REQUEST),
        
        // 2XX - AUTHENTICATION ERRORS
        USER_NOT_FOUND(200, "User not found", Response.Status.NOT_FOUND),
        AUTHENTICATION_FAILED(201, "Authentication failed", Response.Status.FORBIDDEN),
        MAIL_ERROR(202, "There was an error with the mail service", Response.Status.BAD_REQUEST),
        RESET_TOKEN_ERROR(203, "The reset token is not valid", Response.Status.FORBIDDEN)
        
        ;
        private int errorCode;
        private String reasonPhrase;
        private Response.Status responseStatus;

        private Error(int errorCode, String reasonPhrase, Response.Status responseStatus) {
            this.errorCode = errorCode;
            this.reasonPhrase = reasonPhrase;
            this.responseStatus = responseStatus;
        }

        public int getCode() {
            return this.errorCode;
        }

        public String getReasonPhrase() {
            return this.reasonPhrase;
        }

        private Response.Status getResponseStatus() {
            return this.responseStatus;
        }
    }
    
    public static Response getError(final Error error) {
    	Map map = Collections.singletonMap("error", new HashMap() {
            {
                put("message", error.getReasonPhrase());
                put("code", error.getCode());
            }
        });

        Response.ResponseBuilder builder = Response.status(error.getResponseStatus());
        return builder.type(MediaType.APPLICATION_JSON).entity(new JSONSerializer().prettyPrint(true).serialize(map)).build();
    }
}
