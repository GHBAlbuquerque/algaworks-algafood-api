create table cidade (
	id bigint not null auto_increment,
	nome varchar(80) not null,
	estado_id bigint not null,
	
	primary key (id)
) engine=InnoDB default charset=utf8;

create table forma_pagamento (
	id bigint not null auto_increment,
	descricao varchar(80) not null,

	primary key (id)
) engine=InnoDB default charset=utf8;

create table grupo (
	id bigint not null auto_increment,
	nome varchar(60) not null,

	primary key (id)
) engine=InnoDB default charset=utf8;

create table permissao (
	id bigint not null auto_increment,
	nome varchar(60) not null,
	descricao varchar(80) not null,

	primary key (id)
) engine=InnoDB default charset=utf8;

create table produto (
	id bigint not null auto_increment,
	restaurante_id bigint not null,
	nome varchar(80) not null,
	descricao text not null,
	preco decimal(10,2) not null,
	ativo tinyint(1) not null,
	
	primary key (id)
) engine=InnoDB default charset=utf8;

create table usuario (
	id bigint not null auto_increment,
	nome varchar(60) not null,
	email varchar(50) not null,
	senha varchar(50) not null,
	data_cadastro datetime not null,
	

	primary key (id)
) engine=InnoDB default charset=utf8;

create table restaurante (
	id bigint not null auto_increment,
	nome varchar(60) not null,
	taxa_frete bigint(20) not null,
	cozinha_id bigint not null,
	data_cadastro datetime not null,
	data_atualizacao datetime not null,
	endereco_logradouro varchar(80) not null,
	endereco_numero varchar(10) not null,
	endereco_complemento varchar(10) not null,
	endereco_bairro varchar(60) not null,
	endereco_cidade_id bigint not null,
	endereco_cep varchar(8) not null,

	primary key (id)
) engine=InnoDB default charset=utf8;

