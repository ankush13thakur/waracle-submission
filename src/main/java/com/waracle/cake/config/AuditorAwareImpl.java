package com.waracle.cake.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
    	// We can implement logic based on spring security authentication
    	return Optional.of("SYSTEM");
    }

}