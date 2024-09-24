CREATE TABLE couriers (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    identity_number VARCHAR(255),
    phone VARCHAR(255),
    plate_number VARCHAR(255),
    is_working BOOLEAN,
    birth_date DATE,
    status VARCHAR(255),
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_identity_number UNIQUE (identity_number)
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    order_number VARCHAR(255),
    restaurant_code VARCHAR(255),
    user_id BIGINT,
    price DECIMAL(10, 2),
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(255),
    courier_id BIGINT,
    CONSTRAINT fk_courier FOREIGN KEY (courier_id) REFERENCES couriers (id) ON DELETE SET NULL
);
