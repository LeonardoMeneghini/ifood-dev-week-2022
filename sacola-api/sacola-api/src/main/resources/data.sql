INSERT INTO restaurante (id, cep, complemento, nome) VALUES
(1L, '90410-159', 'Perto da Avenida Júpiter','Xis do Joquinha'),
(2L, '90210-100', 'Ao lado da Prefeitura','Lancheria 10');

INSERT INTO cliente (id, cep, complemento, nome) VALUES
(1L, '90410-110', 'Paralelo á rua Indenpendencia','Maria Bernadete'),
(2L, '90410-100', 'sem referencias','Sergio Souza'),
(3L, '90351-100', 'sem referencias','Aloir Menezes');

INSERT INTO produto (id, disponivel, nome, valor_unitario, restaurante_id) VALUES
(1L, true, 'Pastel', 5.0, 1L),
(2L, true, 'Coxinha', 7.0, 1L),
(3L, true, 'Pizza' , 35.0, 2L);

INSERT INTO sacola (id, forma_pagamento, fechada, valor_total, cliente_id) VALUES
(1L, 0, false, 0.0, 1L),
(2L, 1, false, 0.0, 2L),
(3L, 0, false, 0.0, 3L);




