package com.nibavs.prometheus.grafana;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Controller {

    private final PaymentService paymentService;

    @GetMapping("/work")
    public ResponseEntity<String> work(@RequestParam long delayMs, @RequestParam boolean fail)
            throws InterruptedException {
        if (delayMs < 0) {
            return ResponseEntity.badRequest().body("delayMs must not be negative");
        }

        Thread.sleep(delayMs);

        if (fail) {
            return ResponseEntity.internalServerError().body("500");
        } else {
            return ResponseEntity.ok("200");
        }
    }

    @PostMapping
    public Map<String, String> pay(
            @RequestParam(defaultValue = "false") boolean decline
    ) {
        String result = paymentService.process(decline);
        return Map.of("result", result);
    }


}
