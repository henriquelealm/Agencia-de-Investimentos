
README - Agência de Investimentos
Visão Geral
Este projeto implementa um sistema de comunicação entre um servidor e investidores usando comunicação multicast. O servidor envia atualizações sobre dicas de investimentos em ações e relatórios de dividendos para os investidores, que podem escolher os tópicos de seu interesse para receber essas atualizações em tempo real.

Funcionalidades do Servidor
O servidor da agência de investimentos possui as seguintes funcionalidades:

Envio de Dicas de Investimentos em Ações: O servidor permite que os investidores recebam dicas de investimentos em ações. Os investidores podem solicitar e receber recomendações de compra ou venda de ações, juntamente com informações adicionais sobre as tendências e oportunidades de investimento.

Envio de Relatórios de Dividendos: Além das dicas de investimentos em ações, o servidor envia relatórios sobre os próximos ativos a distribuir dividendos. Os investidores podem receber informações sobre o nome do ativo, o valor do dividendo e a data prevista para distribuição.

Listagem de Investidores Conectados: O servidor mantém uma lista de investidores conectados ao canal 2 e permite que os investidores solicitem essa lista para visualizar quem está acompanhando os relatórios de dividendos.

Funcionalidades do Investidor
O investidor tem as seguintes funcionalidades disponíveis:

Escolha de Tópicos de Interesse: Os investidores podem escolher entre dois tópicos de interesse: Dicas de Investimentos em Ações e Relatórios de Dividendos. Eles podem optar por receber atualizações em tempo real sobre um desses tópicos.

Recepção de Atualizações em Tempo Real: Após escolher um tópico de interesse, os investidores recebem atualizações em tempo real do servidor sobre esse tópico. Eles podem visualizar as dicas de investimentos em ações e os relatórios de dividendos conforme são enviados pelo servidor.

Encerramento de Sessão: Os investidores têm a opção de encerrar sua sessão e sair do programa a qualquer momento. Eles também podem voltar para escolher outro tópico de interesse ou encerrar completamente a sessão.

Execução do Programa
Para executar o programa:

Compile todos os arquivos .java.
Inicie o servidor executando a classe AgenciaInvestimentos.
Inicie um ou mais investidores executando a classe Investidor.
Siga as instruções no console para interagir com o programa.
Se preferir não iniciar os arquivos diretamente, você pode usar a classe Main.java e selecionar se deseja tratar aquela execução como servidor ou investidor.


Requisitos do Sistema
Para executar o programa, é necessário ter o Java Development Kit (JDK) instalado no sistema.