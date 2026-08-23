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
editora, formato, categoria, o tipo de leitura (obrigatória ou livre) e o período de vigência
da licença (data de início e data de fim), *para que* o título fique disponível aos alunos,
já classificado corretamente para os limites da estante, apenas durante o período licenciado.

*Critérios de aceitação*
- Título, editora, formato (PDF, EPUB), categoria, tipo de leitura (obrigatória ou livre) e o período de vigência da licença (data de início e data de fim) são obrigatórios.
- A data de fim da licença deve ser posterior à data de início.
- O limite de acessos simultâneos é fixo em 60 para todo eBook cadastrado — não é um campo configurável.
- A editora e a categoria informadas precisam já estar cadastradas no sistema.
- Após o cadastro, o eBook passa a aparecer no catálogo dentro do período de licença informado, já identificado como obrigatório ou livre.


## HU03 — Cadastrar usuário
*Como* integrante da equipe da biblioteca, *eu quero* cadastrar alunos e bibliotecários no
sistema, *para que* eles possam se autenticar e usar as funcionalidades do seu perfil.

*Critérios de aceitação*
- O cadastro define o perfil do usuário (aluno, bibliotecário ou equipe da biblioteca).
- Não é possível cadastrar dois usuários com o mesmo identificador.
- Um usuário recém-cadastrado consegue realizar login com a senha definida.

## HU04 — Cadastrar editora

Como integrante da equipe da biblioteca, eu quero cadastrar as editoras, para que os
eBooks sejam sempre associados a uma editora já conhecida, sem variações de escrita do mesmo nome.

Critérios de aceitação
- O nome da editora é obrigatório e não pode se repetir.
- A editora cadastrada fica disponível para seleção no cadastro de eBooks.
- Uma editora com eBooks associados não pode ser excluída.


## HU05 — Cadastrar categoria

Como integrante da equipe da biblioteca, eu quero cadastrar as categorias do acervo
(literatura, técnico, periódico), para que os eBooks sejam classificados de forma padronizada e
os alunos consigam filtrar o catálogo.

Critérios de aceitação
- O nome da categoria é obrigatório e não pode se repetir.
- A categoria cadastrada fica disponível para seleção no cadastro de eBooks.
- Uma categoria com eBooks associados não pode ser excluída.


## HU06 — Cadastrar período de acesso

Como integrante da equipe da biblioteca, eu quero cadastrar os períodos de acesso do
semestre com data de início e fim, para que os alunos só possam alterar suas estantes dentro das
janelas definidas pela biblioteca.

Critérios de aceitação
- Um período de acesso tem data de início e data de fim dentro do semestre.
- Períodos de um mesmo semestre não podem se sobrepor.
- Fora de um período vigente, adições e remoções de eBooks ficam bloqueadas.


## HU07 — Renovar licença do eBook

Como integrante da equipe da biblioteca, eu quero processar manualmente a renovação das
licenças ao final do período de acesso, definindo a nova data de fim da licença, para que os
títulos pouco utilizados saiam do catálogo e a biblioteca não mantenha licenças ociosas.

Critérios de aceitação
- A renovação é uma ação disparada pela equipe da biblioteca, disponível apenas após o encerramento do período de acesso, e exige informar a nova data de fim da licença.
- eBooks presentes na estante de menos de 3 alunos não têm a data de fim da licença estendida — a renovação é recusada e a licença permanece com a data de fim já cadastrada.
- eBooks com a data de fim da licença já vencida deixam de aparecer no catálogo do semestre seguinte.


## HU08 — Consultar catálogo de eBooks
*Como* aluno ou bibliotecário, *eu quero* consultar os eBooks licenciados no semestre com título,
editora, formato e categoria, *para que* eu localize os títulos que me interessam.

*Critérios de aceitação*
- A listagem exibe título, editora, formato e categoria.
- É possível filtrar por categoria e buscar por título.
- Somente eBooks com licença vigente no semestre (hoje entre a data de início e a data de fim da licença) aparecem no catálogo.

## HU09 — Consultar alunos com um eBook

Como bibliotecário, eu quero consultar quais alunos têm um determinado eBook em sua estante,
para que eu acompanhe o uso do acervo e possa embasar a decisão de renovação da licença.

Critérios de aceitação
- A busca parte de um eBook do catálogo.
- O resultado lista os alunos e o tipo de leitura (obrigatória ou livre) de cada um.
- O total de alunos é exibido, para comparação direta com o mínimo de 3 exigido para renovação.

## HU10 — Adicionar eBook à estante
*Como* aluno, *eu quero* adicionar um eBook do catálogo à minha estante pessoal, *para que* eu
tenha acesso ao título durante o semestre.

*Critérios de aceitação*
- A adição só é permitida durante um período de acesso vigente.
- O tipo de leitura (obrigatória ou livre) é o que já está definido no cadastro do eBook, não escolhido pelo aluno.
- O sistema recusa a adição de um 5º eBook obrigatório ou de um 3º eBook livre, informando o limite atingido.
- O mesmo eBook não pode ser adicionado duas vezes à mesma estante.
- Toda adição bem-sucedida notifica o sistema de estatísticas de uso.

## HU11 — Remover eBook da estante

Como aluno, eu quero remover um eBook da minha estante, para que eu libere uma vaga e
possa adicionar outro título de leitura obrigatória ou livre.

Critérios de aceitação
- A remoção só é permitida durante um período de acesso vigente.
- Após a remoção, a vaga correspondente (obrigatória ou livre, conforme o tipo definido no cadastro do eBook) volta a ficar disponível.
- O eBook removido deixa de ser contabilizado na contagem de alunos usada na renovação da licença.

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

## HU14 — Notificar sistema de estatísticas

Como biblioteca, eu quero que o sistema de estatísticas de uso seja notificado a cada eBook
adicionado a uma estante, para que possamos acompanhar quais títulos são mais utilizados pelos
alunos.

Critérios de aceitação
- A notificação é enviada automaticamente a cada adição bem-sucedida, sem ação manual.
- A notificação identifica o eBook, o aluno e a data/hora da adição.
- Uma falha na notificação é registrada e não impede a adição do eBook à estante.