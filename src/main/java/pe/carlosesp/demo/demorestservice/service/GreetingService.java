package pe.carlosesp.demo.demorestservice.service;

import org.springframework.stereotype.Service;
import pe.carlosesp.demo.demorestservice.domain.Greeting;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class GreetingService {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    public Greeting getGreeting(String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
