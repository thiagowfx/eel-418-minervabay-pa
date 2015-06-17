package com.minervabay.servlet;

import com.minervabay.entity.*;
import com.minervabay.facade.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author thiago
 */
public class buscaServlet extends HttpServlet {

    @EJB
    private DadoscatalogoFacade dadosFacade;

    private final int RESULTS_PER_PAGE = 2;

    private int[] calculateRange(int page) {
        int[] ans = new int[2];
        ans[0] = (page - 1) * RESULTS_PER_PAGE;
        ans[1] = page * RESULTS_PER_PAGE - 1;
        return ans;
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Dadoscatalogo> dados = new ArrayList<>();
        JsonArrayBuilder booksArrayBuilder = Json.createArrayBuilder();

        boolean buscarPatrimonio = request.getParameter("idcheckpatrimonio").compareTo("true") == 0;
        String patrimonio = request.getParameter("idpatrimonio2");
        int pagina = Integer.parseInt(request.getParameter("idPaginaDestino"));

        if (buscarPatrimonio && !patrimonio.isEmpty()) {
            Dadoscatalogo dado = dadosFacade.find(Integer.parseInt(patrimonio));
            dados.add(dado);
        } else if (buscarPatrimonio) {
            dados = dadosFacade.findRange(calculateRange(pagina));
        } else {
            boolean checktituloE = request.getParameter("idchecktituloE").compareTo("true") == 0;
            boolean checkautoriaE = request.getParameter("idcheckautoriaE").compareTo("true") == 0;
            boolean checkveiculoE = request.getParameter("idcheckveiculoE").compareTo("true") == 0;
            boolean checkdatapublicacaoE = request.getParameter("idcheckdatapublicacaoE").compareTo("true") == 0;
            boolean checkpalchaveE = request.getParameter("idcheckpalchaveE").compareTo("true") == 0;

            boolean checktituloOU = request.getParameter("idchecktituloOU").compareTo("true") == 0;
            boolean checkautoriaOU = request.getParameter("idcheckautoriaOU").compareTo("true") == 0;
            boolean checkveiculoOU = request.getParameter("idcheckveiculoOU").compareTo("true") == 0;
            boolean checkdatapublicacaoOU = request.getParameter("idcheckdatapublicacaoOU").compareTo("true") == 0;
            boolean checkpalchaveOU = request.getParameter("idcheckpalchaveOU").compareTo("true") == 0;

            String titulo = request.getParameter("idtitulo2");
            String autoria = request.getParameter("idautoria2");
            String veiculo = request.getParameter("idveiculo2");
            String palchave = request.getParameter("idpalchave2");

            String datapublicacao_str = request.getParameter("iddatapublicacao2");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date datapublicacao;
            try {
                datapublicacao = df.parse(datapublicacao_str);
            }
            catch(Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            
            boolean atlone = checktituloE || checkautoriaE || checkveiculoE || checkdatapublicacaoE || checkpalchaveE || checktituloOU || checkautoriaOU || checkveiculoOU || checkdatapublicacaoOU || checkpalchaveOU;
            StringBuilder sb;
            
            if(checktituloE) {
                
            }
            
            String ourwhere = ""
                    + "d.titulo LIKE :titulo"
                    + " AND "
                    + "d.autoria = :autoria"
                    + " AND "
                    + "d.veiculo = :veiculo"
                    + " AND "
                    + "d.palchave = :palchave"
                    + " AND "
                    + "d.datapublicacao = :datapublicacao"
                    + " AND "
                    + "("
                    + "d.titulo = :titulo"
                    + " OR "
                    + "d.autoria = :autoria"
                    + " OR "
                    + "d.veiculo = :veiculo"
                    + " OR "
                    + "d.palchave = :palchave"
                    + " OR "
                    + "d.datapublicacao = :datapublicacao"
                    + ")"
                    ;
            
            Query query = dadosFacade.getEntityManager().createQuery("SELECT d FROM Dadoscatalogo d WHERE (" + ourwhere + ")");
            dados = query.getResultList();
        }

        for (Dadoscatalogo dado : dados) {
            booksArrayBuilder.add(dado.toJson());
        }

        JsonArray booksArray = booksArrayBuilder.build();
        JsonObject jsonObj = Json.createObjectBuilder()
                .add("justanarray", booksArray)
                .build();

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(jsonObj);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
