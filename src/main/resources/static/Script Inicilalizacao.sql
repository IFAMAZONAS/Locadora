

INSERT INTO public.funcao(
            id_funcao, descricao)
    VALUES (1, 'ADMIN');

INSERT INTO public.funcao(
            id_funcao, descricao)
    VALUES (2, 'GERENTE');

INSERT INTO public.funcao(
            id_funcao, descricao)
    VALUES (3, 'CAIXA');



INSERT INTO public.usuario(
            id, login, nome, senha, id_funcao)
    VALUES (1, 'admin', 'ADMINISTRADOR', '$2a$10$ukgtd/0YTi2gOKqLfzVMIu2RMmsQNebdYMxINqU3h9t9XHEtcA.fu',1);


INSERT INTO public.role(
            id, descricao, nome_role)
    VALUES (1, 'Descricao Admin', 'ROLE_ADMIN');

INSERT INTO public.role(
            id, descricao, nome_role)
    VALUES (2, 'Descricao GERENTE', 'ROLE_GERENTE');

INSERT INTO public.role(
            id, descricao, nome_role)
    VALUES (3, 'Descricao CAIXA', 'ROLE_CAIXA');



ALTER TABLE public.usuarios_role
  DROP CONSTRAINT uk_krvk2qx218dxa3ogdyplk0wxw;


ALTER TABLE public.usuarios_role
  ADD CONSTRAINT uk_krvk2qx218dxa3ogdyplk0wxw UNIQUE(role_id,usuario_id);



INSERT INTO public.usuarios_role(
            usuario_id, role_id)
    VALUES (1, 1);
	
	

INSERT INTO public.tipo_midia(
            id_tipo_midia, descricao, valor)
    VALUES (1, 'DVD',0);

INSERT INTO public.tipo_midia(
            id_tipo_midia, descricao, valor)
    VALUES (2, 'VHS',0);

INSERT INTO public.tipo_midia(
            id_tipo_midia, descricao, valor)
    VALUES (3, 'BLU-RAY',0);

INSERT INTO public.tipo_midia(
            id_tipo_midia, descricao, valor)
    VALUES (4, 'HD-DVD',0);
	
INSERT INTO public.genero(
            id_genero, descricao)
    VALUES (1, 'Comédia');

    INSERT INTO public.genero(
            id_genero, descricao)
    VALUES (2, 'Aventura');

 INSERT INTO public.genero(
            id_genero, descricao)
    VALUES (3, 'Ação');


INSERT INTO public.valores_locacao(
            id, descricao, valor)
    VALUES (1, 'DVD', 5);
INSERT INTO public.valores_locacao(
            id, descricao, valor)
    VALUES (2, 'BLU-RAY', 7.5);
INSERT INTO public.valores_locacao(
            id, descricao, valor)
    VALUES (3, 'HD-DVD', 5);
INSERT INTO public.valores_locacao(
            id, descricao, valor)
    VALUES (4, 'VHS', 5);
INSERT INTO public.valores_locacao(
            id, descricao, valor)
    VALUES (5, 'DVD LANCAMENTO', 7.5);
INSERT INTO public.valores_locacao(
            id, descricao, valor)
    VALUES (6, 'BLU-RAY LANCAMENTO', 11.25);
INSERT INTO public.valores_locacao(
            id, descricao, valor)
    VALUES (7, 'HD-DVD LANCAMENTO', 7.5);
INSERT INTO public.valores_locacao(
            id, descricao, valor)
    VALUES (8, 'VHS LANCAMENTO', 7.5);




INSERT INTO public.status_cliente(
            id_status, descricao)
    VALUES (1, 'ATIVO');

    INSERT INTO public.status_cliente(
            id_status, descricao)
    VALUES (2, 'INATIVO');





INSERT INTO public.status_locacao(
            id_status_locacao, descricao)
    VALUES (1, 'ABERTO');

    INSERT INTO public.status_locacao(
            id_status_locacao, descricao)
    VALUES (2, 'FECHADO');
INSERT INTO public.status_locacao(
            id_status_locacao, descricao)
    VALUES (3, 'PAGA');
	
	
