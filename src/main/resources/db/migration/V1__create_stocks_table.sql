CREATE TABLE stocks
(
    product_id UUID PRIMARY KEY,
    quantity   INTEGER NOT NULL CHECK (quantity >= 0)
);
