package model;

import lombok.Value;

/**
 * CREATE TABLE Gun (
 *  id      INT PRIMARY KEY AUTO_INCREMENT,
 *  name    VARCHAR(255) NOT NULL,
 *  caliber DOUBLE       NOT NULL
 * );
 */
@Value
public class Gun {
    private final long id;
    private final String name;
    private final double caliber;
}
