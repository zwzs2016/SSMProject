package com.uwan.SSM.AppService;

import com.uwan.SSM.AppEntity.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    List<User> getUserList(int id);
}
