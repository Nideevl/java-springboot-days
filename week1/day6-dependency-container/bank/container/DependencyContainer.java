package bank.container;

import bank.annotation.Inject;
import java.lang.reflect.Field;

public class DependencyContainer {
    public <T> T getInstance(Class<T> clazz) throws Exception {
        T instance = clazz.getDeclaredConstructor().newInstance();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                Object dependency = field.getType().getDeclaredConstructor().newInstance();
                field.setAccessible(true);
                field.set(instance, dependency);
            }
        }
        return instance;
    }
}