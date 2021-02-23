package pe.carlosesp.demo.demorestservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.carlosesp.demo.demorestservice.domain.Greeting;
import pe.carlosesp.demo.demorestservice.service.GreetingService;

@RestController
public class GreetingController {

    private GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/greeting")
    public Greeting getGreeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return greetingService.getGreeting(name);

    }

}
