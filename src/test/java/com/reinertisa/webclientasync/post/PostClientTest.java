package com.reinertisa.webclientasync.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostClientTest {

    @Mock
    WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private PostClient postClient;

    @BeforeEach
    void setup() {
        postClient = new PostClient(webClient);
    }

    @Test
    void testFindAllPosts() {
        Post hello = new Post(1, 1, "Hello, World!", "This is my first post");
        Post goodbye = new Post(1, 2, "Goodbye, World!", "This is my last post");

        Mockito.when(webClient.get()).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.uri("/posts")).thenReturn(requestHeadersSpec);
        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(responseSpec.bodyToFlux(Post.class)).thenReturn(Flux.just(hello, goodbye));

        Flux<Post> result = postClient.findAll();

        StepVerifier.create(result)
                .expectNext(hello)
                .expectNext(goodbye)
                .verifyComplete();



    }

}