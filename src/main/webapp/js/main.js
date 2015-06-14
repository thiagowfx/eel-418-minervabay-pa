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

function doLogin() {
    $.ajax({
        url: window.location.pathname + 'loginServlet',
        method: 'POST',
        data: {
            user: $('#idUsuario').val(),
            password: $('#idSenha').val()
        },
        success: function () {
            console.log('Login succeeded.');
            $('#idRespAutorizacao').html('');
            clearLogin();
            mostrarDiv(2);
        },
        error: function () {
            console.log('Login failed.');
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