CREATE TABLE IF NOT EXISTS teacher(
    id INT PRIMARY KEY, -- mesmo valor do id da tabela person
    specialty varchar(20) null,
    status BOOLEAN not null,
    FOREIGN KEY (id) REFERENCES person(id)
);