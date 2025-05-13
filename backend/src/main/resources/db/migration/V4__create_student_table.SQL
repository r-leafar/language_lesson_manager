CREATE TABLE IF NOT EXISTS student(
    id INT PRIMARY KEY, -- mesmo valor do id da tabela person
    addressid INT not null,
    contactid INT not null,
    FOREIGN KEY (id) REFERENCES person(id),
    FOREIGN KEY (addressid) REFERENCES residential_address(id),
    FOREIGN KEY (contactid) REFERENCES contact(id)
);