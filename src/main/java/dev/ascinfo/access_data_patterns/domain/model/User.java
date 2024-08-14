package dev.ascinfo.access_data_patterns.domain.model;

import java.util.UUID;

public record User(
    UUID id,
    String username,
    String email
) {}
