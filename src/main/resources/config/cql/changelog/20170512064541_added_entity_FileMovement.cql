
CREATE TABLE IF NOT EXISTS fileMovement (
    id uuid,
    fileId uuid,
    markFrom text,
    markTo text,
    fileName text,
    markDate timestamp,
    updateDate timestamp,
    actionStatus text,
    comment text,
    PRIMARY KEY(id)
);
