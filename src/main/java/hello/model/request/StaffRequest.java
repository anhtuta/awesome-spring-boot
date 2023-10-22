package hello.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class StaffRequest {

    @NotEmpty(message = "firstName cannot be null or empty")
    private String firstName;

    @NotEmpty(message = "lastName cannot be null or empty")
    private String lastName;

    private String gender;

    private String email;

    private Byte isAlive;

    @NotNull(message = "storeId cannot be null or empty")
    private Integer storeId;
}
