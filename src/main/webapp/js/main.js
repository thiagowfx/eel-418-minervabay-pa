$(document).ready(function () {
    var index = getUrlParameter('index');
    if (index === undefined || index === "") {
        mostrarDiv(1);
    }
    else {
        doPopulaCatalogacao(index);
    }
    
    /*
     * Enableness
     */
    updatePatrimonioEnableness();
    updatePaginaBuscaEnableness();

    /*
     * Inject listeners/callbacks
     */
    $(document)
            .ajaxSend(function () {
            setSpinnerRotating(true);
        })
            .ajaxStop(function () {
            setSpinnerRotating(false);
        });

    $("#idEntrar").click(doLogin);

    $("#idLimparBusca").click(clearBusca);
    $("#idLimparCat").click(clearCatalogo);
    
    $("#idBuscar").click(doBusca);
    $("#idPagProxima").click(doProximaBusca);
    $("#idPagAnterior").click(doAnteriorBusca);
    
    $("#idItemProximo").click(doProximaCatalogacao);
    $("#idItemAnterior").click(doAnteriorCatalogacao);
    
    $("#idExcluir").click(doExcluir);
});

// Upstream: http://stackoverflow.com/questions/19491336/get-url-parameter-jquery
function getUrlParameter(sParam) {
    var sPageURL = window.location.search.substring(1), sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++) {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam) {
            return sParameterName[1];
        }
    }
} 

function setSpinnerRotating(status) {
    var spinner = $('#idSpinner i');
    if(status) {
        spinner.addClass('fa-spin');
    } else {
        spinner.removeClass('fa-spin');
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
    $('#idNovoComentario').val('');
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
    
    $("input[id^='id'][id$='OU'],#idcheckpatrimonio").each(function(index) {
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

function popularCatalogacao(json) {
    console.log("INFO: " + arguments.callee.name);
    
    $('#idtitulo3').val(json.titulo);
    $('#idautoria3').val(json.autoria);
    $('#idveiculo3').val(json.veiculo);
    $('#iddatapublicacao3').val(json.datapublicacao);
    $('#idpalchave3').val(json.palchave);
    
    $("#idComentarios").append("<ol>");
    var list = $("#idComentarios>ol");
    
    if(json.comentarios.length === 0) {
        $("#idComentarios").html("<p><strong>Nenhum comentário para esse livro.</strong></p>");
    }
    else {
        $.each(json.comentarios, function(index, value) {
            list.append('<li>' + value + '</li>');
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
        $("#idPagAnterior").addClass('pure-button-disabled')
        $("#idPagAnterior").prop('disabled', true);
    } else {
        $("#idPagAnterior").removeClass('pure-button-disabled')
        $("#idPagAnterior").prop('disabled', false);
    }
}

function doPopulaCatalogacao(patrimonio) {
    console.log("INFO: " + arguments.callee.name);
    mostrarDiv(3);
    $("#idMsgDialogo3").html('');
    $("#idComentarios").html('');
    $("#idNovoComentario").val('');
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
            $("#idMsgDialogo3").html('Erro! Um erro inesperado ocorreu no AJAX da catalogação.');
            catalogoExisteEnableness(false);
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
            $("#idMsgDialogo3").html('Erro! Tentativa de exclusão de patrimônio não existente.');
        }
    });   
}

function catalogoExisteEnableness(b) {
    if(b) {
        $("#idExcluir,#idSalvarAtual").removeClass('pure-button-disabled');
        $("#idExcluir,#idSalvarAtual").prop('disabled', false);
    }
    else {
        $("#idExcluir,#idSalvarAtual").addClass('pure-button-disabled');
        $("#idExcluir,#idSalvarAtual").prop('disabled', true);
    }
}