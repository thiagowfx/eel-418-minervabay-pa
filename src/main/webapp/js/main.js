$(document).ready(function () {
    mostrarDiv(1);

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
});

function setSpinnerRotating(status) {
    spinner = $('#idSpinner i');
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

//function doLogin() {
//    $.ajax({
//        url: window.location.pathname + 'loginServlet',
//        method: 'POST',
//        data: {
//            user: $('#idUsuario').val(),
//            password: $('#idSenha').val()
//        },
//        success: function () {
//            console.log('INFO: Login succeeded.');
//            $('#idRespAutorizacao').html('');
//            clearLogin();
//            mostrarDiv(2);
//        },
//        error: function () {
//            console.log('INFO: Login failed.');
//            $('#idRespAutorizacao').html('Nome de usuário ou senha incorretos.');
//        }
//    });
//}

function doLogin() {
    console.log('INFO: Login succeeded.');
    $('#idRespAutorizacao').html('');
    clearLogin();
    mostrarDiv(2);
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

function clearCatalogo() {
    $('#idpatrimonio3').val('');
    $('#idtitulo3').val('');
    $('#idautoria3').val('');
    $('#idveiculo3').val('');
    $('#iddatapublicacao3').val('');
    $('#idpalchave3').val('');
    $('#idInputTypeFile').val('');
    $('#idNovoComentario').val('');
    $('#idComentarios').val('');
    $('#idMsgDialogo3').html('');
}
clear3 = clearCatalogo;

function getJsonBusca() {
    var jsonBusca = {
        patrimonio: $("#idpatrimonio2").val(),
        titulo: $("#idtitulo2").val(),
        autoria: $("#idautoria2").val(),
        veiculo: $("#idveiculo2").val(),
        datapublicacao: $("#iddatapublicacao2").val(),
        palchave: $("#idpalchave2").val(),
        paginadestino: $("#idPaginaDestino").val(),
        checkpatrimonio: $("#idcheckpatrimonio").prop('checked'),
        checktituloOU: $("#idchecktituloOU").prop('checked'),
        checktituloE:  $("#idchecktituloE").prop('checked'),
        checkautoriaOU: $("#idcheckautoriaOU").prop('checked'),
        checkautoriaE:  $("#idcheckautoriaE").prop('checked'),
        checkveiculoOU: $("#idcheckveiculoOU").prop('checked'),
        checkveiculoE:  $("#idcheckveiculoE").prop('checked'),
        checkdatapublicacaoOU: $("#idcheckdatapublicacaoOU").prop('checked'),
        checkdatapublicacaoE:  $("#idcheckdatapublicacaoE").prop('checked'),
        checkpalchaveOU: $("#idcheckpalchaveOU").prop('checked'),
        checkpalchaveE:  $("#idcheckpalchaveE").prop('checked')
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
    
    if(json.response.length === 0) {
        $("#idTabelaResultados").html('<p><strong>Nenhum livro correspondente à busca foi encontrado no banco de dados.</strong></p>');
        return;
    }
    
    $.each(json.response, function(index, value) {
        link = '#?index=' + value.patrimonio;
        description = value.patrimonio + ": " + value.titulo + " - " + value.autoria; 
        list.append('<li><a target="_blank" href="' + link + '">' + description + '</a></li><br />');
    });
}

function doBusca() {
    console.log("INFO: " + arguments.callee.name);
    $("#idMsgDialogo2").html('');
    $("#idTabelaResultados").html('');
    
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
            var jsonResposta = data;
            console.log("INFO: Json do servlet da busca:");
            console.log(jsonResposta);
            popularBusca(data);
        },
        error: function () {
            $("#idMsgDialogo2").html('Um erro inesperado ocorreu no AJAX da busca.');
        }
    });
}