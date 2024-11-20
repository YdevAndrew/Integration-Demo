CREATE TABLE customer (
                          id serial PRIMARY KEY,
                          full_name varchar(255) NOT NULL,
                          cpf varchar(11) NOT NULL UNIQUE,
                          gender varchar(15) NOT NULL,
                          birthday date NOT NULL,
                          profile_picture varchar(255) NULL,
                          street varchar(100),
                          district varchar(50),
                          city varchar(50),
                          state varchar(50),
                          postal_code varchar(10),
                          country varchar(50),
                          email varchar(100),
                          phone_number varchar(20)
);

CREATE TABLE account (
                         id serial PRIMARY KEY,
                         account_number varchar(50) NOT NULL,
                         balance decimal(15, 2) NOT NULL,
                         status varchar(10) NOT NULL,
                         currency varchar(3) NOT NULL,
                         created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         customer_id int NOT NULL UNIQUE,
                         payment_password char(4),
                         CONSTRAINT account_customer FOREIGN KEY (customer_id)
                             REFERENCES customer (id) NOT DEFERRABLE INITIALLY IMMEDIATE
);

CREATE TABLE authentication (
                                id serial PRIMARY KEY,
                                password_hash varchar(100) NOT NULL,
                                mfa_enabled boolean NOT NULL DEFAULT FALSE,
                                mfa_secret varchar(255) NULL,
                                failed_attempts int NOT NULL DEFAULT 0,
                                account_locked boolean NOT NULL DEFAULT FALSE,
                                lock_timestamp timestamp NULL,
                                created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                customer_id int NOT NULL,
                                CONSTRAINT authentication_customer FOREIGN KEY (customer_id)
                                    REFERENCES customer (id) NOT DEFERRABLE INITIALLY IMMEDIATE
);

CREATE TABLE login_logs (
                            id serial PRIMARY KEY,
                            login_timestamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            ip_address varchar(45) NULL,
                            success boolean NOT NULL,
                            created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            customer_id int NOT NULL,
                            CONSTRAINT login_logs_customer FOREIGN KEY (customer_id)
                                REFERENCES customer (id) NOT DEFERRABLE INITIALLY IMMEDIATE
);

CREATE TABLE password_recovery (
                                   id serial PRIMARY KEY,
                                   recovery_token varchar(100) NULL,
                                   requested_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   expires_at timestamp NOT NULL,
                                   customer_id int NOT NULL,
                                   CONSTRAINT password_recovery_customer FOREIGN KEY (customer_id)
                                       REFERENCES customer (id) NOT DEFERRABLE INITIALLY IMMEDIATE
);
