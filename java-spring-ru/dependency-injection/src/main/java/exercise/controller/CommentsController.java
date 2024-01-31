package exercise.controller;

import exercise.model.Post;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(path = "")
    public List<Comment> index(){
        return commentRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Comment show(@PathVariable long id){
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found"));
        return comment;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@RequestBody Comment comment){
        commentRepository.save(comment);
        return comment;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> update(@PathVariable String id, @RequestBody Comment data){
        Optional<Comment> com = Optional.ofNullable(commentRepository.
                findById(Long.parseLong(id)).orElseThrow(()
                        -> new ResourceNotFoundException("Comment with id " + id + " not found")));

        if(com.isPresent()){
            Comment foundComment =  com.get();
            foundComment.setBody(data.getBody());
            foundComment.setCreatedAt(LocalDate.now());
            return ResponseEntity.ok(foundComment);
        }else{
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id){
        commentRepository.deleteById(id);
    }
}