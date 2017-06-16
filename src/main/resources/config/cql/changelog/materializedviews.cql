
    CREATE MATERIALIZED VIEW organisationBylogin AS
    SELECT id FROM organisation
    WHERE createdby IS NOT NULL AND id IS NOT NULL
    PRIMARY KEY(createdby, id);


