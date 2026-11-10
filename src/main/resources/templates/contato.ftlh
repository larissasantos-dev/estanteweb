<#include "cabecalho.ftlh">

<#if erro??>
    <p class="erro">${erro}</p>
</#if>

<h1>Fale com a EstanteWeb</h1>

<form action="/contato" method="POST">
    <div>
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" value="${nome!}" required>
    </div>

    <br>

    <div>
        <label for="mensagem">Mensagem:</label>
        <textarea id="mensagem" name="mensagem" required></textarea>
    </div>

    <br>

    <button type="submit">Enviar</button>
</form>
