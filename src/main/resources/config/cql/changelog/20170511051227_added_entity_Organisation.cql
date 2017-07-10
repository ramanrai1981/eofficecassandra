
CREATE TABLE IF NOT EXISTS organisation (
    id uuid,
    orgname text,
    address text,
    active boolean,
    createdate timestamp,
    updatedate timestamp,
    owner text,
    establishmentdate timestamp,
    PRIMARY KEY(id)
);
