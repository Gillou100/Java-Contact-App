CREATE TABLE user (
  iduser INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(50) NOT NULL,
  pathFolderContact VARCHAR(100) NOT NULL,
PRIMARY KEY (iduser));
  
CREATE TABLE person (
  idperson INT NOT NULL AUTO_INCREMENT,
  user_id INT NOT NULL,
  lastname VARCHAR(50) NOT NULL,
  firstname VARCHAR(50) NOT NULL,
  nickname VARCHAR(50) NOT NULL,
  phone_number VARCHAR(50) NULL,
  email_address VARCHAR(100) NULL,
  birth_date DATETIME NULL,
  url_photo VARCHAR(100) NULL,
  category INT NULL,
  PRIMARY KEY (idperson),
  INDEX user_fk_idx (user_id ASC),
  CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES user (iduser));

CREATE TABLE address (
  idaddress INT NOT NULL AUTO_INCREMENT,
  person_id INT NOT NULL,
  boitePostale VARCHAR(50) NULL,
  adresseEtendue VARCHAR(50) NULL,
  rue VARCHAR(50) NULL,
  ville VARCHAR(50) NULL,
  regionEtatProvince VARCHAR(50) NULL,
  codePostal INT NULL,
  pays VARCHAR(50) NULL,
  PRIMARY KEY (idaddress),
  INDEX person_fk_idx (person_id ASC),
  CONSTRAINT person_fk FOREIGN KEY (person_id) REFERENCES person (idperson));