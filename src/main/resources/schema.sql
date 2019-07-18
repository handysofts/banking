CREATE TABLE IF NOT EXISTS customer (
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR2(50) NOT NULL,
  last_name  VARCHAR2(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS account (
  id          VARCHAR2(50) UNIQUE,
  customer_id BIGINT,
  currency    VARCHAR2(3) NOT NULL,
  balance     DECIMAL     NOT NULL,
  FOREIGN KEY (customer_id) REFERENCES customer (id)
);

CREATE TABLE trns (
  id        BIGINT AUTO_INCREMENT PRIMARY KEY,
  from_acc  VARCHAR2(50),
  to_acc    VARCHAR2(50),
  amount    DECIMAL,
  date_time TIMESTAMP,
  FOREIGN KEY (from_acc) REFERENCES account (id),
  FOREIGN KEY (to_acc) REFERENCES account (id)
);