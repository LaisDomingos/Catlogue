# 📱 Catlogue – App de Raças de Gatos 🐱
**Catlogue** (Cat + Catálogo) é um aplicativo Android desenvolvido em **Kotlin**, com **Jetpack Compose**, que consome a [Cat API](https://thecatapi.com/) para exibir uma lista de raças de gatos.

O objetivo é permitir que o usuário visualize as raças disponíveis, veja detalhes de cada uma e salve suas favoritas.  
O app também funciona **offline**, armazenando os dados localmente.

---

## ✅ Funcionalidades do App

- **Listagem de raças de gatos**  
  Exibe uma lista com nome e imagem de cada raça.

- **Busca por nome da raça**  
  Permite filtrar a lista digitando o nome da raça desejada.

- **Marcar/desmarcar como favorita**  
  Cada raça pode ser favoritada, tanto pela lista quanto pela tela de detalhes.

- **Tela de favoritos**  
  Mostra apenas as raças marcadas como favoritas.

- **Expectativa de vida média dos favoritos**  
  Exibe a média de expectativa de vida das raças favoritas.

- **Tela de detalhes da raça**  
  Exibe informações completas:
  - Nome  
  - Origem  
  - Temperamento  
  - Descrição

- **Navegação entre telas**  
  O app permite navegar entre a lista, favoritos e detalhes usando **Jetpack Navigation**.

- **Armazenamento offline com Room**  
  As raças e favoritos são salvos localmente para funcionar mesmo sem internet.

---

## 💡 Funcionalidades Extras (se houver tempo)

- **Paginação** na lista de raças (ex: carregar 10 por vez)
- **Tratamento de erros** (sem internet, falha de API)
- **Testes unitários e de integração**
- **Design modular** do projeto

## 📁 Estrutura
```bash
├── data/
│   ├── model/         ← classes de dados (Breed, etc)
│   ├── remote/        ← Retrofit e API
│   ├── local/         ← Room (DAO, DB, entidades locais)
│   └── repository/    ← onde a ViewModel busca os dados
├── ui/
│   ├── screens/       ← uma subpasta por tela
│   │   ├── breedlist/     ← tela da lista de raças
│   │   ├── breeddetails/  ← tela de detalhes
│   │   └── favorites/     ← tela de favoritos
│   └── components/    ← componentes reutilizáveis (ex: Card, botão favorito etc)
├── viewmodel/         ← ViewModels das telas
└── MainActivity.kt    ← ponto de entrada
```
## Passos

- Inicialmente, criei o model Breed para representar os dados das raças de gato, mapeando os campos importantes da resposta JSON da API, como nome, origem, temperamento, descrição, expectativa de vida e imagem.
- Em seguida, configurei o serviço de comunicação com a API criando a interface CatApiService, que define os endpoints que o aplicativo vai acessar, começando pelo endpoint para buscar a lista de raças.
- Para fazer a conexão com a API, criei uma instância do Retrofit em um singleton chamado RetrofitInstance, configurando a URL base da Cat API e adicionando um interceptor para inserir o token de autenticação em todas as requisições.
- Depois, implementei a camada de dados com a Repository, criando a classe BreedRepository para centralizar o acesso aos dados da API, facilitando o gerenciamento e a manutenção futura do código.
- Criei a BreedViewModel, que é responsável por buscar os dados da Repository, armazenar o estado da lista de raças, controlar os estados de carregamento e erro, e expor esses estados para a UI de forma reativa usando StateFlow. Isso permite que a interface observe mudanças e atualize automaticamente conforme os dados chegam ou ocorrem erros.