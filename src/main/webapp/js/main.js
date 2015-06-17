var dateRegex = /^\d{4}-\d{2}-\d{2}$/;

$(document).ready(function () {
    var index = getUrlParameter('index');
    if (index === undefined || index === "") {
        mostrarDiv(2);
    }
    else {
        doPopulaCatalogacao(index);
    }
    
    /*
     * Enableness
     */
    updatePatrimonioEnableness();
    updatePaginaBuscaEnableness();

    $("#idEntrar").click(doLogin);

    $("#idLimparBusca").click(clearBusca);
    $("#idLimparCat").click(clearCatalogo);
    
    $("#idBuscar").click(doBusca);
    $("#idPagProxima").click(doProximaBusca);
    $("#idPagAnterior").click(doAnteriorBusca);
    
    $("#idItemProximo").click(doProximaCatalogacao);
    $("#idItemAnterior").click(doAnteriorCatalogacao);
    
    $("#idExcluir").click(doExcluir);
    $("#idEditar").click(toggleEdit);
    $("#idSalvarAtual").click(prepareAddOrUpdate);
    $("#idSalvarNovo").click(doNovoComentario);
    
    $("#idSubmitFile").click(doSubmeterArquivo);
    $("#idAbrirArquivo").click(doAbrirArquivo);
});

// Upstream: http://stackoverflow.com/questions/19491336/get-url-parameter-jquery
function getUrlParameter(sParam) {
    var sPageURL = window.location.search.substring(1), sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++) {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] === sParam) {
            return sParameterName[1];
        }
    }
} 

function mostrarDiv(i) {
    if (i === 1) {
        // login
        document.getElementById('idDivLogin').style.display = "inline";
        document.getElementById('idDivBusca').style.display = "none";
        document.getElementById('idDivCatalogacao').style.display = "none";
    } else if (i === 2) {
        // busca
        document.getElementById('idDivLogin').style.display = "none";
        document.getElementById('idDivBusca').style.display = "inline";
        document.getElementById('idDivCatalogacao').style.display = "none";
    } else if (i === 3) {
        // catálogo
        document.getElementById('idDivLogin').style.display = "none";
        document.getElementById('idDivBusca').style.display = "none";
        document.getElementById('idDivCatalogacao').style.display = "inline";
    }
}
showDiv = mostrarDiv;

function doLogin() {
    $.ajax({
        url: window.location.pathname + 'loginServlet',
        method: 'POST',
        data: {
            user: $('#idUsuario').val(),
            password: $('#idSenha').val()
        },
        success: function () {
            console.log('INFO: Login succeeded.');
            $('#idRespAutorizacao').html('');
            clearLogin();
            mostrarDiv(2);
        },
        error: function () {
            console.log('INFO: Login failed.');
            $('#idRespAutorizacao').html('Nome de usuário ou senha incorretos.');
        }
    });
}

function clearLogin() {
    $('#idDivLogin form').trigger('reset');
    $('#idRespAutorizacao').html('');
}
clear1 = clearLogin;

function clearBusca() {
    $('#idpatrimonio2').val('');
    $('#idtitulo2').val('');
    $('#idautoria2').val('');
    $('#idveiculo2').val('');
    $('#iddatapublicacao2').val('');
    $('#idpalchave2').val('');
    $('#idTabelaResultados').val('');
    $('#idPaginaDestino').val('');
    $(':checked').removeAttr('checked');
    $('#idMsgDialogo2').html('');
    $("#idTabelaResultados").html('');
}
clear2 = clearBusca;

function clearAlmostAll3() {
    $('#idtitulo3').val('');
    $('#idautoria3').val('');
    $('#idveiculo3').val('');
    $('#iddatapublicacao3').val('');
    $('#idpalchave3').val('');
    $('#idInputTypeFile').val('');
    if(tinymce.activeEditor !== null) {
        tinymce.activeEditor.setContent("");
    }
    $('#idComentarios').html('');
    $('#idMsgDialogo3').html('');
}

function clearCatalogo() {
    $('#idpatrimonio3').val('');
    clearAlmostAll3();
}
clear3 = clearCatalogo;

function getJsonBusca() {
    if($("#idPaginaDestino").val() === "") {
        $("#idPaginaDestino").val("1");
        updatePaginaBuscaEnableness();
    }
    
    var jsonBusca = {
        idpatrimonio2: $("#idpatrimonio2").val(),
        idtitulo2: $("#idtitulo2").val(),
        idautoria2: $("#idautoria2").val(),
        idveiculo2: $("#idveiculo2").val(),
        iddatapublicacao2: $("#iddatapublicacao2").val(),
        idpalchave2: $("#idpalchave2").val(),
        idPaginaDestino: $("#idPaginaDestino").val(),
        idcheckpatrimonio: $("#idcheckpatrimonio").prop('checked'),
        idchecktituloOU: $("#idchecktituloOU").prop('checked'),
        idchecktituloE:  $("#idchecktituloE").prop('checked'),
        idcheckautoriaOU: $("#idcheckautoriaOU").prop('checked'),
        idcheckautoriaE:  $("#idcheckautoriaE").prop('checked'),
        idcheckveiculoOU: $("#idcheckveiculoOU").prop('checked'),
        idcheckveiculoE:  $("#idcheckveiculoE").prop('checked'),
        idcheckdatapublicacaoOU: $("#idcheckdatapublicacaoOU").prop('checked'),
        idcheckdatapublicacaoE:  $("#idcheckdatapublicacaoE").prop('checked'),
        idcheckpalchaveOU: $("#idcheckpalchaveOU").prop('checked'),
        idcheckpalchaveE:  $("#idcheckpalchaveE").prop('checked')
    };
    console.log("INFO: json busca:");
    console.log(jsonBusca);
    return jsonBusca;
}

function validateJsonBusca(json) {
    var ans = false;
    
    $("input[id^='id'][id$='OU'],input[id^='id'][id$='E'],#idcheckpatrimonio").each(function(index) {
        ans = ans || ($(this).prop('checked'));
    });
    
    console.log("INFO: validateJsonBusca: " + ans);
    return ans;
}

function popularBusca(json) {
    console.log("INFO: " + arguments.callee.name);
    
    $("#idTabelaResultados").append("<ul>");
    var list = $("#idTabelaResultados>ul");
    
    if(json.justanarray.length === 0) {
        $("#idTabelaResultados").html('<p><strong>Nenhum livro correspondente à busca foi encontrado no banco de dados.</strong></p>');
    }
    else {
        $.each(json.justanarray, function(index, value) {
            var description = value.patrimonio + ": " + value.titulo + " - " + value.autoria; 
            list.append('<li><a onclick="' + 'doPopulaCatalogacao(' + value.patrimonio + ')' + '" href="#">' + description + '</a></li><br />');
        });
    }
}

function addComentario(comment) {
    var list = $("#idComentarios>ol");
    if (list.length === 0) {
        $("#idComentarios").append("<ol>");
        list = $("#idComentarios>ol");
    }
    
    list.append('<li>' + comment + '</li><hr/>');
}

function popularCatalogacao(json) {
    console.log("INFO: " + arguments.callee.name);
    
    $('#idtitulo3').val(json.titulo);
    $('#idautoria3').val(json.autoria);
    $('#idveiculo3').val(json.veiculo);
    $('#iddatapublicacao3').val(json.datapublicacao);
    $('#idpalchave3').val(json.palchave);
    
    if(json.arquivo === undefined || json.arquivo === "") {
        $("#idAbrirArquivo").addClass('pure-button-disabled');
        $("#idAbrirArquivo").prop('disabled', true);
    }
    else {
        $("#idAbrirArquivo").removeClass('pure-button-disabled');
        $("#idAbrirArquivo").prop('disabled', false);
    }
    
    if(json.comentarios.length === 0) {
        $("#idComentarios").html("<p align='center'><strong>Nenhum comentário para esse livro.</strong></p>");
    }
    else {
        $.each(json.comentarios, function(index, value) {
            addComentario(value);
        });
    }
}

function doBusca() {
    console.log("INFO: " + arguments.callee.name);
    $("#idMsgDialogo2").html('');
    $("#idTabelaResultados").html('');
    updatePaginaBuscaEnableness();
    
    jsonBusca = getJsonBusca();
    
    isValid = validateJsonBusca(jsonBusca);
    if(!isValid) {
        $("#idMsgDialogo2").html('Erro! Por favor selecione pelo menos um critério de busca.');
        return;
    }
    
    var datapublicacao = $("#iddatapublicacao2").val();
    if(datapublicacao !== "") {
        if(!datapublicacao.match(dateRegex)) {
            $("#idMsgDialogo2").html('Erro! Data com formato inválido! Utilize YYYY-MM-DD.');
            return;
        }
    }
    
    $.ajax({
        url: window.location.pathname + 'buscaServlet',
        method: 'POST',
        data: jsonBusca,
        success: function (data, status, jqxhr) {
            popularBusca(data);
        },
        error: function () {
            $("#idMsgDialogo2").html('Erro! Um erro inesperado ocorreu no AJAX da busca.');
        }
    });
}

function updatePatrimonioEnableness() {
    var patrimonio = parseInt($("#idpatrimonio3").val());
    
    if(isNaN(patrimonio)) {
        $("#idExcluir").addClass('pure-button-disabled');
        $("#idExcluir").prop('disabled', true);
        return;
    }
    else {
        $("#idExcluir").removeClass('pure-button-disabled');
        $("#idExcluir").prop('disabled', false);
        
        if(patrimonio === 1) {
            $("#idItemAnterior").addClass('pure-button-disabled');
            $("#idItemAnterior").prop('disabled', true);
        } else {
            $("#idItemAnterior").removeClass('pure-button-disabled');
            $("#idItemAnterior").prop('disabled', false);
        }
    }
}

function updatePaginaBuscaEnableness() {
    var pagina = parseInt($("#idPaginaDestino").val());
    if(pagina === 1) {
        $("#idPagAnterior").addClass('pure-button-disabled');
        $("#idPagAnterior").prop('disabled', true);
    } else {
        $("#idPagAnterior").removeClass('pure-button-disabled');
        $("#idPagAnterior").prop('disabled', false);
    }
}

function doPopulaCatalogacao(patrimonio) {
    console.log("INFO: " + arguments.callee.name);
    mostrarDiv(3);
    $("#idMsgDialogo3").html('');
    $("#idComentarios").html('');
    if(tinymce.activeEditor != null) { 
        tinymce.activeEditor.setContent("");
    }
    $("#idpatrimonio3").val(patrimonio);
    updatePatrimonioEnableness();
    
    $.ajax({
        url: window.location.pathname + 'catalogacaoServlet',
        method: 'POST',
        data: {
            "patrimonio": patrimonio
        },
        success: function (data, status, jqxhr) {
            popularCatalogacao(data);
            catalogoExisteEnableness(true);
        },
        error: function () {
            clearAlmostAll3();
            setEdit(false);
            catalogoExisteEnableness(false);
            $("#idMsgDialogo3").html('Erro! Um erro inesperado ocorreu no AJAX da catalogação.');
        }
    });
}

function doProximaCatalogacao() {
    var patrimonio = parseInt($("#idpatrimonio3").val());
    
    if(isNaN(patrimonio)) {
        patrimonio = 1;
    }
    else {
        patrimonio++;
    }
    
    $("#idpatrimonio3").val(patrimonio);
    doPopulaCatalogacao(patrimonio);
}

function doAnteriorCatalogacao() {
    var patrimonio = parseInt($("#idpatrimonio3").val());
    
    if(isNaN(patrimonio)) {
        patrimonio = 1;
    }
    else {
        patrimonio = Math.max(1, patrimonio - 1);
    }
    
    $("#idpatrimonio3").val(patrimonio);
    doPopulaCatalogacao(patrimonio);
}

function doProximaBusca() {
    var page = parseInt($("#idPaginaDestino").val());
    
    if(isNaN(page)) {
        page = 1;
    }
    else {
        page++;
    }
    
    $("#idPaginaDestino").val(page);
    doBusca();
}

function doAnteriorBusca() {
    var page = parseInt($("#idPaginaDestino").val());
    
    if(isNaN(page)) {
        page = 1;
    }
    else {
        page = Math.max(1, page - 1);
    }
    
    $("#idPaginaDestino").val(page);
    doBusca();
}

function doExcluir() {
    var patrimonio = parseInt($("#idpatrimonio3").val());
    
    if(isNaN(patrimonio)) {
         $("#idMsgDialogo3").html('Erro! Por favor especifique qual patrimônio você quer excluir.');
    }

    $.ajax({
        url: window.location.pathname + 'excluirServlet',
        method: 'POST',
        data: {
            patrimonio: patrimonio
        },
        success: function () {
            console.log('INFO: Excluir succeeded.');
            clear3();
            mostrarDiv(2);
            doBusca();
            window.alert('Exclusão do patrimonio ' + patrimonio + ' bem-sucedida!');
        },
        error: function () {
            console.log('INFO: Excluir failed.');
            $("#idMsgDialogo3").html('Erro desconhecido durante a exclusão solicitada.');
        }
    });   
}

function catalogoExisteEnableness(b) {
    if(b) {
        $("#idExcluir,#idSalvarAtual,#idSalvarNovo,#idSubmitFile").removeClass('pure-button-disabled');
        $("#idExcluir,#idSalvarAtual,#idSalvarNovo,#idSubmitFile").prop('disabled', false);
        $("#idNovoComentario").removeAttr('readonly');
    }
    else {
        $("#idExcluir,#idSalvarAtual,#idSalvarNovo,#idSubmitFile,#idAbrirArquivo").addClass('pure-button-disabled');
        $("#idExcluir,#idSalvarAtual,#idSalvarNovo,#idSubmitFile,#idAbrirArquivo").prop('disabled', true);
        $("#idNovoComentario").attr('readonly','');
    }
}

var editStatus = false;

function setEdit(b) {
    editStatus = b;
    $("#idMsgDialogo3").html('');
    
    if(editStatus) {
        $("#idtitulo3").removeAttr('readonly');
        $("#idautoria3").removeAttr('readonly');
        $("#idveiculo3").removeAttr('readonly');
        $("#iddatapublicacao3").removeAttr('readonly');
        $("#idpalchave3").removeAttr('readonly');
        
        $("#idSalvarAtual").removeClass('pure-button-disabled');
        $("#idSalvarAtual").prop('disabled', false);
    }
    else {
        $("#idtitulo3").attr('readonly', '');
        $("#idautoria3").attr('readonly', '');
        $("#idveiculo3").attr('readonly', '');
        $("#iddatapublicacao3").attr('readonly', '');
        $("#idpalchave3").attr('readonly', '');
        
        $("#idSalvarAtual").addClass('pure-button-disabled');
        $("#idSalvarAtual").prop('disabled', true);
    }
}

function toggleEdit() {
    console.log("INFO: " + arguments.callee.name);
    
    setEdit(!editStatus);
}

function prepareAddOrUpdate() {
    console.log("INFO: " + arguments.callee.name);
    
    var patrimonio = parseInt($("#idpatrimonio3").val());
    if(isNaN(patrimonio)) {
        $("#idpatrimonio3").val('1');
        patrimonio = 1;
    }
    
    var datapublicacao = $("#iddatapublicacao3").val();
    
    if(datapublicacao !== "") {
        if(!datapublicacao.match(dateRegex)) {
            $("#idMsgDialogo3").html('Erro! Data com formato inválido! Utilize YYYY-MM-DD.');
            return;
        }
    }
    
    $.ajax({
        url: window.location.pathname + 'adicionarServlet',
        method: 'POST',
        data: {
            patrimonio: patrimonio,
            titulo: $("#idtitulo3").val(),
            autoria: $("#idautoria3").val(),
            veiculo: $("#idveiculo3").val(),
            datapublicacao: datapublicacao,
            palchave: $("#idpalchave3").val()
        },
        success: function () {
            console.log('INFO: Adicionar succeeded.');
            $("#idMsgDialogo3").html('');
            catalogoExisteEnableness(true);
            window.alert('Informações salvas com sucesso no banco de dados!');
        },
        error: function () {
            console.log('INFO: Adicionar failed.');
            $("#idMsgDialogo3").html('Erro! Tentativa de adicionar ou atualizar um registro falhou.');
        }
    });  
}

function doNovoComentario() {
    console.log("INFO: " + arguments.callee.name);
    
    var patrimonio = parseInt($("#idpatrimonio3").val());
    if(isNaN(patrimonio)) {
        $("#idpatrimonio3").val('1');
        patrimonio = 1;
    }
    
    var comentarionovo = tinyMCE.activeEditor.getContent();
    
    if(comentarionovo === "") {
        $("#idMsgDialogo3").html('Erro! Você quer realmente inserir um comentário vazio?');
        return;
    }
    
    $.ajax({
        url: window.location.pathname + 'novoComentarioServlet',
        method: 'POST',
        data: {
            patrimonio: patrimonio,
            comentarionovo: comentarionovo
        },
        success: function () {
            console.log('INFO: Comentário novo succeeded.');
            $("#idMsgDialogo3").html('');
            addComentario(comentarionovo);
            window.alert('Novo comentário enviado com sucesso!');
        },
        error: function () {
            console.log('INFO: Comentário novo failed.');
            $("#idMsgDialogo3").html('Erro! Tentativa de adicionar um novo comentário falhou.');
        }
    });      
}

function doSubmeterArquivo() {
    console.log("INFO: " + arguments.callee.name);
    
    var patrimonio = parseInt($("#idpatrimonio3").val());
    if(isNaN(patrimonio)) {
        $("#idpatrimonio3").val('1');
        patrimonio = 1;
    }
    
    var nomearquivo = $("#idInputTypeFile").val();
    if(nomearquivo === "") {
        $("#idMsgDialogo3").html('Erro! Arquivo não especificado.');
        return;
    }
    
    var arquivobinario = document.getElementById('idInputTypeFile').files[0];
    
    var formData = new FormData();
    formData.append('patrimonio', patrimonio);
    formData.append('nomearquivo', nomearquivo);
    formData.append('arquivobinario', arquivobinario);
        
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'arquivoServlet');
        
    xhr.onload = function () {
        if (xhr.status === 200) {
            console.log('INFO: Submeter arquivo succeeded.');
            
            $("#idMsgDialogo3").html('');
            $("#idInputTypeFile").val('');
                
            $("#idAbrirArquivo").removeClass('pure-button-disabled');
            $("#idAbrirArquivo").prop('disabled', false);
                
            window.alert('Your file has been uploaded to the server successfully.');
        } 
        else {
            console.log('INFO: Submeter arquivo failed.');
            $("#idMsgDialogo3").html('Erro! Tentativa de submeter arquivo falhou.');
            return;
        }
    };
        
    xhr.send(formData);
}

function doAbrirArquivo() {
    console.log("INFO: " + arguments.callee.name);
    
    var patrimonio = parseInt($("#idpatrimonio3").val());
    if(isNaN(patrimonio)) {
        $("#idpatrimonio3").val('1');
        patrimonio = 1;
    }
    
    $.ajax({
        url: window.location.pathname + 'abrirArquivoServlet',
        method: 'POST',
        data: {
            patrimonio: patrimonio
        },
        success: function (data, status, jqxhr) {
            console.log('INFO: Abrir arquivo succeeded.');
            $("#idMsgDialogo3").html('');
            
            var form = document.createElement("form");
            form.setAttribute("method", "POST");
            form.setAttribute("action", "abrirArquivoServlet");
            form.setAttribute("target", "novaJanela_str");
            
            var element = document.createElement("input");
            element.setAttribute("type", "hidden");
            element.setAttribute("name", "patrimonio");
            element.setAttribute("value", patrimonio);
            
            document.body.appendChild(form);
            form.appendChild(element);
            form.submit();
        },
        error: function () {
            console.log('INFO: Abrir arquivo failed.');
            $("#idMsgDialogo3").html('Erro desconhecido ao tentar abrir o arquivo.');
        }
    });    
}