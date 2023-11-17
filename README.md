<h1 align="center">Missão Prática</h1>

<p align="center">
  <strong>Andrey Haertel Aires - Matrícula: 2021.07.22851-2</strong><br>
  Polo Centro - Palhoça – SC<br>
  Nível 3: Back-end Sem Banco Não Tem – T 9001 – 3º Semestre Letivo
</p>

<h2>Objetivo da Prática</h2>

<p>
  - Implementar persistência com base no middleware JDBC.<br>
  - Utilizar o padrão DAO (Data Access Object) no manuseio de dados.<br>
  - Implementar o mapeamento objeto-relacional em sistemas Java.<br>
  - Criar sistemas cadastrais com persistência em banco relacional.<br>
  - Ao final do exercício, o aluno terá criado um aplicativo cadastral com uso do SQL Server na persistência de dados.
</p>

<h2>Resumo do Programa</h2>

<p>
  A missão prática pede a criação de um sistema de cadastro que interage com um banco de dados. Vamos analisar algumas partes importantes do código:
</p>

<h3>Conexão com o Banco de Dados:</h3>

<p>
  - O código inicia estabelecendo uma conexão com o banco de dados usando a classe ConectorBD.<br>
  - Em caso de sucesso na conexão, são criadas instâncias de PessoaFisicaDAO e PessoaJuridicaDAO para interagir com o banco de dados.
</p>

<h3>Menu de Opções:</h3>

<p>
  - O programa exibe um menu de opções para o usuário, onde ele pode escolher entre diferentes ações, como inclusão, alteração, exclusão, busca por ID e exibição de todos os registros.
</p>

<h3>Métodos de Manipulação de Dados:</h3>

<p>
  - Existem métodos específicos para incluir, alterar, excluir, buscar por ID e exibir todos os registros de pessoas físicas e jurídicas no banco de dados.<br>
  - Cada ação é realizada de acordo com o tipo de pessoa escolhido (física ou jurídica).
</p>

<h3>Tratamento de Exceções:</h3>

<p>
  - O código inclui blocos de tratamento de exceções para lidar com possíveis erros durante a execução, exibindo mensagens de erro específicas.
</p>

<h3>Entrada do Usuário:</h3>

<p>
  - O programa utiliza a classe Scanner para obter entradas do usuário e interagir com as escolhas do menu.
</p>

<h3>Codificação UTF-8:</h3>

<p>
  - Há uma configuração para utilizar a codificação UTF-8 na saída, permitindo o uso de caracteres especiais.
</p>

<h3>Encerramento do Programa:</h3>

<p>
  - O programa continua em execução até que o usuário escolha a opção 0 para finalizá-lo.
</p>

<p>
  Em resumo, trata-se de um sistema simples de cadastro que permite a manipulação de dados de pessoas físicas e jurídicas em um banco de dados.
</p>
