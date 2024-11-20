CREATE TABLE TB_ENERGSYSTEM_EMPRESA (
                                        ID_EMPRESA INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                        NOME VARCHAR(255) NOT NULL,
                                        CNPJ CHAR(14) UNIQUE NOT NULL,
                                        EMAIL VARCHAR(255) UNIQUE NOT NULL,
                                        ESTADO VARCHAR(50),
                                        KWH INTEGER,
                                        TIPO_ENERGIA VARCHAR(50)
);
CREATE TABLE TB_ENERGSYSTEM_LOGIN (
                                      ID_LOGIN INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                      CNPJ CHAR(14) NOT NULL,
                                      SENHA VARCHAR(255) NOT NULL,
                                      STATUS VARCHAR(50),
                                      ID_EMPRESA INTEGER,
                                      CONSTRAINT FK_LOGIN_EMPRESA FOREIGN KEY (ID_EMPRESA)
                                          REFERENCES TB_ENERGSYSTEM_EMPRESA(ID_EMPRESA)
                                          ON DELETE CASCADE,
                                      CONSTRAINT FK_LOGIN_CNPJ FOREIGN KEY (CNPJ)
                                          REFERENCES TB_ENERGSYSTEM_EMPRESA(CNPJ),
                                      CONSTRAINT UQ_LOGIN_CNPJ UNIQUE (CNPJ)
);



SELECT SQL_TEXT,
       PARSING_SCHEMA_NAME,
       EXECUTIONS,
       LAST_ACTIVE_TIME
FROM V$SQL
WHERE PARSING_SCHEMA_NAME = 'RM557252'
ORDER BY LAST_ACTIVE_TIME DESC;