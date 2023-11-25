drop table if exists app_config, commissions, accounts;

CREATE TABLE IF NOT EXISTS app_config
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY not null,
    secret_key varchar(10) not null
    );

CREATE TABLE IF NOT EXISTS commissions
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY not null,
    currency_from varchar(3) not null,
    currency_to varchar(3) not null,
    commission int not null,
    conversion_rate decimal not null
    );

CREATE TABLE IF NOT EXISTS accounts
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY not null,
    currency_name varchar(3) not null,
    balance decimal not null
    );