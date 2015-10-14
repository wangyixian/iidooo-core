package com.iidooo.core.service.security;


import com.iidooo.core.dto.extend.SecurityUserDto;


public interface UserDetailService {

    /**
     * Get user by primary id
     *  
     * @param userID The user id to get the user.
     * @return The gotten user.
     */
    SecurityUserDto getUserByID(int userID);
    
    boolean isLoginIDDuplicate(String loginID);

    boolean createUser(SecurityUserDto user);

    boolean updateUser(SecurityUserDto user);

}
