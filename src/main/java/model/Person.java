package model;

import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * CREATE TABLE Person (
 *  id         INT PRIMARY KEY AUTO_INCREMENT,
 *  first_name VARCHAR(255) NOT NULL,
 *  last_name  VARCHAR(255),
 *  permission BOOLEAN         DEFAULT FALSE,
 *  dob        DATE,
 *  email      VARCHAR(255) NOT NULL,
 *  password   VARCHAR(255) NOT NULL,
 *  address    VARCHAR(255),
 *  telephone  VARCHAR(15)
 * );
 */
@Value
public class Person {
    private final long id;

    @Size(max = 255)
    @NotNull
    private final String firstName;

    @Size(max = 255)
    private final String lastName;

    private final boolean permission;

    private final LocalDate dob;

    @Size(max = 255)
    @NotNull
    private final String email;

    @Size(max = 255)
    @NotNull
    private final String password;

    @Size(max = 255)
    private final String address;

    @Size(max = 15)
    private final String telephone;
}