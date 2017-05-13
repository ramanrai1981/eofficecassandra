
CREATE TABLE IF NOT EXISTS employee (
    id uuid,
    empid text,
    empname text,
    department text,
    designation text,
    mobilenumber int,
    emailid text,
    dateofbirth timestamp,
    dateofjoining timestamp,
    relievingdate timestamp,
    active boolean,
    createdate timestamp,
    updatedate timestamp,
    PRIMARY KEY(id)
);
