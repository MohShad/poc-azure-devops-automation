package br.com.helloworldjava.dto;

public class ResponsePersonDTO {

    private boolean success;
    private Long id;
    private String message;

    public ResponsePersonDTO(boolean success, Long id, String message) {
        this.success = success;
        this.id = id;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
