package hello.common;

import lombok.Getter;

@Getter
public enum StatusType {

    // 200
    SUCCESS(200000, "Success"),
    NO_DATA(200001, "No data"),

    // 400
    FAIL(400000, "FAIL"),
    NONSENSE_IS_MISSING(400001, "'nonsense' header is missing"),
    INVALID_NONSENSE(400002, "Invalid 'nonsense' header"),
    INVALID_AUTHORIZATION_HEADER(400003, "Invalid authorization header"),

    // 401
    UNAUTHORIZED(401000, "Oops! Unauthorized!!!"),

    // 403
    FORBIDDEN(403000, "Oops! Unauthorized!!!"),

    // 404
    BOOK_NOT_FOUND(404001, "Book not found"),

    // 500
    INTERNAL_SERVER_ERROR(500000, "INTERNAL SERVER ERROR!");

    private int code;
    private String message;

    private StatusType(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
