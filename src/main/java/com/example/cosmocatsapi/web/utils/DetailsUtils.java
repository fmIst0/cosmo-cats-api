package com.example.cosmocatsapi.web.utils;

import com.example.cosmocatsapi.web.exception.ExDetails;
import java.net.URI;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

@UtilityClass
public class DetailsUtils {
    public static ProblemDetail getValidationErrors(List<ExDetails> validationErrors) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Request validation failed");

        problemDetail.setTitle("Field Validation Exception");
        problemDetail.setType(URI.create("field-validation-error"));
        problemDetail.setProperty("invalids Params", validationErrors);

        return problemDetail;
    }
}
