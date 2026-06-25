package com.restcontrol.restcontrol_tc2.infra.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateRestaurantRequestDTO(
        @NotBlank String name,
        @NotBlank String city,
        @NotBlank @Pattern(regexp = "\\d{8}") String zipcode,
        @NotBlank String street,
        @NotBlank @Size(min = 2, max = 2) String state,
        @NotBlank String specialty,
        @NotBlank String ownerId
) {
}
