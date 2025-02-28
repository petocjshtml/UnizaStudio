package com.example.UnizaStudio.models.data_transfer_objects;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {

    @NotBlank(message = "Prezývka je povinná")
    @Size(min = 4, max = 20, message = "Prezývka musí mať od 4 do 20 znakov")
    private String nickname;

    @NotBlank(message = "Telefónne číslo je povinné")
    @Pattern(regexp = "^[0-9]{9}$", message = "Telefónne číslo musí mať 9 číslic")
    private String phone;

    @NotBlank(message = "Email je povinný")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@(stud\\.uniza\\.sk|uniza\\.sk)$",
            message = "Email musí končiť na @uniza.sk alebo @stud.uniza.sk")
    private String email;

    @NotBlank(message = "Heslo je povinné")
    @Size(min = 8, message = "Heslo musí mať aspoň 8 znakov")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).+$",
            message = "Heslo musí obsahovať aspoň 1 malé písmeno, 1 veľké písmeno a 1 špeciálny znak")
    private String password;

    @NotBlank(message = "Potvrdenie hesla je povinné")
    private String passwordRepeat;

    @AssertTrue(message = "Musíte súhlasiť s podmienkami")
    private boolean termsAccepted;
}
