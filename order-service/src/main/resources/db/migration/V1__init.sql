CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    order_number UUID NOT NULL,
    user_id BIGINT NOT NULL,
    restaurant_code VARCHAR(255) NOT NULL,
    menu_id VARCHAR(255) NOT NULL,
    price NUMERIC(19, 2) NOT NULL,
    quantity INTEGER NOT NULL,
    status VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);