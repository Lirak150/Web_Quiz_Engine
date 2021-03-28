package engine.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDto {
    @Pattern(regexp = "[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}",
            message = "Username should be correct email")
    @NotNull
    private String email;

    @Size(min = 5)
    @NotNull
    private String password;

    public UserDto(String email,String password) {
        this.email = email;
        this.password = password;
    }

    public UserDto(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
