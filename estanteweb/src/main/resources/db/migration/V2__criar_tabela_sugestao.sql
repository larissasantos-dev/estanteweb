-- V2__criar_tabela_sugestao.sql
-- Cria a tabela "sugestao", espelhando os campos da classe Sugestao.java
-- (nomeCliente, livroSugerido), com id como chave primaria autoincrementada.
CREATE TABLE sugestao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_cliente VARCHAR(255) NOT NULL,
    livro_sugerido VARCHAR(255) NOT NULL
);
