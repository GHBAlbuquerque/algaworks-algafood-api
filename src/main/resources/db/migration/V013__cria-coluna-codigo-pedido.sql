ALTER TABLE pedido ADD COLUMN codigo varchar(36) NOT NULL after id;
update pedido set codigo = uuid();

ALTER TABLE pedido add constraint uk_pedido_codigo unique(codigo);

