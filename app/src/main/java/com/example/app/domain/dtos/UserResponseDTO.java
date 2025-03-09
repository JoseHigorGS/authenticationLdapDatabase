package com.example.app.domain.dtos;

import lombok.Builder;

@Builder
public record UserResponseDTO (
        String token
) {

}
