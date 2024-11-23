# EnergSystem

## Equipe
- **Marcos Vinicius Pereira de Oliveira - RM557252**
- **Felipe Melo de Sousa – RM556099**
- **Leonardo Matheus Teixeira – RM556629**

## Links

<details>
<summary>Veja os links relacionados ao projeto:</summary>

- YouTube  
- Vercel: https://globalsolution-ten.vercel.app/
- GitHub API Java: https://github.com/marcos3777/globalJava  

</details>

## Descrição

<details>
<summary>Descrição do Projeto</summary>
Este projeto foi desenvolvido para gerenciar empresas e calcular valores de energia. Ele inclui funcionalidades para cadastro, login e cálculo de contas de luz com base no consumo.
</details>

## Instalação

### Frontend

<details>
<summary>Passos para instalação do Frontend</summary>

1. Clone o repositório:  
   `git clone https://github.com/marcos3777/globalsolution`

2. Navegue até o diretório do projeto:  
   `cd globalsolution`

3. Instale as dependências:  
   `npm install`

4. Inicie a aplicação:  
   `npm run dev`

A aplicação estará disponível em [http://localhost:3000](http://localhost:3000).

</details>

### Backend (API)

<details>
<summary>Passos para instalação do Backend</summary>

Recomendo utilizar IntelliJ

1. Clone o repositório da API:  
   `git clone https://github.com/marcos3777/globalJava`

2. Navegue até o diretório do projeto:  
   `cd globalJava`

3. Abre com IntelliJ de preferencia

4. Altere as variáveis de ambiente para acesso ao banco de dados na classe DatabaseConnection.  
   As informações necessárias estão no arquivo `database-config.txt` (que será fornecido) e também serão disponibilizadas nos comentários da entrega.


5. Execute a classe Main do projeto para iniciar a API.

A API estará disponível em [http://localhost:8080](http://localhost:8080).

</details>

## Uso do Site

<details>
<summary>Funcionalidades</summary>

### Página Inicial

- É possível selecionar a empresa atual e calcular valores com base na conta de luz.

### Cadastro de Empresa

- Empresas podem ser cadastradas e ficam na fila de espera para aprovação.

### Login de Teste

Use os seguintes dados para teste:

- **Login:** 22345678000102  
- **Senha:** senha123  

Observação: Só é possível fazer login com empresas cujo cadastro foi aceito.

- Funcionalidades do CRUD incluidas para alteração e excluir empresa após login.

</details>
