<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="info" tagdir="/WEB-INF/tags/"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- basic -->
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <!-- metadata -->
        <meta name="author" content="<info:authorname />">
        <meta name="application-name" content="<info:appname />">
        <meta name="description" content="<info:appname />">
        <meta name="keywords" content="Biblioteca, Library, <info:appname />, <info:authorname />">
        <title><info:appname /></title>
        
        <!-- favicon -->
        <link rel="icon" href="favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
        
        <!-- stylesheets -->
        <link rel="stylesheet" href="bower_components/font-awesome/css/font-awesome.min.css" />
        <link rel="stylesheet" href="bower_components/pure/pure-min.css" />
        <link rel="stylesheet" href="bower_components/hover/css/hover-min.css" media="all" />
        <link rel="stylesheet" href="css/main.css" type="text/css" />
        
        <!-- scripts -->
        <script type="text/javascript" src="bower_components/jquery/dist/jquery.min.js"></script>
        <script type="text/javascript" src="bower_components/tinymce/tinymce.min.js"></script>
        <script>tinymce.init({
            selector: '#idNovoComentario',
            setup: function(ed) {
                ed.on('init', function() {
                    this.execCommand("fontSize", false, "18px");
                });
            },
            // forced_root_block : ''
        });
        </script>
        <script type="text/javascript" src="js/main.js"></script>
    </head>

    <body id="idBody">
        <div id="idDivMaisExterna">
            <h1 class="title"><info:appname /></h1>

            <div id="idDivLogin">
                <form class="pure-form pure-form-aligned">
                    <fieldset>
                        <div class="pure-control-group">
                            <label for="idUsuario"><i class="fa fa-user"></i> Usuário</label>
                            <input id="idUsuario" class="pure-input-rounded" type="text" placeholder="Usuário" style="width: 200px"/>
                            <br />
                        </div>
                        <div class="pure-control-group">
                            <label for="idSenha"><i class="fa fa-key"></i> Senha</label>
                            <input id="idSenha" class="pure-input-rounded" type="password" placeholder="Senha" style="width: 200px"/>
                            <br />
                        </div>
                        <p class="status" id="idRespAutorizacao"></p>
                        <button id="idEntrar" class="pure-button hvr-float-shadow pure-button-primary" type="button">
                            <i class="fa fa-sign-in"></i> Entrar
                        </button>
                        <button class="pure-button hvr-float-shadow" type="reset">
                            <i class="fa fa-eraser"></i> Limpar
                        </button>
                    </fieldset>
                </form>
            </div>
            
            <div id="idDivBusca">
                <table class="pure-table pure-table-striped" align="center" border="0">
                    <thead>
                    <tr> 
                        <td width="160" align="left" style="font-weight: bold">
                            <a href="#" onclick="mostrarDiv(3)" class="pure-button hvr-float-shadow pure-button-primary"><i class="fa fa-book"></i> Catalogação</a>
                        </td>
                        <td width="500" align="center" style="font-weight: bold" class="title">
                            <h3>BUSCA</h3>
                        </td>
                        <td width="145" align="right" style="font-weight: bold">
                            <a href="#" onclick="mostrarDiv(1)" class="pure-button hvr-float-shadow"><i class="fa fa-sign-out"></i> Sair</a>
                        </td>
                    </tr>
                    </thead>
                    
                    <tbody>
                    <tr>
                        <td class="status" id="idMsgDialogo2" colspan="3"></td>
                    </tr>
                    <tr>
                        <td align="right">
                            #Patrimônio
                        </td>
                        <td align="left" colspan="2">
                            <input type="text"     id="idpatrimonio2"/>
                            <input checked="checked" type="checkbox" id="idcheckpatrimonio"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            Título
                        </td>
                        <td align="left" colspan="2">
                            <input type="text"     id="idtitulo2"/>
                            <input type="checkbox" id="idchecktituloOU"/> (OU)
                            <input type="checkbox" id="idchecktituloE"/> (E)
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            Autoria
                        </td>
                        <td align="left" colspan="2">
                            <input type="text"     id="idautoria2"/>
                            <input type="checkbox" id="idcheckautoriaOU"/> (OU)
                            <input type="checkbox" id="idcheckautoriaE"/> (E)
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            Veículo
                        </td>
                        <td align="left" colspan="2">
                            <input type="text"     id="idveiculo2"/>
                            <input type="checkbox" id="idcheckveiculoOU"/> (OU)
                            <input type="checkbox" id="idcheckveiculoE"/> (E)
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            Data da publicação
                        </td>
                        <td align="left" colspan="2">
                            <input type="text"     id="iddatapublicacao2"/>
                            <input type="checkbox" id="idcheckdatapublicacaoOU"/> (OU)
                            <input type="checkbox" id="idcheckdatapublicacaoE"/> (E)
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            Palavras-chave
                        </td>
                        <td align="left" colspan="2">
                            <input type="text"     id="idpalchave2"/>
                            <input type="checkbox" id="idcheckpalchaveOU"/> (OU)
                            <input type="checkbox" id="idcheckpalchaveE"/> (E)
                        </td>
                    </tr>
                    <tr valign="top">
                        <td align="right">
                            Resultados
                        </td>
                        <td align="left" colspan="2">
                            <div id="idTabelaResultados">

                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td align="center" colspan="3">
                            Nº da página de resultados
                        </td>
                    </tr>
                    <tr>
                        <td align="center" colspan="3">
                            <button class="pure-button hvr-float-shadow" type="button" id="idPagAnterior"><i class="fa fa-arrow-left"></i> Anterior</button>
                            <input type="text" id="idPaginaDestino" style="width: 70px; text-align:center;">
                            <button class="pure-button hvr-float-shadow" type="button" id="idPagProxima"><i class="fa fa-arrow-right"></i> Próxima</button>
                        </td>
                    </tr>
                    <tr valign="top">
                        <td align="center" colspan="3">
                            <button class="pure-button hvr-float-shadow pure-button-primary" type="button" id="idBuscar"><i class="fa fa-search"></i> Buscar</button>
                            <button class="pure-button hvr-float-shadow" type="button" id="idLimparBusca"><i class="fa fa-eraser"></i> Limpar</button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            
            <div id="idDivCatalogacao">
                <table class="pure-table pure-table-striped" align="center" border="0">
                    <thead>
                    <tr>
                        <td width="240" align="left" style="font-weight: bold">
                            <a href="#" onclick="mostrarDiv(2)" class="pure-button hvr-float-shadow pure-button-primary"><i class="fa fa-search"></i> Busca</a>
                        </td>
                        <td width="540" align="center" class="title">
                            <h3>CATALOGAÇÃO</h3>
                        </td>
                        <td width="160" align="right" style="color:red;font-weight:bold;">
                            <a href="#" onclick="mostrarDiv(1)" class="pure-button hvr-float-shadow"><i class="fa fa-sign-out"></i> Sair</a>
                        </td>
                    </tr>
                    </thead>
                        
                    <tbody>
                    <tr>
                        <td class="status" id="idMsgDialogo3" colspan="3"></td>
                    </tr>
                    <tr>
                        <td style="width: 275px" align="right">
                            <button class="pure-button hvr-float-shadow" type="button" id="idItemAnterior"><i class="fa fa-arrow-left"></i> Anterior</button>&nbsp;&nbsp;#Patrimônio
                        </td>
                        <td align="left" colspan="2">
                            <input type="text" id="idpatrimonio3" readonly/>&nbsp;
                            <button class="pure-button hvr-float-shadow" type="button" id="idItemProximo"><i class="fa fa-arrow-right"></i> Próximo</button>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            Título
                        </td>
                        <td align="left" colspan="2">
                            <input type="text" id="idtitulo3" readonly/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            Autoria
                        </td>
                        <td align="left" colspan="2">
                            <input type="text" id="idautoria3" readonly/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            Veículo
                        </td>
                        <td align="left" colspan="2">
                            <input type="text" id="idveiculo3" readonly/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            Data da publicação
                        </td>
                        <td align="left" colspan="2">
                            <input type="text" id="iddatapublicacao3" readonly/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            Palavras-chave
                        </td>
                        <td align="left" colspan="2">
                            <input type="text" id="idpalchave3" readonly/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            <button class="pure-button hvr-float-shadow pure-button-primary" type="button" id="idSubmitFile"><i class="fa fa-upload"></i> Submeter arquivo</button>
                        </td>
                        <td align="left" colspan="2">
                            <input type="file" id="idInputTypeFile"/>
                        </td>
                    </tr>
                    <tr valign="top">
                        <td align="right">
                            Novo comentário
                        </td>
                        <td align="left" colspan="2">
                            <textarea id="idNovoComentario" rows="6" cols="100"></textarea>
                        </td>
                    </tr>
                    <tr valign="top">
                        <td align="right">
                            Comentários já feitos
                        </td>
                        <td align="left" colspan="2">
                            <div id="idComentarios"></div>
                        </td>
                    </tr>
                    <tr valign="top">
                        <td align="center" colspan="3">
                            <button class="pure-button pure-button-primary hvr-float-shadow" type="button" id="idAbrirArquivo"><i class="fa fa-folder-open"></i> Abrir arquivo</button>
                            <button class="pure-button pure-button-primary hvr-float-shadow" type="button" id="idEditar"><i class="fa fa-pencil"></i> Editar</button>
                            <button class="pure-button pure-button-primary hvr-float-shadow" type="button" id="idSalvarNovo"><i class="fa fa-save"></i> Enviar novo comentário</button>
                        </td>
                    </tr>
                    <tr valign="top">
                        <td align="center" colspan="3">
                            <button class="pure-button pure-button-primary hvr-float-shadow" type="button" id="idSalvarAtual"><i class="fa fa-save"></i> Salvar patrimônio</button>
                            <button class="pure-button hvr-float-shadow" type="button" id="idLimparCat"><i class="fa fa-eraser"></i> Limpar</button>
                            <button class="pure-button hvr-float-shadow" type="button" id="idExcluir"><i class="fa fa-trash-o"></i> Excluir</button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <!-- footer -->
            <hr />
            <div class="footer">
                &copy; <info:authorname /> 2015 &lt;<a style="text-decoration: none" href="mailto:<info:authoremail />"><info:authoremail /></a>&gt; &bull; <a href="<info:authorhomepage />">homepage</a>
            </div>
            <div>
                <span style="color: #cc3300">Patrocínio:</span><br />
                <img alt="Leão da Floresta" style="height: 50px" src="leao.png" />
            </div>
        </div>
    </body>
</html>
