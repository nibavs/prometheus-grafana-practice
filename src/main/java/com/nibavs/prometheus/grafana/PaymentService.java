package com.nibavs.prometheus.grafana;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final Counter successfulPayments;
    private final Counter declinedPayments;

    public PaymentService(MeterRegistry registry) {
        successfulPayments = Counter.builder("payments.processed")
                .description("Number of completed payment attempts")
                .tag("result", "success")
                .register(registry);

        declinedPayments = Counter.builder("payments.processed")
                .description("Number of completed payment attempts")
                .tag("result", "declined")
                .register(registry);
    }

    public String process(boolean decline) {
        if (decline) {
            declinedPayments.increment();
            return "declined";
        }

        successfulPayments.increment();
        return "success";
    }
}

