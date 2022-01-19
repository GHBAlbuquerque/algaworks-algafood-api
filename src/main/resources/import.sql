insert into cozinha (id, nome) values (1, 'Tailandesa'); 
insert into cozinha (id, nome) values (2, 'Inglesa'); 
insert into cozinha (id, nome) values (3, 'Japonesa'); 
insert into cozinha (id, nome) values (4, 'Coreana'); 
insert into cozinha (id, nome) values (5, 'Brasileira');
insert into cozinha (id, nome) values (6, 'Indiana');
insert into cozinha (id, nome) values (7, 'Churrasco');
insert into cozinha (id, nome) values (8, 'Hamburguer');

insert into estado (id, nome, sigla) values (1, 'Amapá', 'AP');
insert into estado (id, nome, sigla) values (2, 'Amazonas', 'AM');
insert into estado (id, nome, sigla) values (3, 'Mato Grosso', 'MT');
insert into estado (id, nome, sigla) values (4, 'Espírito Santo', 'ES');
insert into estado (id, nome, sigla) values (5, 'São Paulo', 'SP');
insert into estado (id, nome, sigla) values (6, 'Minas Gerais', 'MG');

insert into cidade (id, nome, estado_id) values (1, 'Uberlândia', 6);
insert into cidade (id, nome, estado_id) values (2, 'Belo Horizonte', 6);
insert into cidade (id, nome, estado_id) values (3, 'São Paulo', 5);
insert into cidade (id, nome, estado_id) values (4, 'Campinas', 5);
insert into cidade (id, nome, estado_id) values (5, 'Manaus', 2);

insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao) values ('Senhor Glutão', 12.50, 5, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro', utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao) values ('Thai Gourmet', 15.50, 1, 2, '38400-999', 'Rua João Pinheiro', '1000', 'Centro', utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao) values ('Larica', 3.00, 5, 3, '38400-999', 'Rua João Pinheiro', '1000', 'Centro',  utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao) values ('TukTuk Delivery', 1.00, 6, 4, '38400-999', 'Rua João Pinheiro', '1000', 'Centro',  utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao) values ('La Pururuca', 0.50, 7, 5, '38400-999', 'Rua João Pinheiro', '1000', 'Centro',  utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao) values ('Fish and Chips', 18.50, 2, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro',  utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao) values ('Burguer King', 0, 8, 2, '38400-999', 'Rua João Pinheiro', '1000', 'Centro',  utc_timestamp, utc_timestamp);

insert into forma_pagamento (id, descricao) values (1, 'Cartão de crédito');
insert into forma_pagamento (id, descricao) values (2, 'Cartão de débito');
insert into forma_pagamento (id, descricao) values (3, 'Dinheiro');

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurante_formas_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);
