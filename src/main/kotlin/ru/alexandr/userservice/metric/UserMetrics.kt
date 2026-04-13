package ru.alexandr.userservice.metrics

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.DistributionSummary
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import org.springframework.stereotype.Component

@Component
class UserMetrics(
    registry: MeterRegistry,
) {

    val registerTimer: Timer = Timer.builder("user.register.duration")
        .description("User registration processing duration")
        .publishPercentileHistogram()
        .register(registry)

    val registerErrors: Counter = Counter.builder("user.register.errors.total")
        .description("User registration errors")
        .register(registry)

    val registerSuccess: Counter = Counter.builder("user.register.success.total")
        .description("Successful user registrations")
        .register(registry)

    val registerDuplicateEmail: Counter = Counter.builder("user.register.duplicate_email.total")
        .description("Registration attempts with duplicate email")
        .register(registry)

    val registerPasswordLength: DistributionSummary = DistributionSummary
        .builder("user.register.password.length")
        .description("Password length used during registration")
        .register(registry)

    val loginTimer: Timer = Timer.builder("user.login.duration")
        .description("User login processing duration")
        .publishPercentileHistogram()
        .register(registry)

    val loginErrors: Counter = Counter.builder("user.login.errors.total")
        .description("User login errors")
        .register(registry)

    val loginSuccess: Counter = Counter.builder("user.login.success.total")
        .description("Successful user logins")
        .register(registry)

    val internalGetEmailTimer: Timer = Timer.builder("user.internal.get_email.duration")
        .description("Internal get-user-email processing duration")
        .publishPercentileHistogram()
        .register(registry)

    val internalGetEmailErrors: Counter = Counter.builder("user.internal.get_email.errors.total")
        .description("Internal get-user-email errors")
        .register(registry)

    val internalGetEmailNotFound: Counter = Counter.builder("user.internal.get_email.not_found.total")
        .description("Internal get-user-email not found")
        .register(registry)

    val internalGetEmailSuccess: Counter = Counter.builder("user.internal.get_email.success.total")
        .description("Successful internal get-user-email responses")
        .register(registry)
}