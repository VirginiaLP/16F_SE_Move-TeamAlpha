DROP TABLE IF EXISTS Student;
CREATE TABLE Student(
    banner_id   INTEGER,
    first_name  VARCHAR(30),
    last_name   VARCHAR(30),
    class       VARCHAR(5),
    PRIMARY KEY(banner_id)
);

CREATE TABLE SECTION(
    crn             INTEGER,
    term            VARCHAR(6),
    term_length     VARCHAR(30),
    class_subject   VARCHAR(10),
    class_number    VARCHAR(3),
    class_title     VARCHAR(70),
    class_suffix    VARCHAR(5),
    year            INTEGER,
    time_start      INTEGER,
    time_end        INTEGER,
    PRIMARY KEY(crn)
);

