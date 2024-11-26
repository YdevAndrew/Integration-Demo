-- This script was generated by the ERD tool in pgAdmin 4.
-- Please log an issue at https://github.com/pgadmin-org/pgadmin4/issues/new/choose if you find any bugs, including reproduction steps.
BEGIN;


CREATE TABLE IF NOT EXISTS public.account
(
    id serial NOT NULL,
    account_number character varying(255) COLLATE pg_catalog."default",
    balance numeric(38, 2),
    created_at date,
    currency smallint,
    customer_id integer NOT NULL,
    payment_password character varying(255) COLLATE pg_catalog."default",
    status character varying(255) COLLATE pg_catalog."default",
    updated_at date,
    CONSTRAINT account_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.authentication
(
    id serial NOT NULL,
    customer_id integer,
    CONSTRAINT authentication_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.backup_data
(
    backup_id serial NOT NULL,
    backup_timestamp timestamp(6) without time zone,
    card_expiry_date character varying(255) COLLATE pg_catalog."default",
    card_holder_name character varying(255) COLLATE pg_catalog."default",
    card_number character varying(255) COLLATE pg_catalog."default",
    client_name character varying(255) COLLATE pg_catalog."default",
    fk_client integer,
    fk_credit_card integer,
    fk_limit_card integer,
    fk_payment_date integer,
    limit_value numeric(38, 2),
    password_credit_card character varying(255) COLLATE pg_catalog."default",
    payment_date character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT backup_data_pkey PRIMARY KEY (backup_id)
);

CREATE TABLE IF NOT EXISTS public.billing_address
(
    id_billing_address serial NOT NULL,
    cep character varying(255) COLLATE pg_catalog."default",
    city character varying(255) COLLATE pg_catalog."default",
    country character varying(255) COLLATE pg_catalog."default",
    fk_account_id integer NOT NULL,
    state character varying(255) COLLATE pg_catalog."default",
    street character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT billing_address_pkey PRIMARY KEY (id_billing_address)
);

CREATE TABLE IF NOT EXISTS public.credit_card
(
    id_credit_card serial NOT NULL,
    active boolean NOT NULL,
    cvv character varying(255) COLLATE pg_catalog."default" NOT NULL,
    expiration_date character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name_card character varying(255) COLLATE pg_catalog."default" NOT NULL,
    number_card character varying(255) COLLATE pg_catalog."default" NOT NULL,
    type_card character varying(255) COLLATE pg_catalog."default" NOT NULL,
    fk_limit_card_id integer,
    CONSTRAINT credit_card_pkey PRIMARY KEY (id_credit_card),
    CONSTRAINT uk_mhisp0djlmbvt6vn1gugbs0yk UNIQUE (number_card)
);

CREATE TABLE IF NOT EXISTS public.credit_card_client
(
    id_credit_card_client serial NOT NULL,
    fk_account_id integer NOT NULL,
    fk_credit_card_id integer,
    fk_password_credit_card_id integer,
    fk_date_payment bigint,
    CONSTRAINT credit_card_client_pkey PRIMARY KEY (id_credit_card_client)
);

CREATE TABLE IF NOT EXISTS public.custom_limit
(
    id_custom_limit serial NOT NULL,
    custom_limit numeric(38, 2) NOT NULL,
    fk_limit_card_id integer,
    CONSTRAINT custom_limit_pkey PRIMARY KEY (id_custom_limit)
);

CREATE TABLE IF NOT EXISTS public.customer
(
    id serial NOT NULL,
    birthday date,
    country character varying(255) COLLATE pg_catalog."default",
    cpf character varying(255) COLLATE pg_catalog."default",
    district character varying(255) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default",
    full_name character varying(255) COLLATE pg_catalog."default",
    gender character varying(255) COLLATE pg_catalog."default",
    password bytea,
    phone_number character varying(255) COLLATE pg_catalog."default",
    postal_code character varying(255) COLLATE pg_catalog."default",
    profile_picture character varying(255) COLLATE pg_catalog."default",
    state character varying(255) COLLATE pg_catalog."default",
    street character varying(255) COLLATE pg_catalog."default",
    verification_code character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT customer_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.date_payment
(
    id_payment serial NOT NULL,
    date_payment character varying(255) COLLATE pg_catalog."default" NOT NULL,
    fk_id_invoice_id integer,
    fk_id_status_invoices_id integer,
    CONSTRAINT date_payment_pkey PRIMARY KEY (id_payment)
);

CREATE TABLE IF NOT EXISTS public.financial_information
(
    id_financial_information serial NOT NULL,
    credit_status boolean NOT NULL,
    current_position character varying(255) COLLATE pg_catalog."default",
    fk_account_id integer NOT NULL,
    marital_status character varying(255) COLLATE pg_catalog."default",
    month_income numeric(38, 2),
    type_ocupation character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT financial_information_pkey PRIMARY KEY (id_financial_information)
);

CREATE TABLE IF NOT EXISTS public.form
(
    id serial NOT NULL,
    income double precision,
    maximum_amount double precision,
    proof_of_income oid,
    CONSTRAINT form_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.installment
(
    id serial NOT NULL,
    amount double precision,
    due_date date,
    paid boolean,
    payment_date date,
    loan_id integer NOT NULL,
    CONSTRAINT installment_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.invoice_backup
(
    id serial NOT NULL,
    card_number character varying(255) COLLATE pg_catalog."default" NOT NULL,
    date_payment character varying(255) COLLATE pg_catalog."default" NOT NULL,
    invoice_value numeric(38, 2) NOT NULL,
    number_card character varying(255) COLLATE pg_catalog."default" NOT NULL,
    number_ff_installments integer NOT NULL,
    purchase_date character varying(255) COLLATE pg_catalog."default" NOT NULL,
    status_invoice boolean NOT NULL,
    total_value numeric(38, 2) NOT NULL,
    CONSTRAINT invoice_backup_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.invoices
(
    id_invoices serial NOT NULL,
    fk_id_account integer NOT NULL,
    number_card character varying(255) COLLATE pg_catalog."default" NOT NULL,
    number_of_installments integer NOT NULL,
    status_invoice boolean NOT NULL,
    total_value numeric(38, 2) NOT NULL,
    credit_card_id_credit_card integer,
    fk_card_seller integer,
    fk_product integer,
    fk_purchase_request integer,
    CONSTRAINT invoices_pkey PRIMARY KEY (id_invoices)
);

CREATE TABLE IF NOT EXISTS public.limit_credit_card
(
    id_limit_credit_card serial NOT NULL,
    fk_account_id integer NOT NULL,
    limit_credit numeric(38, 2) NOT NULL,
    CONSTRAINT limit_credit_card_pkey PRIMARY KEY (id_limit_credit_card)
);

CREATE TABLE IF NOT EXISTS public.loan
(
    id serial NOT NULL,
    amount_borrowed double precision,
    issue_date date,
    loan_due_date date,
    number_of_installments integer,
    payment_method integer,
    status integer,
    total_interest double precision,
    total_payable double precision,
    value_of_installments double precision,
    account_id integer,
    form_id integer,
    scheduled_payment_id integer,
    CONSTRAINT loan_pkey PRIMARY KEY (id),
    CONSTRAINT uk_5dw9qvc949jmy1wrwx5dqji9g UNIQUE (scheduled_payment_id),
    CONSTRAINT uk_euu4jjfywmma43cbx5fn8iulw UNIQUE (form_id)
);

CREATE TABLE IF NOT EXISTS public.notifications
(
    id integer NOT NULL,
    message oid,
    type character varying(255) COLLATE pg_catalog."default",
    account_id integer NOT NULL,
    CONSTRAINT notifications_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.password_credit_card
(
    id_password_credit_card serial NOT NULL,
    password_credit_card character varying(255) COLLATE pg_catalog."default" NOT NULL,
    fk_id_credit_card integer,
    CONSTRAINT password_credit_card_pkey PRIMARY KEY (id_password_credit_card)
);

CREATE TABLE IF NOT EXISTS public.payment_date
(
    id_payment_date bigserial NOT NULL,
    day_payment character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_update timestamp(6) without time zone,
    fk_credit_card integer,
    CONSTRAINT payment_date_pkey PRIMARY KEY (id_payment_date)
);

CREATE TABLE IF NOT EXISTS public.payment_history
(
    id serial NOT NULL,
    account_receiver character varying(255) COLLATE pg_catalog."default",
    agency_receiver character varying(255) COLLATE pg_catalog."default",
    amount numeric(38, 2),
    bank_name_receiver character varying(255) COLLATE pg_catalog."default",
    cpf_cnpj_receiver character varying(255) COLLATE pg_catalog."default",
    description character varying(255) COLLATE pg_catalog."default",
    name_receiver character varying(255) COLLATE pg_catalog."default",
    transaction_date timestamp(6) without time zone,
    account_id integer,
    scheduled_payment integer,
    status_id integer,
    transaction_type_id integer,
    CONSTRAINT payment_history_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.product
(
    id_product serial NOT NULL,
    barcode character varying(255) COLLATE pg_catalog."default" NOT NULL,
    description character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name_product character varying(255) COLLATE pg_catalog."default" NOT NULL,
    product_price numeric(38, 2) NOT NULL,
    quantity integer NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (id_product)
);

CREATE TABLE IF NOT EXISTS public.purchase_request
(
    id_purchase_request serial NOT NULL,
    date_buy character varying(255) COLLATE pg_catalog."default" NOT NULL,
    number_of_installments integer NOT NULL,
    fk_credit_card_id integer,
    fk_product_id integer,
    fk_seller_credit_card_id integer,
    CONSTRAINT purchase_request_pkey PRIMARY KEY (id_purchase_request)
);

CREATE TABLE IF NOT EXISTS public.request_limit
(
    id_request_limit serial NOT NULL,
    fk_account_id integer NOT NULL,
    value_request numeric(38, 2) NOT NULL,
    CONSTRAINT request_limit_pkey PRIMARY KEY (id_request_limit)
);

CREATE TABLE IF NOT EXISTS public.scheduled_payment
(
    id integer NOT NULL,
    account_recipient character varying(255) COLLATE pg_catalog."default" NOT NULL,
    agency_recipient character varying(255) COLLATE pg_catalog."default" NOT NULL,
    amount numeric(38, 2) NOT NULL,
    cnpj_receiver character varying(255) COLLATE pg_catalog."default",
    cpf_receiver character varying(255) COLLATE pg_catalog."default",
    description character varying(255) COLLATE pg_catalog."default" NOT NULL,
    end_date date,
    expired_date character varying(255) COLLATE pg_catalog."default" NOT NULL,
    start_date date NOT NULL,
    account_id integer NOT NULL,
    CONSTRAINT scheduled_payment_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.status
(
    id serial NOT NULL,
    status_name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT status_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.status_invoice
(
    id_status_invoice serial NOT NULL,
    status_invoice boolean NOT NULL,
    value_invoice numeric(38, 2) NOT NULL,
    fk_id_invoice integer,
    CONSTRAINT status_invoice_pkey PRIMARY KEY (id_status_invoice)
);

CREATE TABLE IF NOT EXISTS public.transaction_type
(
    id serial NOT NULL,
    transaction_type_name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT transaction_type_pkey PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.authentication
    ADD CONSTRAINT fk924k0b8fpt21lqn8ai5j4oj7x FOREIGN KEY (customer_id)
    REFERENCES public.customer (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.credit_card
    ADD CONSTRAINT fkeierrxc79d9dwybxk1xpqp59t FOREIGN KEY (fk_limit_card_id)
    REFERENCES public.limit_credit_card (id_limit_credit_card) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.credit_card_client
    ADD CONSTRAINT fk2ggx3q0qqyhsaylt5584ynuqd FOREIGN KEY (fk_password_credit_card_id)
    REFERENCES public.password_credit_card (id_password_credit_card) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.credit_card_client
    ADD CONSTRAINT fkbr4h7go10b7g3dc60ona43hvv FOREIGN KEY (fk_date_payment)
    REFERENCES public.payment_date (id_payment_date) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.credit_card_client
    ADD CONSTRAINT fkrydq5bvsmyptpdug0ffsdy7k9 FOREIGN KEY (fk_credit_card_id)
    REFERENCES public.credit_card (id_credit_card) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.custom_limit
    ADD CONSTRAINT fkg3iw33x28tonike7xe7vkg3ku FOREIGN KEY (fk_limit_card_id)
    REFERENCES public.limit_credit_card (id_limit_credit_card) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.date_payment
    ADD CONSTRAINT fk5aakrpdapqry1y4hf2m5ob4ee FOREIGN KEY (fk_id_status_invoices_id)
    REFERENCES public.status_invoice (id_status_invoice) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.date_payment
    ADD CONSTRAINT fkhp5d7tkd2mv40qv1ysxn98ge6 FOREIGN KEY (fk_id_invoice_id)
    REFERENCES public.invoices (id_invoices) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.installment
    ADD CONSTRAINT fkddvr1rongdlfl3pmj87eg48cy FOREIGN KEY (loan_id)
    REFERENCES public.loan (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.invoices
    ADD CONSTRAINT fkhhc2yy3n68g2y4pjqtik010q1 FOREIGN KEY (fk_purchase_request)
    REFERENCES public.purchase_request (id_purchase_request) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.invoices
    ADD CONSTRAINT fkjjuexi06coixbfy98btwr27vl FOREIGN KEY (fk_product)
    REFERENCES public.product (id_product) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.invoices
    ADD CONSTRAINT fkr7dolyxac3700edk3sy4lu26r FOREIGN KEY (credit_card_id_credit_card)
    REFERENCES public.credit_card (id_credit_card) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.invoices
    ADD CONSTRAINT fks35k306xnv6nsfxl0g8d6qj5u FOREIGN KEY (fk_card_seller)
    REFERENCES public.credit_card (id_credit_card) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.loan
    ADD CONSTRAINT fkgv9cgsh4k76wmaf83ktoekpub FOREIGN KEY (account_id)
    REFERENCES public.account (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.loan
    ADD CONSTRAINT fkji675vx3gp0pbp9g93i2etj1o FOREIGN KEY (form_id)
    REFERENCES public.form (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS uk_euu4jjfywmma43cbx5fn8iulw
    ON public.loan(form_id);


ALTER TABLE IF EXISTS public.loan
    ADD CONSTRAINT fkldlcqcdpryslw7ruy4456gmjt FOREIGN KEY (scheduled_payment_id)
    REFERENCES public.scheduled_payment (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS uk_5dw9qvc949jmy1wrwx5dqji9g
    ON public.loan(scheduled_payment_id);


ALTER TABLE IF EXISTS public.notifications
    ADD CONSTRAINT fkgeo42enre2dq66r36t5mc3kc FOREIGN KEY (account_id)
    REFERENCES public.account (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.password_credit_card
    ADD CONSTRAINT fk13onyq6r04r77mg00c19pb4s8 FOREIGN KEY (fk_id_credit_card)
    REFERENCES public.credit_card (id_credit_card) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.payment_date
    ADD CONSTRAINT fkphkpw5s2e9tjc7v91ansui54a FOREIGN KEY (fk_credit_card)
    REFERENCES public.credit_card (id_credit_card) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.payment_history
    ADD CONSTRAINT fk3772s6ixp8sl4m34kuphp2v37 FOREIGN KEY (status_id)
    REFERENCES public.status (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.payment_history
    ADD CONSTRAINT fk874nqkvs09nwc65unbho28b7p FOREIGN KEY (transaction_type_id)
    REFERENCES public.transaction_type (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.payment_history
    ADD CONSTRAINT fkd9anq3f2af6e2hm2onbrh8hcb FOREIGN KEY (account_id)
    REFERENCES public.account (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.payment_history
    ADD CONSTRAINT fkhysx4rv19iojtthxda9dptllb FOREIGN KEY (scheduled_payment)
    REFERENCES public.scheduled_payment (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.purchase_request
    ADD CONSTRAINT fk1b1816hr1yjw9valx2urb17wc FOREIGN KEY (fk_product_id)
    REFERENCES public.product (id_product) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.purchase_request
    ADD CONSTRAINT fkjrtqjt6s8q87tn52aqyyme2dr FOREIGN KEY (fk_seller_credit_card_id)
    REFERENCES public.credit_card (id_credit_card) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.purchase_request
    ADD CONSTRAINT fkrml0ip4c5yxjg97qljya2nd7k FOREIGN KEY (fk_credit_card_id)
    REFERENCES public.credit_card (id_credit_card) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.scheduled_payment
    ADD CONSTRAINT fkj22e0326mfsm541jkc23nrj3s FOREIGN KEY (account_id)
    REFERENCES public.account (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.status_invoice
    ADD CONSTRAINT fk9odkkdftmbp1g2j5koc7kpxgw FOREIGN KEY (fk_id_invoice)
    REFERENCES public.invoices (id_invoices) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

END;