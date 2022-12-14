package com.clonecode.inssagram.exception.advice;

import com.clonecode.inssagram.dto.response.ResponseDto;
import com.clonecode.inssagram.exception.BusinessException;
import com.clonecode.inssagram.exception.EntityNotFoundException;
import com.clonecode.inssagram.exception.InvalidValueException;
import com.clonecode.inssagram.global.error.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> handleValidationExceptions(MethodArgumentNotValidException exception) {

        return new ResponseEntity<>(ResponseDto.fail(ErrorCode.NOT_PASS_VALIDATION), HttpStatus.valueOf(ErrorCode.NOT_PASS_VALIDATION.getStatus()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseDto> handleBusinessExceptions(BusinessException exception) {

        return new ResponseEntity<>(ResponseDto.fail(ErrorCode.SERVER_ERROR), HttpStatus.valueOf(ErrorCode.SERVER_ERROR.getStatus()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseDto> handleEntityNotFoundException(EntityNotFoundException exception) {

        final ErrorCode errorCode = exception.getErrorCode();
        final ResponseDto responseDto = ResponseDto.fail(errorCode);
        return new ResponseEntity<>(responseDto, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<ResponseDto> handleInvalidValueException(InvalidValueException exception) {

        final ErrorCode errorCode = exception.getErrorCode();
        final ResponseDto responseDto = ResponseDto.fail(errorCode);
        return new ResponseEntity<>(responseDto, HttpStatus.valueOf(errorCode.getStatus()));
    }


}
