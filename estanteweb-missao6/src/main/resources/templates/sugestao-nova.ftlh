<#include "cabecalho.ftlh">

<#if erro??>
    <p class="erro">${erro}</p>
</#if>

<h1>Sugira um livro para a EstanteWeb</h1>

<form action="/sugestoes" method="POST">
    <div>
        <label for="nomeCliente">Seu nome:</label>
        <input type="text" id="nomeCliente" name="nomeCliente" value="${nomeCliente!}" required>
    </div>

    <br>

    <div>
        <label for="livroSugerido">Livro sugerido:</label>
        <input type="text" id="livroSugerido" name="livroSugerido" value="${livroSugerido!}">
    </div>

    <br>

    <button type="submit">Enviar Sugestão</button>
</form>