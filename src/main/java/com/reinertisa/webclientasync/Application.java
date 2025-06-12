package com.reinertisa.webclientasync;

import com.reinertisa.webclientasync.post.Post;
import com.reinertisa.webclientasync.post.PostClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("https://jsonplaceholder.typicode.com").build();
    }

    @Bean
    CommandLineRunner commandLineRunner(PostClient postClient) {
        return args -> {
            Flux<Post> posts = postClient.findAll();
            posts.subscribe(System.out::println);

            Mono<Post> post = postClient.findById(1);
            post.subscribe(System.out::println);

            Mono<Post> updatedPost = postClient.update(1, new Post(1, 3, "test", "test"));
            updatedPost.subscribe(System.out::println);
        };
    }
}
