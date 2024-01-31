package exercise.daytime;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class Day implements Daytime {
    private String name = "day";

    public String getName() {
        return name;
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("Day Cleaning up resources or performing final actions!");
    }
}

