<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<h1>README - Agência de Investimentos</h1>

<h2>Visão Geral</h2>

<p>Este projeto implementa um sistema de comunicação entre um servidor e investidores usando comunicação multicast. O servidor envia atualizações sobre dicas de investimentos em ações e relatórios de dividendos para os investidores, que podem escolher os tópicos de seu interesse para receber essas atualizações em tempo real.</p>

<h2>Funcionalidades do Servidor</h2>

<ol>
<li><strong>Envio de Dicas de Investimentos em Ações:</strong> O servidor permite que os investidores recebam dicas de investimentos em ações. Os investidores podem solicitar e receber recomendações de compra ou venda de ações, juntamente com informações adicionais sobre as tendências e oportunidades de investimento.</li>
<li><strong>Envio de Relatórios de Dividendos:</strong> Além das dicas de investimentos em ações, o servidor envia relatórios sobre os próximos ativos a distribuir dividendos. Os investidores podem receber informações sobre o nome do ativo, o valor do dividendo e a data prevista para distribuição.</li>
<li><strong>Listagem de Investidores Conectados:</strong> O servidor mantém uma lista de investidores conectados ao canal 2 e permite que os investidores solicitem essa lista para visualizar quem está acompanhando os relatórios de dividendos.</li>
</ol>

<h2>Funcionalidades do Investidor</h2>

<ol>
<li><strong>Escolha de Tópicos de Interesse:</strong> Os investidores podem escolher entre dois tópicos de interesse: Dicas de Investimentos em Ações e Relatórios de Dividendos. Eles podem optar por receber atualizações em tempo real sobre um desses tópicos.</li>
<li><strong>Recepção de Atualizações em Tempo Real:</strong> Após escolher um tópico de interesse, os investidores recebem atualizações em tempo real do servidor sobre esse tópico. Eles podem visualizar as dicas de investimentos em ações e os relatórios de dividendos conforme são enviados pelo servidor.</li>
<li><strong>Encerramento de Sessão:</strong> Os investidores têm a opção de encerrar sua sessão e sair do programa a qualquer momento. Eles também podem voltar para escolher outro tópico de interesse ou encerrar completamente a sessão.</li>
</ol>

<h2>Execução do Programa</h2>

<p>Para executar o programa:</p>

<ol>
<li>Compile todos os arquivos <code>.java</code>.</li>
<li>Inicie o servidor executando a classe <code>AgenciaInvestimentos</code>.</li>
<li>Inicie um ou mais investidores executando a classe <code>Investidor</code>.</li>
<li>Siga as instruções no console para interagir com o programa.</li>
<li>Se preferir não iniciar os arquivos diretamente, você pode usar a classe Main.java e selecionar se deseja tratar aquela execução como servidor ou investidor.</li>

</ol>

<h2>Requisitos do Sistema</h2>

<p>Para executar o programa, é necessário ter o Java Development Kit (JDK) instalado no sistema.</p>

</body>
</html>
