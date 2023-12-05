

CREATE TABLE CLIENT (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_number BIGINT UNIQUE,
    name VARCHAR(400) NOT NULL,
    is_vip BOOLEAN,
    is_urgent BOOLEAN,
    is_processed BOOLEAN,
    processing_start_time TIMESTAMP,
    time_added TIMESTAMP
);

