package de.turnertech.ows.common;

public enum ExceptionCode {
    
    OPERATION_NOT_SUPPORTED("OperationNotSupported"),
    NO_APPLICABLE_CODE("NoApplicableCode"),
    MISSING_PARAMETER_VALUE("MissingParameterValue"),
    INVALID_PARAMETER_VALUE("InvalidParameterValue"),
    OPERATION_PARSING_FAILED("OperationParsingFailed"),
    OPERATION_PROCESSING_FAILED("OperationProcessingFailed");

    private final String exceptionCode;

    private ExceptionCode(final String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public static ExceptionCode valueOfIgnoreCase(final String code) {
        for(ExceptionCode exceptionCode : ExceptionCode.values()) {
            if(exceptionCode.exceptionCode.equalsIgnoreCase(code)) {
                return exceptionCode;
            }
        }
        return NO_APPLICABLE_CODE;
    }

    @Override
    public String toString() {
        return this.exceptionCode;
    }

}
