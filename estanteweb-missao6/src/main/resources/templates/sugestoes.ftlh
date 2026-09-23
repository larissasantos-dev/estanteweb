<#include "cabecalho.ftlh">

<#if enviada?? && enviada>
    <p class="sucesso">Sua sugestão foi enviada com sucesso!</p>
</#if>

<h1>Sugestões Recebidas</h1>
<ul>
    <#list sugestoes as sugestao>
    <li>${sugestao.livroSugerido} (sugerido por ${sugestao.nomeCliente})</li>
</#list>
</ul>