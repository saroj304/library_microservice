package com.library.library_microservice.constants;

import org.springframework.stereotype.Component;

@Component
public class Constants {
    private Constants() {

    }
    public static final String  SAVINGS = "Savings";
    public static final String  ADDRESS = "123 Main Street, New York";
    public static final String  STATUS_201 = "201";
    public static final String  MESSAGE_201 = "created successfully";
    public static final String  STATUS_200 = "200";
    public static final String  MESSAGE_200 = "Request processed successfully";
    public static final String STATUS_400 = "400";
    public static final String MESSAGE_400_UPDATE = "Update operation failed";
    public static final String MESSAGE_400_DELETE = "Delete operation failed.";
   public static final String  STATUS_404 = "404";
   public static final String  MESSAGE_404 = "Requested resource could not be found on the server";
    public static final String  STATUS_417 = "417";
    public static final String  MESSAGE_417_UPDATE= "Update operation failed. Please try again or contact Dev team";
    public static final String  MESSAGE_417_DELETE= "Delete operation failed. Please try again or contact Dev team";
    // public static final String  STATUS_500 = "500";

}
