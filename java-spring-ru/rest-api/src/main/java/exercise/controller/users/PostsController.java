package exercise.controller.users;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.*;

import exercise.model.Post;
import exercise.Data;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class PostsController {
    private final List<Post> posts = Data.getPosts();


    @GetMapping("/posts") // Список всех постов
    public List<Post> index(
            @RequestParam(defaultValue = "2") Integer page,
            @RequestParam(defaultValue = "10") Integer limit)
    {
        int startIndex = (page - 1) * limit;
        int endIndex = startIndex + limit;

        return posts.stream()
                .skip(startIndex)
                .limit(limit)
                .collect(Collectors.toList());

    }


    @PostMapping("/users/{id}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@PathVariable int id, @RequestBody Post post) {
        post.setUserId(id);
        posts.add(post);
        return post;
    }


    @GetMapping("/users/{id}/posts")
    public List<Post> show(@PathVariable String id) {
        int userId = Integer.parseInt(id);
        System.out.println("TEST!");
        System.out.println(userId);
        Stream<Post> postStream = posts.stream();

        if (userId > 0) {
            postStream = postStream.filter(post -> post.getUserId() == userId);
        }

        return postStream.collect(Collectors.toList());
    }

}
