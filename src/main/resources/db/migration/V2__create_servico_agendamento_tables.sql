CREATE TABLE servico(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    duracao INTEGER NOT NULL,
    preco FLOAT NOT NULL
);

CREATE TABLE agendamento(
     id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
     cliente_id BIGINT NOT NULL,
     servico_id BIGINT NOT NULL,
     data DATE NOT NULL,
     hora_inicio TIME,
     hora_fim TIME NOT NULL,
     status TEXT,

     CONSTRAINT fk_cliente_agendamento
        FOREIGN KEY (cliente_id)
        REFERENCES cliente(id),

     CONSTRAINT fk_servico_agendamento
        FOREIGN KEY (servico_id)
        REFERENCES servico(id)
);




