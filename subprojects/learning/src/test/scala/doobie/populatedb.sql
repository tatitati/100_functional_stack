
CREATE TABLE country (
  code       character(2)  NOT NULL,
  name       text          NOT NULL,
  population integer       NOT NULL,
  gnp        numeric(10,2)
  -- more columns, but we won't use them here
);

INSERT INTO "public"."country" ("code", "name", "population", "gnp") VALUES ('SP', 'spain', '500', '8');
INSERT INTO "public"."country" ("code", "name", "population", "gnp") VALUES ('IT', 'italy', '600', '3');
INSERT INTO "public"."country" ("code", "name", "population", "gnp") VALUES ('FR', 'France', '650', '7');