package model;

import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Value
public class Person {
    private final long id; // id   INT PRIMARY KEY AUTO_INCREMENT,

    @Size(max = 255)
    @NotNull
    private final String first_name; // first_name VARCHAR(255) NOT NULL,

    @Size(max = 255)
    private final String last_name; // last_name  VARCHAR(255),

    private final boolean permission; // permission BOOLEAN         DEFAULT FALSE,

    private final LocalDate dob; // dob        DATE,

    @Size(max = 255)
    @NotNull
    private final String email; // email      VARCHAR(255) NOT NULL,

    @Size(max = 255)
    @NotNull
    private final String password; // password   VARCHAR(255) NOT NULL,

    @Size(max = 255)
    private final String address; // address    VARCHAR(255),

    @Size(max = 15)
    private final String telephone; // telephone  VARCHAR(15)
}