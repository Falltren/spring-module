CREATE TABLE IF NOT EXISTS audit (
    id bigserial primary key,
    username varchar(255),
    action varchar(255),
    description varchar(255),
    timestamp bigint
);