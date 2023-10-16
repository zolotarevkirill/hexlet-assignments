package exercise;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/posts") // Список всех постов
    public ResponseEntity<List<Post>> index(@RequestParam(defaultValue = "10") Integer limit) {
        var result = posts.stream().limit(limit).toList();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(posts.size()))
                .body(result);
    }

    @GetMapping("/posts/{id}") // Просмотр конкретного поста
    public ResponseEntity<Optional<Post>> show(@PathVariable String id) {
        var post = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        if (post.isPresent()){
            return ResponseEntity.ok().body(post);
        }
        else{
            return ResponseEntity.status(404).body(post);
        }

    }

    @PostMapping("/posts") // Создание страницы
    public ResponseEntity<Post> create(@RequestBody Post page) {
        posts.add(page);
        return ResponseEntity.status(201).body(page);
    }

    @PutMapping("/posts/{id}") // Обновление поста
    public ResponseEntity<Post> update(@PathVariable String id, @RequestBody Post data) {
        var maybePage = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        if (maybePage.isPresent()) {
            var page = maybePage.get();
            page.setId(data.getId());
            page.setTitle(data.getTitle());
            page.setBody(data.getBody());
            return ResponseEntity.ok().body(data);
        }else{
            return ResponseEntity.status(204).body(data);
        }
    }

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
