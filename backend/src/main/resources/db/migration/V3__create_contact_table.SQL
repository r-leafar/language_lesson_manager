CREATE TABLE IF NOT EXISTS contact(
    id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    contact varchar(50) null,
    whatsapp varchar(50) not null,
    email varchar(50) null
);