package exercise.dto;

import exercise.model.Car;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CarCreateDTO extends Car {
    private String model;
    private String manufacturer;
    private int enginePower;
}
