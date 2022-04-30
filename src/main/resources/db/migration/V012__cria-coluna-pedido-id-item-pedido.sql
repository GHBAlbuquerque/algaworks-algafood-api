ALTER TABLE item_pedido
DROP FOREIGN KEY fk_item_pedido_pedido;

ALTER TABLE item_pedido
DROP COLUMN itens_id,

DROP INDEX fk_item_pedido_pedido;

ALTER TABLE item_pedido ADD COLUMN pedido_id BIGINT NOT NULL;

update item_pedido set pedido_id = 1;

alter table item_pedido add constraint fk_item_pedido_pedido
foreign key (pedido_id) references pedido (id);

