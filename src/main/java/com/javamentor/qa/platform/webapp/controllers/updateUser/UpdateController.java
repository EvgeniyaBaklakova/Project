package com.javamentor.qa.platform.webapp.controllers.updateUser;


import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/update")
public class UpdateController {

    private final UserDao userDao;

    @Autowired
    public UpdateController(UserDao userDao) {
        this.userDao = userDao;
    }

    @PatchMapping ("/blockUser/{email}")
    public ResponseEntity<?> blockUser(@PathVariable String email) {

        userDao.blockUser(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
