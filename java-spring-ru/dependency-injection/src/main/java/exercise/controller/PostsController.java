package exercise.controller;

import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private  CommentRepository commentRepository;

    @GetMapping(path = "")
    public List<Post> index(){
        return postRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Post show(@PathVariable long id){
        var post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
        return post;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post){
        postRepository.save(post);
        return post;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable String id, @RequestBody Post data){
        Optional<Post> post = Optional.ofNullable(postRepository.
                findById(Long.parseLong(id)).orElseThrow(()
                        -> new ResourceNotFoundException("Product with id " + id + " not found")));

        if(post.isPresent()){
            Post foundPost =  post.get();
            foundPost.setTitle(data.getTitle());
            foundPost.setBody(data.getBody());
            foundPost.setCreatedAt(data.getCreatedAt());
            return ResponseEntity.ok(foundPost);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id){
        postRepository.deleteById(id);
        commentRepository.deleteByPostId(id);
    }

}
