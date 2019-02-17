#language:pt
@lancamento
Funcionalidade: Realizar o cálculo do valor da locação
Para validar deve ter como referencia o tipo de mídia
Como usuário do sistema
Eu quero ao locar um Lançamento de um Filme seja feito um acréscimo de 50% no valor original.

Contexto: Calculo do Lancamento
Dado que usuário possa acessar o sistema

Esquema do Cenário: realizar locacao de filme
Quando escolher um <TipoMidia>
E o sistema pegar o valor da midia <Valor>
Então o sistema caso seja lançamento acescentar 50% ao valor da mídia 
E o exibir uma mensagem se é lançamento: <Lancamento>

Exemplos:
 |  TipoMidia   			 |  Valor   | Lancamento	|
 |  "DVD" 						 | 5.0			| "NÃO" 			|
 |  "BLU-RAY"	 				 | 7.5		  | "NÃO"	      |
 |  "HD-DVD"	 				 | 5.0		  | "NÃO"	      |
 |  "VHS"	 				     | 5.0		  | "NÃO"	      |
 |  "DVD LANCAMENTO"	 | 7.5		  | "SIM"	      |
 |  "BLURAY LANCAMENTO"| 11.25		| "SIM"	      |
 |  "HD-DVD LANCAMENTO"| 7.5			| "SIM"	      |
 |  "VHS LANCAMENTO"   | 7.5			| "SIM"	      |
