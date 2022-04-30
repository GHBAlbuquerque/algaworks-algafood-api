ALTER TABLE produto
DROP FOREIGN KEY fk_produto_foto;

ALTER TABLE produto
DROP COLUMN foto_id,

DROP INDEX fk_produto_foto;

DROP TABLE foto_produto;