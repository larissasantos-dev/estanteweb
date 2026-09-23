<#include "cabecalho.ftlh">

<h1>Acervo Estante Web: </h1>
<#if livros?size == 0>
    <p>Nenhum livro foi encontrado no acervo.</p>
<#else>
<ul>
    <#list livros as livro>
        <li>${livro.titulo} , de ${livro.autor} (R$ ${livro.preco?c} ) |</li>
    </#list>
</ul>
</#if>