
<#include "cabecalho.ftlh">

<h1>Editar livro</h1>

<#if erro??>
<p style="color: red;">${erro}</p>
</#if>

<form method="post" action="/livros/${livro.id?c}">

    <p>
        <label>Título:</label>
        <input type="text" name="titulo"
               value="<#if titulo??>${titulo?html}<#else>${livro.titulo?html}</#if>">
    </p>

    <p>
        <label>Autor:</label>
        <input type="text" name="autor"
               value="<#if autor??>${autor?html}<#else>${livro.autor?html}</#if>">
    </p>

    <p>
        <label>Ano:</label>
        <input type="text" name="ano"
               value="<#if ano??>${ano?html}<#else>${livro.ano?c}</#if>">
    </p>

    <p>
        <label>Preço:</label>
        <input type="text" name="preco"
               value="<#if preco??>${preco?html}<#else>${livro.preco?c}</#if>">
    </p>

    <button type="submit">Salvar alterações</button>

</form>

<a href="/livros/${livro.id?c}">Cancelar</a>
