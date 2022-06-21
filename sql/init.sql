CREATE TABLE IF NOT EXISTS "users" (
  id VARCHAR(40) PRIMARY KEY,
  created_utc TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS "user_info" (
  id VARCHAR(40) PRIMARY KEY,
  nickname VARCHAR(40) DEFAULT NULL,
  firstname VARCHAR(40) DEFAULT NULL,
  lastname VARCHAR(40) DEFAULT NULL
);

INSERT INTO users (id)
VALUES ('78a7d370-d884-11ec-9897-855f7eff4939');


INSERT INTO user_info (id, nickname, firstname, lastname)
VALUES ('78a7d370-d884-11ec-9897-855f7eff4939', 'FranTheHuman', 'Francisco', 'Perrotta');