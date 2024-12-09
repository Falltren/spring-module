CREATE TABLE IF NOT EXISTS users (
    id bigserial primary key,
    username varchar(255),
    password varchar(255),
    role varchar(255),
    is_account_non_locked boolean
);

