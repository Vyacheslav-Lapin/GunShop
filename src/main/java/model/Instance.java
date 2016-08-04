package model;

import lombok.Value;

/**
 * CREATE TABLE Instance (
 *  id       INT PRIMARY KEY AUTO_INCREMENT,
 *  model_id INT NOT NULL,
 *  FOREIGN KEY (model_id) REFERENCES Gun (id)
 * );
 */
@Value
public class Instance {
    private final int id;
    private final Gun model;
}
