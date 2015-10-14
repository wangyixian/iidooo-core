package com.iidooo.core.service.security;

import java.util.List;

import com.iidooo.core.dto.PageDto;
import com.iidooo.core.dto.extend.SecurityUserDto;

public interface UserListService {
    SecurityUserDto getUser(int userId);   

    
    int getUserListCount();

    List<SecurityUserDto> getUserList( PageDto page);
   /**
     * Delete the user
     * 
     * @param user This user should be delete.
     * @return Delete success or not.
     */
    boolean deleteUser(SecurityUserDto user);

}
