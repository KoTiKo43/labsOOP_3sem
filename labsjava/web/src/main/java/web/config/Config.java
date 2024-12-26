package web.config;

import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class Config {

    @Bean
    TabulatedFunctionFactory arrayTabulatedFunctionFactory() {
        return new ArrayTabulatedFunctionFactory();
    }

    @Bean
    TabulatedFunctionFactory linkedListTabulatedFunctionFactory() {
        return new LinkedListTabulatedFunctionFactory();
    }

    @Bean
    Map<String, String> nameFabricMap() {
        return Map.of(
                "array", "arrayTabulatedFunctionFactory",
                "linked", "linkedListTabulatedFunctionFactory"
        );
    }
}
