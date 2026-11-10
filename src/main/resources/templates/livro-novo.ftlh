<#include "cabecalho.ftlh">

<h1>Cadastrar livro</h1>

<#if erro??>
    <p style="color: red;">${erro}</p>
</#if>

<form method="post" action="/livros">
    <p>
        <label>Título:
            <input type="text" name="titulo" value="${titulo!}">
        </label>
    </p>
    <p>
        <label>Autor:
            <input type="text" name="autor" value="${autor!}">
        </label>
    </p>
    <p>
        <label>Ano:
            <input type="text" name="ano" value="${(ano?c)!}">
        </label>
    </p>
    <p>
        <label>Preço:
            <input type="text" name="preco" value="${(preco?c)!}">
        </label>
    </p>
    <button type="submit">Cadastrar</button>
</form>
