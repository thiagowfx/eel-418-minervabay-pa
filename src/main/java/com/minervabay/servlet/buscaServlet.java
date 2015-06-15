package com.minervabay.servlet;

import com.minervabay.entity.*;
import com.minervabay.facade.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
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
    private DadoscatalogoFacade dadoscatalogoFacade;
    
    private final int RESULTS_PER_PAGE = 2;
    
    private int[] calculateRange(int page) {
        int[] ans = new int[2];
        ans[0] = (page -1) * RESULTS_PER_PAGE;
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
        
        if(buscarPatrimonio && !patrimonio.isEmpty()) {
            Dadoscatalogo dado = dadoscatalogoFacade.find(Integer.parseInt(patrimonio));
            dados.add(dado);
        }
        else if (buscarPatrimonio) {
            dados = dadoscatalogoFacade.findRange(calculateRange(pagina));
        }
        else {
            boolean checktituloE = request.getParameter("idchecktituloE").compareTo("true") == 0;
            boolean checkautoriaE = request.getParameter("idcheckautoriaE").compareTo("true") == 0;
            boolean checkveiculoE = request.getParameter("idcheckveiculoE").compareTo("true") == 0;
            boolean checkdatapublicacaoE = request.getParameter("idcheckdatapublicacaoE").compareTo("true") == 0;
            boolean checkpalchaveE = request.getParameter("idcheckpalchaveE").compareTo("true") == 0;
            
            if(checktituloE) {
                // TODO
            }
        }
        
        // TODO: pages
        for(Dadoscatalogo dado: dados) {
            booksArrayBuilder.add(dado.toJson());
        }
        
        JsonArray booksArray = booksArrayBuilder.build();
        JsonObject jsonObj = Json.createObjectBuilder()
                .add("response", booksArray)
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
