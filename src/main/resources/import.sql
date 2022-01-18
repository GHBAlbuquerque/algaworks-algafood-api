insert into cozinha (id, nome) values (1, 'Tailandesa'); 
insert into cozinha (id, nome) values (2, 'Inglesa'); 
insert into cozinha (id, nome) values (3, 'Japonesa'); 
insert into cozinha (id, nome) values (4, 'Coreana'); 
insert into cozinha (id, nome) values (5, 'Brasileira');
insert into cozinha (id, nome) values (6, 'Indiana');
insert into cozinha (id, nome) values (7, 'Churrasco');
insert into cozinha (id, nome) values (8, 'Hamburguer');

insert into restaurante (nome, taxa_frete, cozinha_id) values ('Senhor Glutão', 12.50, 5);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Gourmet', 15.50, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Larica', 3.00, 5);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('TukTuk Delivery', 1.00, 6);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('La Pururuca', 0.50, 7);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Fish and Chips', 18.50, 2);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Burguer King', 0, 8);

insert into estado (id, nome, sigla) values (1, 'Amapá', 'AP');
insert into estado (id, nome, sigla) values (2, 'Amazonas', 'AM');
insert into estado (id, nome, sigla) values (3, 'Mato Grosso', 'MT');
insert into estado (id, nome, sigla) values (4, 'Espírito Santo', 'ES');
insert into estado (id, nome, sigla) values (5, 'São Paulo', 'SP');
insert into estado (id, nome, sigla) values (6, 'Minas Gerais', 'MG');


-- TODO: fazer inserções de outras entidades