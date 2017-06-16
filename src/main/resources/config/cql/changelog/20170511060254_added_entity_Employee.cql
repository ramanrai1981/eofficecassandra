
CREATE TABLE IF NOT EXISTS employee (
    id uuid,
    empid text,
    empname text,
    department text,
    designation text,
    emailid text,
    dateofbirth timestamp,
    dateofjoining timestamp,
    relievingdate timestamp,
    active boolean,
    createdate timestamp,
    updatedate timestamp,
    mobilenumber text,
    createdby text,
    PRIMARY KEY(id)
);
