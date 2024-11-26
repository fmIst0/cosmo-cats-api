package com.example.cosmocatsapi.web.exception;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ExDetails {
    String field;
    String reason;
}
