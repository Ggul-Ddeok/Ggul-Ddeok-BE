package com.example.ggulddeokbe.global.swagger;

import com.example.ggulddeokbe.global.exception.ErrorCode;
import com.example.ggulddeokbe.global.exception.response.ErrorResponse;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ErrorCodeOperationCustomizer implements OperationCustomizer {

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        ApiErrorCodes annotation = handlerMethod.getMethodAnnotation(ApiErrorCodes.class);
        if (annotation == null) return operation;

        Map<Integer, List<ErrorCode>> grouped = Arrays.stream(annotation.value())
            .collect(Collectors.groupingBy(e -> e.getStatus().value(), LinkedHashMap::new, Collectors.toList()));

        ApiResponses responses = operation.getResponses();
        Schema<?> schema = ModelConverters.getInstance()
            .resolveAsResolvedSchema(new io.swagger.v3.core.converter.AnnotatedType(ErrorResponse.class))
            .schema;

        grouped.forEach((statusCode, errorCodes) -> {
            Map<String, Example> examples = new LinkedHashMap<>();
            for (ErrorCode errorCode : errorCodes) {
                Example example = new Example();
                example.setValue(Map.of(
                    "status", errorCode.getStatus().value(),
                    "message", errorCode.getMessage()
                ));
                examples.put(errorCode.name(), example);
            }

            Content content = new Content();
            MediaType mediaType = new MediaType();
            mediaType.setSchema(schema);
            mediaType.setExamples(examples);
            content.addMediaType("application/json", mediaType);

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setContent(content);
            responses.addApiResponse(String.valueOf(statusCode), apiResponse);
        });

        return operation;
    }
}
