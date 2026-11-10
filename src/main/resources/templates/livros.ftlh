<#include "cabecalho.ftlh">

<h1>Acervo Estante Web: </h1>

<#-- Missão 4.3: a ordenação vai na query string e não muda o endereço de cada ficha -->
<p>
    Ordenar por:
    <a href="/livros/html?ordem=titulo">título</a> |
    <a href="/livros/html?ordem=autor">autor</a> |
    <a href="/livros/html?ordem=ano">ano</a> |
    <a href="/livros/html?ordem=preco">preço</a> |
    <a href="/livros/html">sem ordenação</a>
</p>

<#if livros?size == 0>
    <p>Nenhum livro foi encontrado no acervo.</p>
<#else>
<ul>
    <#list livros as livro>
        <#-- Missão 3.1: o endereço da ficha é montado com o id do próprio registro.
             O ?c é obrigatório: sem ele, 1998 viraria "1,998" e o link quebraria. -->
        <li><a href="/livros/${livro.id?c}">${livro.titulo}</a> , de ${livro.autor} (R$ ${livro.preco?c} ) |</li>
    </#list>
</ul>
</#if>
