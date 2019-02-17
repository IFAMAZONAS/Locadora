#language:pt
@login
Funcionalidade: realizar login no sistema de gestão da resultados digitais
Para validar a autenticação no sistema de gestão da resultados digitais
Como usuário do sistema
Eu quero logar e validar as permissões de administrador e caixa.

Contexto: possibilidade de acessar o sistema
Dado que usuário possa acessar a tela de login do sistema

Esquema do Cenário: realizar login no sistema com usuários válidos
Quando informar um <Login> válido
E inserir uma <Senha> válida
Então o sistema deve permitir a autenticação do usuário
E o tipo de <Mensagem> do usuário deve ser exibido

Exemplos:
 |  Login   |  Senha  | Mensagem	                |
 |  "Admin" | "admin" | "Acesso do Administrador" |
 |  "caixa" | "caixa" | "Acesso do Caixa"	        |


