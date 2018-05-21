create table cliente (cliente_id int not null constraint cliente_pk primary key,
cliente_nome varchar(50) not null,
cliente_cpf varchar(11) unique not null,
cliente_cnpj varchar(15) unique,
cliente_logradouro varchar(40) not null,
cliente_numero int not null,
cliente_bairro varchar(20) not null,
cliente_cidade varchar(25) not null,
cliente_complemento text,
cliente_estado varchar(2) not null,
cliente_cep varchar(8) not null,
cliente_telefone varchar(14) not null,
cliente_email varchar(40),
cliente_observacoes text,
cliente_ativo boolean);

create table carro(carro_id int not null constraint carro_pk primary key,
carro_modelo varchar(20) not null unique,
carro_marca varchar(20) not null,
carro_ano varchar(4) not null,
carro_placa varchar(12) not null unique,
carro_cor varchar(15) not null,
carro_cliente_id int not null references cliente(cliente_id) on update cascade on delete set null,
carro_ativo boolean);

create table atendente (atendente_matricula int not null constraint atendente_pk primary key,
atendente_nome varchar(50) not null,
atendente_login varchar(20) not null unique,
atendente_senha varchar(20) not null,
atendente_cpf varchar(11) not null unique,
atendente_logradouro varchar(40) not null,
atendente_numero int not null,
atendente_bairro varchar(20) not null,
atendente_cidade varchar(25) not null,
cliente_complemento text,
atendente_estado varchar(2) not null,
atendente_cep varchar(8) not null,
atendente_telefone varchar(14) not null,
atendente_email varchar(40),
atendente_observacoes text,
atendente_ativo boolean);

create table tecnico (tecnico_matricula int not null constraint tecnico_pk primary key,
tecnico_nome varchar(50) not null,
tecnico_login varchar(20) not null unique,
tecnico_senha varchar(20) not null,
tecnico_cpf varchar(11) not null unique,
tecnico_logradouro varchar(40) not null,
tecnico_numero int not null,
tecnico_bairro varchar(20) not null,
tecnico_cidade varchar(25) not null,
cliente_complemento text,
tecnico_estado varchar(2) not null,
tecnico_cep varchar(8) not null,
tecnico_telefone varchar(14) not null,
tecnico_email varchar(40),
tecnico_observacoes text,
tecnico_ativo boolean);

create table especialidade (especialidade_codigo int not null constraint especialidade_pk primary key,
especialidade_nome varchar(20) not null unique,
especialidade_salario_hora numeric not null,
especialidade_ativo boolean);

create table setor_oficina (setor_codigo int not null constraint setor_pk primary key,
setor_nome varchar(30) not null,
setor_tecnico_matricula int not null references tecnico(tecnico_matricula) on update cascade on delete set null,
setor_ativo boolean);

create table mecanico (mecanico_matricula int not null constraint mecanico_pk primary key,
mecanico_nome varchar(50) not null,
mecanico_login varchar(20) not null unique,
mecanico_senha varchar(20) not null,
mecanico_cpf varchar(11) not null unique,
mecanico_logradouro varchar(40) not null,
mecanico_numero int not null,
mecanico_bairro varchar(20) not null,
mecanico_cidade varchar(25) not null,
cliente_complemento text,
mecanico_estado varchar(2) not null,
mecanico_cep varchar(8) not null,
mecanico_telefone varchar(14) not null,
mecanico_email varchar(40),
mecanico_observacoes text,
mecanico_especialidade_codigo int references especialidade(especialidade_codigo) on update cascade on delete set null,
mecanico_setor_codigo int references setor_oficina(setor_codigo) on update cascade on delete set null,
mecanico_ativo boolean);

create table registro_atendimento(ra_numero int not null constraint ra_pk primary key,
ra_data_abertura date not null,
ra_data_encerramento date,
ra_descricao_abertura text not null,
ra_descricao_encerramento text,
ra_estado varchar(15),
ra_atendente_matricula int references atendente(atendente_matricula) on update cascade on delete set null,
constraint ra_estado_chk check(ra_estado in ('Aberto', 'Em Andamento', 'Cancelado', 'Concluído')),
ra_cliente_id int references cliente(cliente_id) on update cascade on delete set null,
ra_carro_id int references carro(carro_id) on update cascade on delete set null,
ra_ativo boolean);

create table ordem_servico(os_numero int not null constraint os_pk primary key,
os_prioridade varchar(10),
os_data_abertura date not null,
os_data_encerramento date,
os_preco_servico numeric not null,
os_descricao_abertura text not null,
os_descricao_encerramento text,
os_estado varchar(15),
os_setor_atual int references setor_oficina(setor_codigo) on update cascade on delete set null,
os_ra_numero int not null references registro_atendimento(ra_numero) on update cascade on delete set null,
os_tecnico_encerramento int references tecnico(tecnico_matricula) on update cascade on delete set null,
constraint os_prioridade_chk check(os_prioridade in ('Urgente', 'Alta', 'Mediana', 'Baixa', 'Prorrogavel')),
constraint os_estado_chk check(os_estado in ('Aberto', 'Em Andamento', 'Cancelado', 'Concluído')),
os_ativo boolean
);

create table tipo_servico(tipo_servico_codigo int not null constraint tipo_servico_pk primary key,
tipo_servico_nome varchar(30) not null unique,
tipo_servico_duracao_estimada int not null,
tipo_servico_valor numeric not null,
tipo_servico_ativo boolean);

create table tarefa(tarefa_numero int not null constraint tarefa_pk primary key,
tarefa_os_numero int not null references ordem_servico(os_numero) on update cascade on delete cascade,
tarefa_tipo_servico_codigo int references tipo_servico(tipo_servico_codigo) on update cascade on delete set null,
tarefa_data_abertura date not null,
tarefa_data_encerramento date,
tarefa_descricao_abertura text not null,
tarefa_descricao_encerramento text,
tarefa_estado varchar(15),
constraint tarefa_estado_chk check(tarefa_estado in ('Aberto', 'Em Andamento', 'Cancelado', 'Concluído')),
tarefa_ativo boolean
);

create table item_estoque(item_estoque_codigo int not null constraint item_estoque_pk primary key,
item_estoque_nome varchar(25) not null unique,
item_estoque_descricao text not null unique,
item_estoque_preco numeric not null,
item_estoque_quantidade_estoque int not null,
item_estoque_ativo boolean);

create table item_material(item_material_numero int not null,
item_material_tarefa_numero int not null references tarefa(tarefa_numero) on update cascade on delete cascade,
item_material_quantidade numeric not null,
item_material_cobrado boolean,
constraint item_material_pk primary key(item_material_numero, item_material_tarefa_numero),
item_material_ativo boolean);

create table equipe_trabalho(equipe_trabalho_mecanico_matricula int not null references mecanico(mecanico_matricula) on update cascade on delete cascade,
equipe_trabalho_tarefa_numero int not null references tarefa(tarefa_numero) on update cascade on delete cascade,
constraint equipe_trabalho_pk primary key (equipe_trabalho_mecanico_matricula, equipe_trabalho_tarefa_numero),
equipe_tranbalho_ativo boolean);

create table forma_pagamento(forma_pagamento_codigo int not null constraint forma_pagamento_pk primary key,
forma_pagamento_nome varchar(20) unique,
forma_pagamento_ativo boolean);

create table pagamento(pagamento_numero int not null constraint pagamento_pk primary key,
pagamento_data_criacao date not null,
pagamento_data_conclusao date,
pagamento_estado varchar(20) not null,
pagamento_numero_nota int unique,
pagamento_forma_pagamento_codigo int references forma_pagamento(forma_pagamento_codigo) on update cascade on delete set null,
pagamento_os_codigo int not null references ordem_servico(os_numero) on update cascade on delete set null,
constraint pagamento_estado_chk check(pagamento_estado in ('Pendente', 'Pago', 'Cancelado')));

create sequence seq_cliente_codigo increment 7 minvalue 7 maxvalue 9223372036854775807 start 7 cache 2;
create sequence seq_carro_codigo increment 7 minvalue 7 maxvalue 9223372036854775807 start 7 cache 2;
create sequence seq_especialidade_codigo increment 7 minvalue 7 maxvalue 9223372036854775807 start 7 cache 2;
create sequence seq_atendente_codigo increment 7 minvalue 7 maxvalue 9223372036854775807 start 7 cache 2;
create sequence seq_tecnico_codigo increment 7 minvalue 7 maxvalue 9223372036854775807 start 7 cache 2;
create sequence seq_setor_oficina_codigo increment 7 minvalue 7 maxvalue 9223372036854775807 start 7 cache 2;
create sequence seq_mecanico_codigo increment 7 minvalue 7 maxvalue 9223372036854775807 start 7 cache 2;
create sequence seq_registro_atendimento_numero increment 7 minvalue 7 maxvalue 9223372036854775807 start 7 cache 2;
create sequence seq_ordem_servico_codigo increment 7 minvalue 7 maxvalue 9223372036854775807 start 7 cache 2;
create sequence seq_tipo_servico_codigo increment 7 minvalue 7 maxvalue 9223372036854775807 start 7 cache 2;
create sequence seq_tarefa_codigo increment 7 minvalue 7 maxvalue 9223372036854775807 start 7 cache 2;
create sequence seq_item_estoque_codigo increment 7 minvalue 7 maxvalue 9223372036854775807 start 7 cache 2;
create sequence seq_item_material_codigo increment 7 minvalue 7 maxvalue 9223372036854775807 start 7 cache 2;
create sequence seq_forma_pagamento_codigo increment 7 minvalue 7 maxvalue 9223372036854775807 start 7 cache 2;
create sequence seq_pagamento_codigo increment 7 minvalue 7 maxvalue 9223372036854775807 start 7 cache 2;

insert into cliente values(nextval('seq_cliente_codigo'), 'Miguel Ângelo da Silva', '11111111111', '111111111111111', 'Rua das Candongas', 1, 'Vilaça', 'Mogi das Valsas', NULL, 'RN', '00000000','0000000000', NULL, NULL, true);
insert into cliente values(nextval('seq_cliente_codigo'), 'Donatello de Souza', '22222222222', '222222222222222', 'Rua das Moringas', 2, 'Vilaça', 'Mogi das Valsas', NULL, 'RN', '00000000','0000000000', NULL, NULL, true);
insert into cliente values(nextval('seq_cliente_codigo'), 'Rafael Sânzio Medeiros', '3333333333', '333333333333333', 'Rua das Milongas', 132, 'Vilaça', 'Mogi das Valsas', NULL, 'RN', '00000000','0000000000', NULL, NULL, true);
insert into cliente values(nextval('seq_cliente_codigo'), 'Leonardo Ferreira', '44444444444', '444444444444444', 'Rua dos Brejeiros', 121, 'Vilaça', 'Mogi das Valsas', NULL, 'RN', '00000000','0000000000', NULL, NULL, true);
insert into cliente values(nextval('seq_cliente_codigo'), 'Donald Splinter', '55555555555', '555555555555555', 'Rua das Candongas', 152, 'Vilaça', 'Mogi das Valsas', NULL, 'RN', '00000000','0000000000', NULL, NULL, true);
insert into cliente values(nextval('seq_cliente_codigo'), 'Eugenia Meireles', '6666666666', '666666666666666', 'Rua das Miçangas', 129, 'Vilaça', 'Mogi das Valsas', NULL, 'RN', '00000000','0000000000', NULL, NULL, true);
insert into cliente values(nextval('seq_cliente_codigo'), 'Antonieta Farias', '77777777777', '777777777777777', 'Rua do Padre Amaro', 12, 'Vilaça', 'Mogi das Valsas', NULL, 'RN', '00000000','0000000000', NULL, NULL, true);

insert into carro values(nextval('seq_carro_codigo'),'Verona', 'Fiat', '2018', 'abc1234rn', 'roxo', 49, true);
insert into carro values(nextval('seq_carro_codigo'),'Louvre', 'Chevrolet', '2018', 'xyz4856rn', 'amarelo', 42, true);
insert into carro values(nextval('seq_carro_codigo'),'Piazza', 'Hyundai', '2018', 'tod2907rn', 'verde', 35, true);
insert into carro values(nextval('seq_carro_codigo'),'Square', 'Honda', '2018', 'ore2397rn', 'prata', 28, true);
insert into carro values(nextval('seq_carro_codigo'),'Suzuko', 'Fiat', '2018', 'asd3216rn', 'marrom', 21, true);
insert into carro values(nextval('seq_carro_codigo'),'Leblon', 'BMW', '2018', 'abg6544rn', 'laranja', 14, true);
insert into carro values(nextval('seq_carro_codigo'),'Aachen', 'Ferrari', '2018', 'pad7645rn', 'vinho', 7, true);

insert into especialidade values(nextval('seq_especialidade_codigo'), 'Motor', 100, true);
insert into especialidade values(nextval('seq_especialidade_codigo'), 'Suspensão', 200, true);
insert into especialidade values(nextval('seq_especialidade_codigo'), 'Freio', 300, true);
insert into especialidade values(nextval('seq_especialidade_codigo'), 'Elétrica', 150, true);
insert into especialidade values(nextval('seq_especialidade_codigo'), 'Eletrônica', 350, true);

insert into atendente values(nextval('seq_atendente_codigo'), 'Marcela Dantas', 'mdantas', '123456', '88888888888', 'Rua das Candongas', 222, 'Vilaça', 'Mogi das Valsas', NULL, 'RN', '00000000','0000000000', NULL, NULL, true);
insert into atendente values(nextval('seq_atendente_codigo'), 'Heriberto Maranhão', 'heriberto', '654321', '99999999999', 'Rua das Moringas', 212, 'Vilaça', 'Mogi das Valsas', NULL, 'RN', '00000000','0000000000', NULL, NULL, true);


insert into tecnico values(nextval('seq_tecnico_codigo'), 'Alícia Fernanda Torres', 'licinha', '000000', '10101010101', 'Rua das Candongas', 198, 'Vilaça', 'Mogi das Valsas', NULL, 'RN', '00000000','0000000000', NULL, NULL, true);
insert into tecnico values(nextval('seq_tecnico_codigo'), 'Amanda Brenner', 'brenner', 'mandoca', '12121212121', 'Rua das Moringas', 998, 'Vilaça', 'Mogi das Valsas', NULL, 'RN', '00000000','0000000000', NULL, NULL, true);
insert into tecnico values(nextval('seq_tecnico_codigo'), 'Financeiro', 'adminfinancas', 'admin', '00000000000', 'Rua da Oficina', 10000, 'Bairro da Oficina', 'Cidade da Oficina', NULL, 'RN', '00000000','0000000000', NULL, NULL, true);

insert into setor_oficina values(nextval('seq_setor_oficina_codigo'), 'Oficina de Motores', 7, true);
insert into setor_oficina values(nextval('seq_setor_oficina_codigo'), 'Oficina de Suspensão', 7, true);
insert into setor_oficina values(nextval('seq_setor_oficina_codigo'), 'Oficina de Freio', 7, true);
insert into setor_oficina values(nextval('seq_setor_oficina_codigo'), 'Oficina Elétrica', 14, true);
insert into setor_oficina values(nextval('seq_setor_oficina_codigo'), 'Oficina Eletrônica', 14, true);
insert into setor_oficina values(nextval('seq_setor_oficina_codigo'), 'Setor Financeiro', 21, true);


insert into mecanico values(nextval('seq_mecanico_codigo'), 'Silvério Amarante', 'silverio', '123456', '13131313131', 'Rua das Candongas', 6565, 'Vilaça', 'Mogi das Valsas', NULL, 'RN', '00000000','0000000000', NULL, NULL, 7, 7, true);
insert into mecanico values(nextval('seq_mecanico_codigo'), 'Paulo Vieira', 'vieira', '123456', '14141414141', 'Rua das Moringas', 9, 'Vilaça', 'Mogi das Valsas', NULL, 'RN', '00000000','0000000000', NULL, NULL, 14, 14, true);

insert into registro_atendimento values(nextval('seq_registro_atendimento_codigo'),'2018-04-04','2018-04-09','Carro com problema no motor. O cliente acha que o motor bateu','problema no motor corrigido','Concluído',7,7,49,true);
insert into registro_atendimento values(nextval('seq_registro_atendimento_codigo'),'2018-04-09',NULL,'O cliente diz que o carro esta com problema na suspensão',NULL,'Aberto',14,14,42,true);

insert into ordem_servico values(nextval('seq_ordem_servico_codigo'),'Alta','2018-04-04','2018-04-09',800.69,'Tinha uma rato no motor. Necessário fazer uma limpeza e tirar os restos do bicho','Limpeza simples realizada','Concluído',42,7,7,true);

insert into tipo_servico values(nextval('seq_tipo_servico_codigo'),'Limpeza de Motor', 1, 300, true);
insert into tipo_servico values(nextval('seq_tipo_servico_codigo'),'Calibração de Suspensão', 1, 400, true);
insert into tipo_servico values(nextval('seq_tipo_servico_codigo'),'Troca de Toda a Parte Elétrica', 4, 4500, true);

insert into tarefa values(nextval('seq_tarefa_codigo'), 7, 7, '2018-04-04', '2018-04-09', 'Realizar limpeza removendo restos de animal morto dentro do motor', 'Limpeza realizada e lubrificação de cortesia', 'Concluído', true);

insert into item_estoque values(nextval('seq_item_estoque_codigo'), 'Desengripante', 'Desengripante para remoção de ferrugem e uso em limpeza de motores e peças.', 25.65, 30, true);
insert into item_estoque values(nextval('seq_item_estoque_codigo'), 'Graxa', 'Graxa para lubrificação de motores e peças.', 15.00, 8, true);
insert into item_estoque values(nextval('seq_item_estoque_codigo'), 'Silicone', 'Silicone para vedação de motores', 12.31, 20, true);

insert into item_material values(nextval('seq_item_material_codigo'), 7, 1, false, true);

insert into equipe_trabalho values(7,7,true);

insert into forma_pagamento values(nextval('seq_forma_pagamento_codigo'), 'Cartão', true);
insert into forma_pagamento values(nextval('seq_forma_pagamento_codigo'), 'A vista', true);
insert into forma_pagamento values(nextval('seq_forma_pagamento_codigo'), 'Boleto', true);
insert into forma_pagamento values(nextval('seq_forma_pagamento_codigo'), 'Transferência', true);

insert into pagamento values(nextval('seq_pagamento_codigo'),'2018-04-09', '2018-04-09', 'Pago', 1111111111, 14, 7);