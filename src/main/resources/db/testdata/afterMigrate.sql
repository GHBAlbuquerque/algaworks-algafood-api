set foreign_key_checks = 0;

delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from usuario;
delete from usuario_grupo;

set foreign_key_checks = 1;



alter table cidade auto_increment = 1;
alter table cozinha auto_increment = 1;
alter table estado auto_increment = 1;
alter table forma_pagamento auto_increment = 1;
alter table grupo auto_increment = 1;
alter table permissao auto_increment = 1;
alter table produto auto_increment = 1;
alter table restaurante auto_increment = 1;
alter table usuario auto_increment = 1;



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

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');
insert into permissao (id, nome, descricao) values (3, 'CONSULTAR_RESTAURANTES', 'Permite consultar restaurantes');
insert into permissao (id, nome, descricao) values (4, 'EDITAR_RESTAURANTES', 'Permite editar restaurantes');

insert into grupo (id, nome) values (1, 'ADMIN_RESTAURANTE');
insert into grupo (id, nome) values (2, 'FUNCIONARIO_RESTAURANTE');

insert into usuario (id, nome, email, senha, data_cadastro) values (1, 'Lodun Iswil', 'lodun@gmail.com', '12345aa', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (2, 'Fuvain Iswil', 'fuvain@gmail.com', '12345aa', utc_timestamp);

insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao) values ('Senhor Glutão', 12.50, 5, 1, '38400999', 'Rua João Pinheiro', '1000', 'Centro', utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao) values ('Thai Gourmet', 15.50, 1, 2, '38400999', 'Rua João Pinheiro', '1000', 'Centro', utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao) values ('Larica', 3.00, 5, 3, '38400999', 'Rua João Pinheiro', '1000', 'Centro',  utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao) values ('TukTuk Delivery', 1.00, 6, 4, '38400999', 'Rua João Pinheiro', '1000', 'Centro',  utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao) values ('La Pururuca', 0.50, 7, 5, '38400999', 'Rua João Pinheiro', '1000', 'Centro',  utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao) values ('Fish and Chips', 18.50, 2, 1, '38400999', 'Rua João Pinheiro', '1000', 'Centro',  utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao) values ('Burguer King', 0, 8, 2, '38400999', 'Rua João Pinheiro', '1000', 'Centro',  utc_timestamp, utc_timestamp);

insert into forma_pagamento (id, descricao) values (1, 'Cartão de crédito');
insert into forma_pagamento (id, descricao) values (2, 'Cartão de débito');
insert into forma_pagamento (id, descricao) values (3, 'Dinheiro');


insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);
insert into grupo_permissao (grupo_id, permissao_id) values (1, 1), (1, 2), (1, 3), (1, 4), (2, 1), (2, 3);
insert into usuario_grupo (usuario_id, grupo_id) values (1, 1), (1, 2), (2, 1), (2, 2);
insert into restaurante_responsavel (restaurante_id, usuario_id) values (1, 1), (1, 2), (2, 2), (3, 2), (4, 2), (5, 1);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 5);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 2);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 5);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 5);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Whooper', 'Acompanha Coca-cola e fritas', 22, 1, 7);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Peixe com fritas', 'Tradicional prato inglës', 48, 1, 7);

