
CREATE TABLE IF NOT EXISTS organisation (
    id uuid,
    orgname text,
    hod text,
    address text,
    establishmentyear timestamp,
    active boolean,
    createdate timestamp,
    updatedate timestamp,
    PRIMARY KEY(id)
);
