package exercise.daytime;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class Night implements Daytime {
    private String name = "night";

    public String getName() {
        return name;
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("Night Cleaning up resources or performing final actions!");
    }
}
