package com.example.springbootvtubers.vtubers.myOshi.dto;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyOshiDto {

    private UUID id;

    @NotNull(message = "First Name is required")
    private String firstName;

    private String lastName;
    private String house;
    private String knownAs;
}
