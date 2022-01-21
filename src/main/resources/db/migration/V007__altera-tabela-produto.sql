create table foto_produto (
	id bigint not null auto_increment, 
	content_type varchar(255) not null, 
	descricao varchar(100), 
	nome varchar(60) not null, 
	tamanho bigint, 

	primary key (id)
) engine=InnoDB;

alter table produto
ADD COLUMN foto_id bigint AFTER ativo;

alter table produto add constraint fk_produto_foto
foreign key (foto_id) references foto_produto (id);

