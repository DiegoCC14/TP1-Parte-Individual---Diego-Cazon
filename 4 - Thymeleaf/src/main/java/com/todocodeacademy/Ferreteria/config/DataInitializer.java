package com.todocodeacademy.Ferreteria.config;

import com.todocodeacademy.Ferreteria.model.Producto;
import com.todocodeacademy.Ferreteria.repository.IProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(IProductoRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                List<Producto> herramientasIniciales = Arrays.asList(
                    new Producto("Taladro Percutor 650W", "Bosch", "Herramientas Eléctricas", 89.99, 15, "Taladro percutor ergonómico con mandril de 13mm y velocidad variable."),
                    new Producto("Amoladora Angular 4 1/2''", "DeWalt", "Herramientas Eléctricas", 75.50, 10, "Amoladora angular con motor de 800W para cortes y desbaste en metal y mampostería."),
                    new Producto("Juego de Destornilladores 6 Piezas", "Stanley", "Herramientas Manuales", 18.25, 30, "Set de 3 destornilladores planos y 3 Phillips con puntas magnéticas."),
                    new Producto("Martillo de Uña Curva 16 oz", "Truper", "Herramientas Manuales", 12.00, 25, "Martillo con mango de fibra de vidrio antigolpes y cabeza de acero forjado."),
                    new Producto("Cinta Métrica 5 Metros", "Stanley", "Medición", 8.50, 40, "Cinta métrica con botón de traba y cinta de acero recubierta de nylon."),
                    new Producto("Sierra Circular 1400W", "Makita", "Herramientas Eléctricas", 145.00, 8, "Sierra circular de alta precisión con disco de 7 1/4'' para madera.")
                );
                repo.saveAll(herramientasIniciales);
                System.out.println(">> Base de datos inicializada con herramientas de ejemplo.");
            }
        };
    }
}
