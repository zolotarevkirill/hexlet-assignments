package exercise;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @GetMapping("/posts") // Список всех постов
//    public List<Post> index(@RequestParam(defaultValue = "10") Integer limit) {
//        return posts.stream().limit(limit).toList();
//    }

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

    @GetMapping("/posts/{id}") // Просмотр конкретного поста
    public Optional<Post> show(@PathVariable String id) {
        var post = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        return post;
    }

    @PostMapping("/posts") // Создание страницы
    public Post create(@RequestBody Post page) {
        posts.add(page);
        return page;
    }

    @PutMapping("/posts/{id}") // Обновление поста
    public Post update(@PathVariable String id, @RequestBody Post data) {
        var maybePage = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        if (maybePage.isPresent()) {
            var page = maybePage.get();
            page.setId(data.getId());
            page.setTitle(data.getTitle());
            page.setBody(data.getBody());
        }
        return data;
    }

    @DeleteMapping("/posts/{id}") // Удаление поста
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }

}
