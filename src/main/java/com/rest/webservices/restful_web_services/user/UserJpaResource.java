package com.rest.webservices.restful_web_services.user;

import com.rest.webservices.restful_web_services.jpa.PostRepository;
import com.rest.webservices.restful_web_services.jpa.UserRepository;
import jakarta.validation.Valid;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(("/jpa"))
public class UserJpaResource {

    private UserRepository userRepository;
    private PostRepository postRepository;

    public UserJpaResource(UserRepository userRepository,PostRepository postRepository){
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/users")
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> findUser(@PathVariable Integer id){
//        User user = userDaoService.retrieveUser(id);
        User user = userRepository.findById(id).orElse(null);
        if(user == null)
            throw new UserNotFoundException("id = "+id);

        EntityModel<User> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findAllUsers());

        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody @Valid User user){

//        User savedUser = userDaoService.addUser(user);
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }


    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id){
        userRepository.deleteById(id);
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> getAllPostsForUser(@PathVariable Integer id){
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new UserNotFoundException("id = "+id);
        }
        return user.getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPostsForUser(@PathVariable Integer id,
                                         @Valid @RequestBody Post post){
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new UserNotFoundException("id = "+id);
        }
        post.setUser(user);
        Post savedPost = postRepository.save(post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}/posts/{postId}")
    public Post getAllPostsForUser(@PathVariable Integer id,@PathVariable Integer postId){
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new UserNotFoundException("id = "+id);
        }
        return user.getPosts().stream().filter(post -> post.getId().equals(postId)).findFirst().orElseThrow(() -> new ResourceNotFoundException("post is not available"));
    }
}
