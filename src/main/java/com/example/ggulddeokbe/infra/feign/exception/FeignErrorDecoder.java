package com.example.ggulddeokbe.infra.feign.exception;

import com.example.ggulddeokbe.global.exception.BaseException;
import com.example.ggulddeokbe.global.exception.ErrorCode;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 400 -> new BaseException(ErrorCode.YOUTH_API_ERROR);
            case 401, 403 -> new BaseException(ErrorCode.YOUTH_API_INVALID_KEY);
            case 404 -> new BaseException(ErrorCode.YOUTH_API_NOT_FOUND);
            default -> new BaseException(ErrorCode.YOUTH_API_SERVER_ERROR);
        };
    }
}