
<#include "cabecalho.ftlh">

<h1>Excluir livro</h1>

<p>Você realmente deseja excluir este livro?</p>

<h2>${livro.titulo}</h2>

<p>Autor: ${livro.autor}</p>
<p>Ano: ${livro.ano?c}</p>
<p>Preço: R$ ${livro.preco?c}</p>

<form method="post"
      action="/livros/${livro.id?c}/excluir">

    <button type="submit">
        Sim, excluir livro
    </button>

</form>

<a href="/livros/${livro.id?c}">
    Não, voltar
</a>
