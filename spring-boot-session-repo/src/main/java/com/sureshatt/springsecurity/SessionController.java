package com.sureshatt.springsecurity;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This controller deletes an active @{@link Session} using the @{@link HttpSession}.
 */
@RestController
public class SessionController {

    private static final String NAME = "NAME";

    private final FindByIndexNameSessionRepository<? extends Session> sessionRepository;

    public SessionController(FindByIndexNameSessionRepository<? extends Session> sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/session", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createSession(HttpServletRequest httpServletRequest, @RequestBody String sessionName) {

        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute(NAME, sessionName);

        System.out.println("Created session " + session.getId());
        return new ResponseEntity<>(session.getId(), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/session/{sessionId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getSession(@PathVariable("sessionId") String sessionId) {

        Session session = sessionRepository.findById(sessionId);

        if (session == null) {
            System.out.println("No session found with id " + sessionId);
            return ResponseEntity.notFound().build();
        }

        System.out.println("Found session  " + session.getId());
        return new ResponseEntity<>((String) session.getAttribute(NAME), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/session/{sessionId}")
    public ResponseEntity<?> deleteSession(@PathVariable String sessionId) {

        Session session = sessionRepository.findById(sessionId);
        System.out.println("Found session " + session.getId());

        sessionRepository.deleteById(session.getId());
        System.out.println("Deleted session " + session.getId());

        return ResponseEntity.noContent().build();
    }
}
