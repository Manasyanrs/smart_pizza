package com.example.smartpizza.entity.userEntity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "contact_data")
public class ContactData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Email should not be empty!")
    @Email(message = "Email should be valid!")
    private String email;
    @NotEmpty(message = "Phone number should not be empty(+374 XX-12-34-56)!")
    @Pattern(regexp = "^(([+1-9]*[\\s\\-]\\d{2}[\\s\\-]\\d{2}[\\s\\-]\\d{2}[\\s\\-]\\d{2})" +
            "|([+1-9]*[\\s\\-]\\d{2}[\\s\\-]\\d{3}[\\s\\-]\\d{3}))",
            message = "Phone number should be valid(+374 XX-12-34-56)!")
    private String phoneNumber;

}
