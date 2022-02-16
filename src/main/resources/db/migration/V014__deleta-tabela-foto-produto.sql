ALTER TABLE `algafood`.`produto`
DROP FOREIGN KEY `fk_produto_foto`;
ALTER TABLE `algafood`.`produto`
DROP COLUMN `foto_id`,
DROP INDEX `fk_produto_foto` ;
;


DROP TABLE `algafood`.`foto_produto`;