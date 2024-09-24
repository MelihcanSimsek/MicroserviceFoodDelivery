ALTER TABLE orders
    ADD COLUMN menu_id VARCHAR(255),
    ADD COLUMN email VARCHAR(255),
    ADD COLUMN quantity INTEGER,
    ALTER COLUMN order_number SET DATA TYPE UUID USING order_number::uuid;