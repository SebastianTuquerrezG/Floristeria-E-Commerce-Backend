package com.floridos.back_admin_management.config;

import com.floridos.back_admin_management.product.Product;
import com.floridos.back_admin_management.product.ProductRepository;
import com.floridos.back_admin_management.user.Role;
import com.floridos.back_admin_management.user.User;
import com.floridos.back_admin_management.user.UserRepository;
import com.floridos.back_admin_management.product.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner seedData() {
        return args -> {
            seedAdminUser();
            seedSampleProducts();
        };
    }

    /* ── Admin por defecto ── */
    private void seedAdminUser() {
        if (userRepository.existsByEmail("admin@floridos.com")) return;

        User admin = User.builder()
                .name("Administrador Floridos")
                .email("admin@floridos.com")
                .password(passwordEncoder.encode("floridos2026"))
                .phone("573146890813")
                .city("Popayán")
                .role(Role.ADMIN)
                .build();

        userRepository.save(admin);
        log.info("Usuario admin creado: admin@floridos.com / floridos2026");
    }

    /* ── Productos de muestra ── */
    private void seedSampleProducts() {
        if (productRepository.count() > 0) return;

        List<Product> products = List.of(
                // FLORES
                Product.builder().name("Rosas Rojas Premium").category(Category.FLORES)
                        .type("Rosas").occasion("Aniversario").color("Rojo")
                        .price(BigDecimal.valueOf(45000)).rating(4.8).inStock(true)
                        .description("Docena de rosas rojas colombianas de alta calidad")
                        .slug("rosas-rojas-premium").imageUrl("/uploads/placeholder.jpg").build(),

                Product.builder().name("Tulipanes Arcoíris").category(Category.FLORES)
                        .type("Tulipanes").occasion("Cumpleaños").color("Multicolor")
                        .price(BigDecimal.valueOf(38000)).rating(4.6).inStock(true)
                        .description("Hermoso arreglo de tulipanes en colores variados")
                        .slug("tulipanes-arcoiris").imageUrl("/uploads/placeholder.jpg").build(),

                Product.builder().name("Orquídea Blanca Elegante").category(Category.FLORES)
                        .type("Orquideas").occasion("Boda").color("Blanco")
                        .price(BigDecimal.valueOf(65000)).rating(4.9).inStock(true)
                        .description("Orquídea phalaenopsis blanca en maceta decorativa")
                        .slug("orquidea-blanca-elegante").imageUrl("/uploads/placeholder.jpg").build(),

                Product.builder().name("Girasoles Alegres").category(Category.FLORES)
                        .type("Girasoles").occasion("Solo porque si").color("Amarillo")
                        .price(BigDecimal.valueOf(32000)).rating(4.7).inStock(true)
                        .description("Ramo de girasoles frescos y radiantes")
                        .slug("girasoles-alegres").imageUrl("/uploads/placeholder.jpg").build(),

                // PLANTAS
                Product.builder().name("Monstera Deliciosa").category(Category.PLANTAS)
                        .type("Tropicales").location("Interior").care("Moderado")
                        .price(BigDecimal.valueOf(65000)).rating(4.9).inStock(true)
                        .description("La reina de las plantas de interior, hojas grandes y elegantes")
                        .slug("monstera-deliciosa").imageUrl("/uploads/placeholder.jpg").build(),

                Product.builder().name("Suculenta Mix").category(Category.PLANTAS)
                        .type("Suculentas").location("Interior").care("Fácil")
                        .price(BigDecimal.valueOf(12000)).rating(4.8).inStock(true)
                        .description("Arreglo de suculentas variadas en maceta decorativa")
                        .slug("suculenta-mix").imageUrl("/uploads/placeholder.jpg").build(),

                Product.builder().name("Pothos Dorado").category(Category.PLANTAS)
                        .type("Potos").location("Interior").care("Fácil")
                        .price(BigDecimal.valueOf(18000)).rating(4.7).inStock(true)
                        .description("Planta trepadora de fácil cuidado, ideal para colgar")
                        .slug("pothos-dorado").imageUrl("/uploads/placeholder.jpg").build()
        );

        productRepository.saveAll(products);
        log.info("{} productos de muestra creados", products.size());
    }
}
