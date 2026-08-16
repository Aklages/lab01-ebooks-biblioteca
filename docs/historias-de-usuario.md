## Histórias de Usuário

## HU01 — Realizar login
*Como* usuário do sistema, *eu quero* acessar o sistema informando meu identificador e minha
senha, *para que* apenas pessoas autorizadas usem minha conta e o sistema saiba quais
funcionalidades liberar para o meu perfil.

*Critérios de aceitação*
- Com credenciais corretas, o acesso é liberado e o perfil (aluno, bibliotecário ou equipe da biblioteca) é identificado.
- Com senha incorreta, o sistema informa a falha sem revelar se o usuário existe.
- Nenhuma funcionalidade do sistema fica disponível antes do login.


## HU02 — Cadastrar eBook
*Como* integrante da equipe da biblioteca, *eu quero* cadastrar um eBook informando título,
editora, formato, categoria e o tipo de leitura (obrigatória ou livre), *para que* o título
fique disponível aos alunos já classificado corretamente para os limites da estante.

*Critérios de aceitação*
- Título, editora, formato (PDF, EPUB), categoria e tipo de leitura (obrigatória ou livre) são obrigatórios.
- O limite de acessos simultâneos é fixo em 60 para todo eBook cadastrado — não é um campo configurável.
- A editora e a categoria informadas precisam já estar cadastradas no sistema.
- Após o cadastro, o eBook passa a aparecer no catálogo, já identificado como obrigatório ou livre.


## HU03 — Cadastrar usuário
*Como* integrante da equipe da biblioteca, *eu quero* cadastrar alunos e bibliotecários no
sistema, *para que* eles possam se autenticar e usar as funcionalidades do seu perfil.

*Critérios de aceitação*
- O cadastro define o perfil do usuário (aluno, bibliotecário ou equipe da biblioteca).
- Não é possível cadastrar dois usuários com o mesmo identificador.
- Um usuário recém-cadastrado consegue realizar login com a senha definida.


## HU08 — Consultar catálogo de eBooks
*Como* aluno ou bibliotecário, *eu quero* consultar os eBooks licenciados no semestre com título,
editora, formato e categoria, *para que* eu localize os títulos que me interessam.

*Critérios de aceitação*
- A listagem exibe título, editora, formato e categoria.
- É possível filtrar por categoria e buscar por título.
- Somente eBooks com licença vigente no semestre aparecem no catálogo.


## HU10 — Adicionar eBook à estante
*Como* aluno, *eu quero* adicionar um eBook do catálogo à minha estante pessoal, *para que* eu
tenha acesso ao título durante o semestre.

*Critérios de aceitação*
- A adição só é permitida durante um período de acesso vigente.
- O tipo de leitura (obrigatória ou livre) é o que já está definido no cadastro do eBook, não escolhido pelo aluno.
- O sistema recusa a adição de um 5º eBook obrigatório ou de um 3º eBook livre, informando o limite atingido.
- O mesmo eBook não pode ser adicionado duas vezes à mesma estante.
- Toda adição bem-sucedida notifica o sistema de estatísticas de uso.


## HU12 — Consultar estante pessoal
*Como* aluno, *eu quero* consultar os eBooks da minha estante separados entre leitura obrigatória
e livre, *para que* eu saiba quais títulos já adicionei e quantas vagas ainda tenho.

*Critérios de aceitação*
- A consulta agrupa os eBooks por tipo de leitura (obrigatória ou livre, conforme o cadastro do eBook).
- O sistema exibe quantas vagas restam em cada tipo (por exemplo, 2 de 4 obrigatórias e 1 de 2 livres).
- Um aluno só enxerga a própria estante.


## HU13 — Acessar eBook
*Como* aluno, *eu quero* abrir um eBook que está na minha estante, *para que* eu possa lê-lo
dentro do limite de licenças de uso do título.

*Critérios de aceitação*
- O acesso só é liberado se houver licença disponível (menos de 60 acessos simultâneos em uso).
- Com todas as 60 licenças ocupadas, o acesso é recusado com mensagem explicativa, mas o eBook permanece na estante.
- O acesso consome uma das licenças simultâneas enquanto durar a leitura, e a licença é liberada ao encerrar (ação explícita do aluno).
- Só é possível acessar eBooks que estejam na estante do próprio aluno.