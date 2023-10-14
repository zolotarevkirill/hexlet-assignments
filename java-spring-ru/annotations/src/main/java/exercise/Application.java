package exercise;

import exercise.model.Address;
import exercise.annotation.Inspect;
import java.lang.reflect.Method;

public class Application {
    public static void main(String[] args) {
        var address = new Address("London", 12345678);

        for (Method method : Address.class.getDeclaredMethods()) {

            if (method.isAnnotationPresent(Inspect.class)) {

                try {
                    // Выполняем метод, помеченный аннотацией Inspect
                    Object result = method.invoke(address);
                    Class<?> returnType = method.getReturnType();
                    String returnTypeString = returnType.getSimpleName();

                    System.out.println("Method " + method.getName() + " returns a value of type " + returnTypeString);
                } catch (Exception e) {
                    e.printStackTrace();
                }




//                System.out.println("Method: " + method.getName() + "returns a value of type " + returnTypeString);
            }
        }
    }
}
