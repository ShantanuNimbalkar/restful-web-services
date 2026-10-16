package com.rest.webservices.restful_web_services.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDaoService {
    private static Integer usersCount = 0;
    private static List<User> userList = new ArrayList<>();
    static {
        userList.add(new User(++usersCount,"Shantanu", LocalDate.now().minusYears(28)));
        userList.add(new User(++usersCount,"Vimal", LocalDate.now().minusYears(49)));
        userList.add(new User(++usersCount,"komal", LocalDate.now().minusYears(25)));
        userList.add(new User(++usersCount,"Babu", LocalDate.now().minusYears(53)));
    }

    public List<User> findAll(){
        return userList;
    }

    public User retrieveUser(Integer id){
        return userList.stream().
                filter(user -> user.getId().equals(id)).
                findFirst().
                orElse(null);

    }

    public void deleteById(Integer id){
        userList.removeIf(user -> user.getId().equals(id));
    }

    public User addUser(User user){
        if(! userList.contains(user)){
            user.setId(++usersCount);
            userList.add(user);
        }
        return user;
    }
}
