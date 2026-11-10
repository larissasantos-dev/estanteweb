-- V3__inserir_livros_iniciais.sql
-- Insere os mesmos livros hoje hardcoded em LivroRepository.java.
-- O campo "ano" nunca e atribuido no construtor de Livro (fica com o
-- valor padrao de int em Java, que e 0); por isso ele entra como 0 aqui,
-- para espelhar fielmente o comportamento atual do codigo.
INSERT INTO livro (titulo, autor, ano, preco) VALUES
('O Ladrão de Raios', 'Rick Riordan', 0, 15.00),
('O Mar de Monstros', 'Rick Riordan', 0, 14.50),
('A Maldição do Titã', 'Rick Riordan', 0, 16.00),
('A Batalha do Labirinto', 'Rick Riordan', 0, 18.00),
('O Último Olimpiano', 'Rick Riordan', 0, 19.90),
('A <b>Marca de Atena</b>', 'Rick Riordan', 0, 22.00);
