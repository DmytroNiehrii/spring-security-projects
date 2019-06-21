package com.sureshatt.springsecurity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class SessionControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testDeleteSession() {

        String session1Name = "session1";

        ResponseEntity<String> sessionIdEntity = restTemplate.postForEntity("/session", session1Name, String.class);
        assertThat(sessionIdEntity.getStatusCode(), equalTo(HttpStatus.CREATED));
        String sessionId = sessionIdEntity.getBody();
        assertThat(sessionId, notNullValue());

        ResponseEntity<String> sessionNameEntity = restTemplate.getForEntity("/session/" + sessionId, String.class);
        assertThat(sessionNameEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(sessionNameEntity.getBody(), equalToIgnoringCase(session1Name));

        restTemplate.delete("/session/" + sessionId);

        ResponseEntity<String> sessionNameEntity2 = restTemplate.getForEntity("/session/" + sessionId, String.class);
        assertThat(sessionNameEntity2.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));

    }
}
