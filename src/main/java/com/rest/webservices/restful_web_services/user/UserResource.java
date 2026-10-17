package com.rest.webservices.restful_web_services.user;

import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserResource {

    private  UserDaoService userDaoService;

    public UserResource(UserDaoService userDaoService){
        this.userDaoService = userDaoService;
    }

    @GetMapping("/users")
    public List<User> findAllUsers(){
        return userDaoService.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> findUser(@PathVariable Integer id){
        User user = userDaoService.retrieveUser(id);
        if(user == null)
            throw new UserNotFoundException("id = "+id);

        EntityModel<User> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findAllUsers());

        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody @Valid User user){

        User savedUser = userDaoService.addUser(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }


    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id){
        userDaoService.deleteById(id);
    }
}
