create schema order_service

CREATE TYPE order_status AS enum (
'CREATED',
'PAID',
'SHIPPED',
'CANCELLED'
);

CREATE TABLE "order_service"."carts" (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       client_id UUID NOT NULL,
                       created_at TIMESTAMP  DEFAULT now(),
                       updated_at TIMESTAMP  DEFAULT now()
);

CREATE TABLE "order_service"."cart_items" (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            cart_id UUID NOT NULL REFERENCES carts(id),
                            product_id UUID NOT NULL,
                            quantity INT NOT NULL,
                            price NUMERIC(10,2) NOT NULL,
                            currency VARCHAR(10) NOT NULL
);

CREATE INDEX idx_cart_items_cart_id ON "order_service"."cart_items"(cart_id);

CREATE TABLE "order_service"."orders" (
                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        client_id UUID NOT NULL,
                        cart_id UUID NOT NULL REFERENCES carts(id),
                        status order_status NOT NULL DEFAULT 'CREATED',
                        total_amount NUMERIC(10,2) NOT NULL,
                        currency VARCHAR(10) NOT NULL,
                        created_at TIMESTAMP  DEFAULT now(),
                        updated_at TIMESTAMP  DEFAULT now()
);

CREATE TABLE "order_service"."order_items" (
                             id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                             order_id UUID NOT NULL REFERENCES orders(id),
                             product_id UUID NOT NULL,
                             quantity INT NOT NULL,
                             price NUMERIC(10,2) NOT NULL,
                             currency VARCHAR(10) NOT NULL
);

CREATE INDEX idx_order_items_order_id ON "order_service"."order_items"(order_id);

CREATE TABLE "order_service"."order_status_history" (
                                      id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                      order_id UUID NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
                                      old_status order_status,
                                      new_status order_status NOT NULL,
                                      source VARCHAR(50),
                                      changed_at TIMESTAMP DEFAULT now()
);

CREATE INDEX idx_order_status_history_order_id ON "order_service"."order_status_history"(order_id);
