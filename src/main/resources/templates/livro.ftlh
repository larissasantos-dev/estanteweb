<#include "cabecalho.ftlh">

<#-- Esta página recebe UM objeto (livro), e não uma lista: não há <#list> para o livro -->
<h1>${livro.titulo}</h1>

<p>Autor: ${livro.autor}</p>
<p>Ano: ${livro.ano?c}</p>
<p>Preço: R$ ${livro.preco?c}</p>

<#-- Missão 4.4 (desafio): a seção só aparece quando existem outros títulos do autor -->
<#if outrosDoAutor?size gt 0>
    <h2>Outros títulos de ${livro.autor}</h2>
    <ul>
        <#list outrosDoAutor as outro>
            <li><a href="/livros/${outro.id?c}">${outro.titulo}</a></li>
        </#list>
    </ul>
</#if>

<a href="/livros/html">Voltar para o acervo</a>
