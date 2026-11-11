
<#include "cabecalho.ftlh">

<h1>${livro.titulo}</h1>

<p>Autor: ${livro.autor}</p>
<p>Ano: ${livro.ano?c}</p>
<p>Preço: R$ ${livro.preco?c}</p>

<p>
    <a href="/livros/${livro.id?c}/editar">
        Editar livro
    </a>
</p>

<p>
    <a href="/livros/${livro.id?c}/excluir">
        Excluir livro
    </a>
</p>

<#if outrosDoAutor?size gt 0>

<h2>Outros títulos de ${livro.autor}</h2>

<ul>
    <#list outrosDoAutor as outro>
    <li>
        <a href="/livros/${outro.id?c}">
            ${outro.titulo}
        </a>
    </li>
</#list>
</ul>

</#if>

<a href="/livros/html">Voltar para o acervo</a>
