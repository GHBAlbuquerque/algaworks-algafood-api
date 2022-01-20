create table usuario_grupo (usuario_id bigint not null, grupo_id bigint not null) engine=InnoDB;
create table grupo_permissao (grupo_id bigint not null, permissao_id bigint not null) engine=InnoDB;
create table restaurante_forma_pagamento (restaurante_id bigint not null, forma_pagamento_id bigint not null) engine=InnoDB;
create table restaurante_responsavel (restaurante_id bigint not null, usuario_id bigint not null) engine=InnoDB;