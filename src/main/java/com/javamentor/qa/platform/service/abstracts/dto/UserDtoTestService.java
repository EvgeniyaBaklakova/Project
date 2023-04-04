package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.dao.abstracts.impl.dto.UserDtoDaoTest;
import com.javamentor.qa.platform.models.dto.UserDtoTest;
import org.springframework.stereotype.Service;

@Service
public class UserDtoTestService  {

  private final UserDtoDaoTest userDtoDaoTest;

    public UserDtoTestService(UserDtoDaoTest userDtoDaoTest) {
        this.userDtoDaoTest = userDtoDaoTest;
    }


    public UserDtoTest getUserById(Long id) {
        return userDtoDaoTest.getUserById(id).orElse(null);
    }


}
