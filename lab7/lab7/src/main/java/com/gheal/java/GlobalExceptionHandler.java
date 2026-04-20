package com.gheal.java;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLException.class)
    public ProblemDetail handleSQLException(SQLException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), e.getMessage());
    }
}