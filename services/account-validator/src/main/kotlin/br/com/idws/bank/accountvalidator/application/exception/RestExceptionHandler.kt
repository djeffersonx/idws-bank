package br.com.idws.bank.accountvalidator.application.exception

import br.com.idws.bank.accountvalidator.domain.model.validation.ValidationException
import br.com.idws.bank.accountvalidator.infrastructure.fuel.CommunicationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.converter.HttpMessageNotWritableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(ValidationException::class)
    protected fun handleValidationException(
        ex: ValidationException, request: WebRequest?
    ) = errorResponse(HttpStatus.BAD_REQUEST, ApiErrors(ex.errors.map { "${it.key}: ${it.message}" }))

    @ExceptionHandler(CommunicationException::class)
    protected fun handleApiException(
        ex: CommunicationException, request: WebRequest?
    ) = errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ApiErrors(ex.errors))

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    protected fun handleMethodArgumentTypeMismatch(
        ex: MethodArgumentTypeMismatchException, request: WebRequest?
    ) = errorResponse(
        HttpStatus.BAD_REQUEST, ApiErrors(
            listOf(
                String.format(
                    "The parameter '%s' of value '%s' could not be converted to type '%s'",
                    ex.name,
                    ex.value,
                    ex.requiredType!!
                        .simpleName
                )
            )
        )
    )

    override fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ) = errorResponse(
        HttpStatus.BAD_REQUEST, ApiErrors(
            listOf(String.format("Could not find the %s method for URL %s", ex.httpMethod, ex.requestURL))
        )
    )

    override fun handleMissingServletRequestParameter(
        ex: MissingServletRequestParameterException, headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ) = errorResponse(HttpStatus.BAD_REQUEST, ApiErrors(listOf(ex.parameterName + " parameter is missing")))

    override fun handleHttpMediaTypeNotSupported(
        ex: HttpMediaTypeNotSupportedException, headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ) = errorResponse(
        HttpStatus.UNSUPPORTED_MEDIA_TYPE,
        ApiErrors(
            listOf(
                "${ex.contentType} media type is not supported. Supported media types are ${
                    ex.supportedMediaTypes.joinToString(separator = ",")
                }"
            )
        )
    )

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val fieldErrors = ex.bindingResult.fieldErrors.map { it.toString() }
        val globalErrors = ex.bindingResult.globalErrors.map { it.toString() }
        return errorResponse(HttpStatus.BAD_REQUEST, ApiErrors(fieldErrors + globalErrors))
    }

//    @ExceptionHandler(javax.validation.ConstraintViolationException::class)
//    protected fun handleConstraintViolation(
//        ex: javax.validation.ConstraintViolationException
//    ): ResponseEntity<Any?>? {
//        val apiError = ApiError(BAD_REQUEST)
//        apiError.setMessage("Validation error")
//        apiError.addValidationErrors(ex.getConstraintViolations())
//        return buildResponseEntity(apiError)
//    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ) = errorResponse(HttpStatus.BAD_REQUEST, ApiErrors(listOf("Malformed request: $ex")))

    override fun handleHttpMessageNotWritable(
        ex: HttpMessageNotWritableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ) = errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ApiErrors(listOf("Error writing output")))

    fun errorResponse(httpStatus: HttpStatus, errors: ApiErrors): ResponseEntity<Any> {
        return ResponseEntity(errors, httpStatus)
    }

}

data class ApiErrors(val errors: List<String>)