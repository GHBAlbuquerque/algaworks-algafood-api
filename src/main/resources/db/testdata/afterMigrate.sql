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
delete from pedido;
delete from item_pedido;

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
insert into permissao (id, nome, descricao) values (3, 'CONSULTAR_FORMAS_PAGAMENTO', 'Permite consultar formas de pagamento');
insert into permissao (id, nome, descricao) values (4, 'EDITAR_FORMAS_PAGAMENTO', 'Permite criar ou editar formas de pagamento');
insert into permissao (id, nome, descricao) values (5, 'CONSULTAR_CIDADES', 'Permite consultar cidades');
insert into permissao (id, nome, descricao) values (6, 'EDITAR_CIDADES', 'Permite criar ou editar cidades');
insert into permissao (id, nome, descricao) values (7, 'CONSULTAR_ESTADOS', 'Permite consultar estados');
insert into permissao (id, nome, descricao) values (8, 'EDITAR_ESTADOS', 'Permite criar ou editar estados');
insert into permissao (id, nome, descricao) values (9, 'CONSULTAR_USUARIOS', 'Permite consultar usuários');
insert into permissao (id, nome, descricao) values (10, 'EDITAR_USUARIOS', 'Permite criar ou editar usuários');
insert into permissao (id, nome, descricao) values (11, 'CONSULTAR_RESTAURANTES', 'Permite consultar restaurantes');
insert into permissao (id, nome, descricao) values (12, 'EDITAR_RESTAURANTES', 'Permite criar, editar ou gerenciar restaurantes');
insert into permissao (id, nome, descricao) values (13, 'CONSULTAR_PRODUTOS', 'Permite consultar produtos');
insert into permissao (id, nome, descricao) values (14, 'EDITAR_PRODUTOS', 'Permite criar ou editar produtos');
insert into permissao (id, nome, descricao) values (15, 'CONSULTAR_PEDIDOS', 'Permite consultar pedidos');
insert into permissao (id, nome, descricao) values (16, 'GERENCIAR_PEDIDOS', 'Permite gerenciar pedidos');
insert into permissao (id, nome, descricao) values (17, 'GERAR_RELATORIOS', 'Permite gerar relatórios');

insert into grupo (id, nome) values (1, 'GERENTE');
insert into grupo (id, nome) values (2, 'VENDEDOR');
insert into grupo (id, nome) values (3, 'SECRETÁRIA');
insert into grupo (id, nome) values (4, 'CADASTRADOR');

insert into grupo_permissao (grupo_id, permissao_id)
select 1, id from permissao;

insert into grupo_permissao (grupo_id, permissao_id)
select 2, id from permissao where nome like 'CONSULTAR_%';

insert into grupo_permissao (grupo_id, permissao_id) values (2, 14);

insert into grupo_permissao (grupo_id, permissao_id)
select 3, id from permissao where nome like 'CONSULTAR_%';

insert into grupo_permissao (grupo_id, permissao_id)
select 4, id from permissao where nome like '%_RESTAURANTES' or nome like '%_PRODUTOS';

insert into usuario (id, nome, email, senha, data_cadastro) values (1, 'Fred Jolie', 'jolie@gmail.com', '$2a$12$z2U9xVyBeRMiTp1QcTMm6.Zs0dOUiVUYPSPO7OhaGFFrSfOwMlAki', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (2, 'Felicity Wilson', 'wilson@gmail.com', '$2a$12$z2U9xVyBeRMiTp1QcTMm6.Zs0dOUiVUYPSPO7OhaGFFrSfOwMlAki', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (3, 'James Doop', 'doop@gmail.com', '$2a$12$z2U9xVyBeRMiTp1QcTMm6.Zs0dOUiVUYPSPO7OhaGFFrSfOwMlAki', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (4, 'Heather Connor', 'connor@gmail.com', '$2a$12$z2U9xVyBeRMiTp1QcTMm6.Zs0dOUiVUYPSPO7OhaGFFrSfOwMlAki', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (5, 'Rick Snozcumber', 'snozcumber@gmail.com', '$2a$12$z2U9xVyBeRMiTp1QcTMm6.Zs0dOUiVUYPSPO7OhaGFFrSfOwMlAki', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (6, 'Zoe Ramsbottom', 'ramsbottom@gmail.com', '$2a$12$z2U9xVyBeRMiTp1QcTMm6.Zs0dOUiVUYPSPO7OhaGFFrSfOwMlAki', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (7, 'Jenna Fish', 'fish@gmail.com', '$2a$12$z2U9xVyBeRMiTp1QcTMm6.Zs0dOUiVUYPSPO7OhaGFFrSfOwMlAki', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (8, 'Warwick Cockle', 'fuvain@gmail.com', '$2a$12$z2U9xVyBeRMiTp1QcTMm6.Zs0dOUiVUYPSPO7OhaGFFrSfOwMlAki', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (9, 'Dan Bundler', 'bundler@gmail.com', '$2a$12$z2U9xVyBeRMiTp1QcTMm6.Zs0dOUiVUYPSPO7OhaGFFrSfOwMlAki', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (10, 'Zach Nolan', 'nolan@gmail.com', '$2a$12$z2U9xVyBeRMiTp1QcTMm6.Zs0dOUiVUYPSPO7OhaGFFrSfOwMlAki', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (11, 'Dan Meadows', 'meadows@gmail.com', '$2a$12$z2U9xVyBeRMiTp1QcTMm6.Zs0dOUiVUYPSPO7OhaGFFrSfOwMlAki', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (12, 'Beth Hemingway', 'hemingway@gmail.com', '$2a$12$z2U9xVyBeRMiTp1QcTMm6.Zs0dOUiVUYPSPO7OhaGFFrSfOwMlAki', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (13, 'Roy Fish', 'rfish@gmail.com', '$2a$12$z2U9xVyBeRMiTp1QcTMm6.Zs0dOUiVUYPSPO7OhaGFFrSfOwMlAki', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (14, 'Luke Pitt', 'pitt@gmail.com', '$2a$12$z2U9xVyBeRMiTp1QcTMm6.Zs0dOUiVUYPSPO7OhaGFFrSfOwMlAki', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (15, 'Saskia Schwartz', 'Schwartz@gmail.com', '$2a$12$z2U9xVyBeRMiTp1QcTMm6.Zs0dOUiVUYPSPO7OhaGFFrSfOwMlAki', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (16, 'Sabrina Zuniga', 'Zuniga@gmail.com', '$2a$12$z2U9xVyBeRMiTp1QcTMm6.Zs0dOUiVUYPSPO7OhaGFFrSfOwMlAki', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (17, 'Shakira Larsen', 'Larsen@gmail.com', '$2a$12$z2U9xVyBeRMiTp1QcTMm6.Zs0dOUiVUYPSPO7OhaGFFrSfOwMlAki', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (18, 'Sumaya Burton', 'hemingway@gmail.com', '$2a$12$z2U9xVyBeRMiTp1QcTMm6.Zs0dOUiVUYPSPO7OhaGFFrSfOwMlAki', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (19, 'Serena Rice', 'Rice@gmail.com', '$2a$12$z2U9xVyBeRMiTp1QcTMm6.Zs0dOUiVUYPSPO7OhaGFFrSfOwMlAki', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (20, 'Sylvia Lane', 'Lane@gmail.com', '$2a$12$z2U9xVyBeRMiTp1QcTMm6.Zs0dOUiVUYPSPO7OhaGFFrSfOwMlAki', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (21, 'Aurora Gemini', 'auroragemini0@gmail.com', '$2a$12$z2U9xVyBeRMiTp1QcTMm6.Zs0dOUiVUYPSPO7OhaGFFrSfOwMlAki', utc_timestamp);

insert into usuario_grupo (usuario_id, grupo_id) values (1, 1), (2, 2), (3, 3), (4, 4), (5,2), (6,2), (7,1);

insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao, ativo, aberto) values ('Senhor Glutão', 12.50, 5, 1, '38400999', 'Rua João Pinheiro', '1000', 'Centro', utc_timestamp, utc_timestamp, true, true);
insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao, ativo, aberto) values ('Thai Gourmet', 15.50, 1, 2, '38400999', 'Rua João Pinheiro', '1000', 'Centro', utc_timestamp, utc_timestamp, true, true);
insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao, ativo, aberto) values ('Larica', 3.00, 5, 3, '38400999', 'Rua João Pinheiro', '1000', 'Centro',  utc_timestamp, utc_timestamp, true, true);
insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao, ativo, aberto) values ('TukTuk Delivery', 1.00, 6, 4, '38400999', 'Rua João Pinheiro', '1000', 'Centro',  utc_timestamp, utc_timestamp, true, true);
insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao, ativo, aberto) values ('La Pururuca', 0.50, 7, 5, '38400999', 'Rua João Pinheiro', '1000', 'Centro',  utc_timestamp, utc_timestamp, true, true);
insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao, ativo, aberto) values ('Fish and Chips', 18.50, 2, 1, '38400999', 'Rua João Pinheiro', '1000', 'Centro',  utc_timestamp, utc_timestamp, true, true);
insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao, ativo, aberto) values ('Burguer King', 0, 8, 2, '38400999', 'Rua João Pinheiro', '1000', 'Centro',  utc_timestamp, utc_timestamp, true, true);

insert into forma_pagamento (id, descricao) values (1, 'Cartão de crédito');
insert into forma_pagamento (id, descricao) values (2, 'Cartão de débito');
insert into forma_pagamento (id, descricao) values (3, 'Dinheiro');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);

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
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Feijoada da Casa', 'Clássico prato brasileiro a base de feijões e carne', 180, 0, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Lombo à milanesa', 'Deliciosas fatias de lombo feitas à milanesa. Acompanha arroz e fritas.', 98, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Hamburguer do Chef', 'Delicioso hamburguer artesanal com queijo prato, alface, tomate e maionese da casa no pão com gergelim.', 38.50, 1, 1);


insert into pedido (id, codigo, restaurante_id, cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, subtotal, taxa_frete, valor_total)
values (1, 'f9981ca4-5a5e-4da3-af04-933861df3e55', 1, 21, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil',
        'CRIADO', utc_timestamp, 298.90, 10, 308.90);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (1, 1, 1, 1, 78.9, 78.9, null);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');


insert into pedido (id, codigo, restaurante_id, cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, subtotal, taxa_frete, valor_total)
values (2, 'd178b637-a785-4768-a3cb-aa1ce5a8cdab', 4, 21, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
        'CRIADO', utc_timestamp, 79, 0, 79);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (3, 2, 6, 1, 79, 79, 'Ao ponto');


insert into pedido (id, codigo, restaurante_id, cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total)
values (3, 'b5741512-8fbc-47fa-9ac1-b530354fc0ff', 1, 1, 1, 1, '38400-222', 'Rua Natal', '200', null, 'Brasil',
        'ENTREGUE', '2019-10-30 21:10:00', '2019-10-30 21:10:45', '2019-10-30 21:55:44', 110, 10, 120);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (4, 3, 2, 1, 110, 110, null);


insert into pedido (id, codigo, restaurante_id, cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total)
values (4, '5c621c9a-ba61-4454-8631-8aabefe58dc2', 1, 21, 1, 1, '38400-800', 'Rua Fortaleza', '900', 'Apto 504', 'Centro',
        'ENTREGUE', '2019-11-02 20:34:04', '2019-11-02 20:35:10', '2019-11-02 21:10:32', 174.4, 5, 179.4);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (5, 4, 3, 2, 87.2, 174.4, null);


insert into pedido (id, codigo, restaurante_id, cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total)
values (5, '8d774bcf-b238-42f3-aef1-5fb388754d63', 1, 21, 2, 1, '38400-200', 'Rua 10', '930', 'Casa 20', 'Martins',
        'ENTREGUE', '2019-11-02 21:00:30', '2019-11-02 21:01:21', '2019-11-02 21:20:10', 87.2, 10, 97.2);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (6, 5, 3, 1, 87.2, 87.2, null);
