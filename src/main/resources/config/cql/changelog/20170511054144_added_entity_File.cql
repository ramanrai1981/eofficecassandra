
CREATE TABLE IF NOT EXISTS file (
    id uuid,
    fileNo text,
    title text,
    tag text,
    uploadDate timestamp,
    status boolean,
    PRIMARY KEY(id)
);
