package modularization.libraries.uicomponents.exceptions;

public class MagicalException extends Exception {

    private String errorCode = null;
    private String errorMessage = null;
    private Throwable errorCause = null;

    public MagicalException() {
        super();
    }

    public MagicalException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public MagicalException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public MagicalException(String errorMessage, Throwable errorCause) {
        super(errorMessage, errorCause);
        this.errorCause = errorCause;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Throwable getErrorCause() {
        return errorCause;
    }

    public void setErrorCause(Throwable errorCause) {
        this.errorCause = errorCause;
    }

}
