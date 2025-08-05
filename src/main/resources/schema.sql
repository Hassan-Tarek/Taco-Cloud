CREATE DATABASE IF NOT EXISTS taco_cloud;

USE taco_cloud;

-- create taco_order table
CREATE TABLE IF NOT EXISTS taco_order (
    id BIGINT AUTO_INCREMENT NOT NULL,
    delivery_name VARCHAR(50) NOT NULL,
    delivery_street VARCHAR(50) NOT NULL,
    delivery_city VARCHAR(50) NOT NULL,
    delivery_state VARCHAR(2) NOT NULL,
    delivery_zip VARCHAR(10) NOT NULL,
    cc_number VARCHAR(20) NOT NULL,
    cc_expiration VARCHAR(5) NOT NULL,
    cc_cvv VARCHAR(3) NOT NULL,
    placed_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_id PRIMARY KEY (id)
);

-- create taco table
CREATE TABLE IF NOT EXISTS taco (
    id BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(50),
    taco_order_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_id PRIMARY KEY (id),
    CONSTRAINT fk_taco_order FOREIGN KEY (taco_order_id)
        REFERENCES taco_order (id)
        ON DELETE CASCADE
);

-- create ingredient table
CREATE TABLE IF NOT EXISTS ingredient (
    id VARCHAR(4) NOT NULL,
    name VARCHAR(25) NOT NULL,
    type VARCHAR(10) NOT NULL,
    CONSTRAINT pk_id PRIMARY KEY (id)
);

-- create taco_ingredients join table
CREATE TABLE IF NOT EXISTS taco_ingredients (
    taco_id BIGINT NOT NULL,
    ingredient_id VARCHAR(4) NOT NULL,
    CONSTRAINT fk_taco FOREIGN KEY (taco_id)
        REFERENCES taco (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_ingredient FOREIGN KEY (ingredient_id)
        REFERENCES ingredient (id)
        ON DELETE CASCADE
);
