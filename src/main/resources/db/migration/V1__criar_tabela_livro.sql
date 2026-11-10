-- V1__criar_tabela_livro.sql
-- Cria a tabela "livro", espelhando os campos da classe Livro.java
-- (titulo, autor, ano, preco), com id como chave primaria autoincrementada.
CREATE TABLE livro (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    ano INT NOT NULL,
    preco DECIMAL(10,2) NOT NULL
);
