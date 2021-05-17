package modularization.libraries.dataSource.repository.remote.baseClasses;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class BaseErrorResponse {

    @SerializedName("errors")
    private Set<ValidationError> errors;

    @SerializedName("message")
    private String message;

    @SerializedName("fingerPrint")
    private String fingerPrint;

    public Set<ValidationError> getErrors() {
        return errors;
    }

    public void setErrors(Set<ValidationError> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public static class ValidationError {

        @SerializedName("field")
        private String field;

        @SerializedName("message")
        private String message;
    }
}
