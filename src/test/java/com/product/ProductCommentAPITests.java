package com.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.entry.CommentEntry;
import com.product.entry.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductCommentAPITests {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    public ProductCommentAPITests() {
    }

    @Test
    public void testComment_nonObjectionable() {
        // act
        ResponseEntity<Response> responseEntity = postForEntity(CommentEntry.builder().comment("Good product").build());

        // assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getStatus()).isEqualTo(Boolean.TRUE);

        CommentEntry entry = OBJECT_MAPPER.convertValue(responseEntity.getBody().getData(), CommentEntry.class);
        assertThat(entry.getObjectionable()).isEqualTo(Boolean.FALSE);
    }

    @Test
    public void testComment_objectionable() {
        // act
        ResponseEntity<Response> responseEntity = postForEntity(CommentEntry.builder()
                .comment("I will kill you, when i seen next time").build());

        // assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getStatus()).isEqualTo(Boolean.TRUE);

        CommentEntry entry = OBJECT_MAPPER.convertValue(responseEntity.getBody().getData(), CommentEntry.class);
        assertThat(entry.getObjectionable()).isEqualTo(Boolean.TRUE);
    }

    @Test
    public void testComment_empty() {
        // act
        ResponseEntity<Response> responseEntity = postForEntity(CommentEntry.builder().build());

        // assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void testComment_space() {
        // act
        ResponseEntity<Response> responseEntity = postForEntity(CommentEntry.builder().comment(" ").build());

        // assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getStatus()).isEqualTo(Boolean.TRUE);

        CommentEntry entry = OBJECT_MAPPER.convertValue(responseEntity.getBody().getData(), CommentEntry.class);
        assertThat(entry.getObjectionable()).isEqualTo(Boolean.FALSE);
    }

    private ResponseEntity<Response> postForEntity(CommentEntry entry) {
        return this.restTemplate.postForEntity("/product/p1/comment", entry, Response.class);
    }

}
