package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

@RestController
@RequestMapping("/")
public class PostsController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/posts")
    public List<PostDTO> index(){
        List<Post> posts = postRepository.findAll();
        var result = posts.stream()
                .map(this::toDTO)
                .toList();
        return result;
    }

    @GetMapping("/posts/{id}")
    public PostDTO show(@PathVariable long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id "+id+" not found"));

        PostDTO postDTO = toDTO(post);

        List<CommentDTO> commentDTOs = commentRepository.findByPostId(id).stream()
                .map(this::toComDTO)
                .collect(Collectors.toList());


        postDTO.setComments(commentDTOs);

        return postDTO;
    }



    public List getComment(Long id){
        List<Comment> comments =  commentRepository.findByPostId(id);
        var result =  comments.stream()
                .map(this::toComDTO)
                .toList();
        return result;
    }

    public PostDTO toDTO(Post post) {
        var dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setBody(post.getBody());
        List<CommentDTO> comments = getComment(post.getId());
        dto.setComments(comments);
        return dto;
    }

    public CommentDTO toComDTO(Comment comment){
        var comDTO =  new CommentDTO();
        comDTO.setId(comment.getId());
        comDTO.setBody(comment.getBody());
        return comDTO;
    }


}
