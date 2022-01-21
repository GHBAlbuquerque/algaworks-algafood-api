create table item_pedido (
	id bigint not null auto_increment,
	observacao varchar(255), 
	preco_total decimal(19,2) not null, 
	preco_unitario decimal(19,2) not null, 
	quantidade integer not null, 
	produto_id bigint not null, 
	itens_id bigint not null, 
	
	primary key (id)
) engine=InnoDB;


create table pedido (
	id bigint not null auto_increment, 
	data_cancelamento date, 
	data_confirmacao date, 
	data_criacao date not null, 
	data_entrega date, 
	endereco_bairro varchar(255) not null, 
	endereco_cep varchar(255) not null, 
	endereco_complemento varchar(255), 
	endereco_logradouro varchar(255) not null, 
	endereco_numero varchar(255) not null, 
	status integer not null, 
	subtotal decimal(19,2) not null, 
	taxa_frete decimal(19,2) not null, 
	valor_total decimal(19,2) not null, 
	cliente_id bigint not null, 
	endereco_cidade_id bigint not null, 
	forma_pagamento_id bigint not null, 
	restaurante_id bigint not null, 
	
	primary key (id)
) engine=InnoDB;


alter table item_pedido add constraint fk_item_pedido_produto 
foreign key (produto_id) references produto (id);

alter table item_pedido add constraint  fk_item_pedido_pedido
foreign key (itens_id) references pedido (id);

alter table pedido add constraint  fk_pedido_usuario 
foreign key (cliente_id) references usuario (id);

alter table pedido add constraint  fk_pedido_cidade
foreign key (endereco_cidade_id) references cidade (id);

alter table pedido add constraint  fk_pedido_forma_pagamento
foreign key (forma_pagamento_id) references forma_pagamento (id);

alter table pedido add constraint  fk_pedido_restaurante
foreign key (restaurante_id) references restaurante (id);
