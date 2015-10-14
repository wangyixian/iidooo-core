package com.iidooo.core.constant;

public class SessionConstant {

    public static final String COOKIE_ENCODING_UTF8 = "UTF-8";

    // The list of security resource list session key
    public static final String RESOURCE_LIST = "RESOURCE_LIST";

    // Key: ResourceURL
    // Value: ResourceDto
    public static final String RESOURCE_URL_MAP = "RESOURCE_URL_MAP";

    // Key: ResourceID
    // Value: ResourceDto
    public static final String RESOURCE_ID_MAP = "RESOURCE_ID_MAP";

    // Key: ResourceURL
    // Value: ResourceDto
    public static final String ROOT_RESOURCE_URL_MAP = "ROOT_RESOURCE_URL_MAP";

    // The current active resource
    public static final String CURRENT_RESOURCE = "CURRENT_RESOURCE";
    // The current selected item of security resource session key
    public static final String CURRENT_ROOT_RESOURCE = "CURRENT_ROOT_RESOURCE";

    public static final String LOGIN_USER = "LOGIN_USER";

    public static final String LOGIN_ROLE_LIST = "LOGIN_ROLE_LIST";

    public static final String LOGIN_RESOURCE_LIST = "LOGIN_RESOURCE_LIST";
    public static final String LOGIN_RESOURCE_URL_LIST = "LOGIN_RESOURCE_URL_LIST";
    
    // The login id of session and cookies key.
    public static final String LOGIN_ID = "LOGIN_ID";

    // The user id of session and cookies key.
    public static final String USER_ID = "USER_ID";

    // The user name of session and cookie key.
    public static final String USER_NAME = "USER_NAME";
    
    // The SSO URL of session key.
    // If the APP should login, should redirect to this SSO URL
    public static final String PASSPORT_URL = "PASSPORT_URL";

    // The access URL of login success should be redirected.
    public static final String ACCESS_URL = "ACCESS_URL";
    
    // This site's URL should be set into session, then the css and js can be full path reference.
    public static final String SITE_URL = "SITE_URL";
}
