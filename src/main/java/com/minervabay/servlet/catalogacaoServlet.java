package com.minervabay.servlet;

import com.minervabay.entity.Dadoscatalogo;
import com.minervabay.entity.PalavrasChave;
import com.minervabay.facade.DadoscatalogoFacade;
import com.minervabay.facade.PalavrasChaveFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map.Entry;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author thiago
 */
public class catalogacaoServlet extends HttpServlet {

    @EJB
    private DadoscatalogoFacade dadoscatalogoFacade;
    
    @EJB
    private PalavrasChaveFacade pcFacade;

    /**
     * @see http://stackoverflow.com/questions/26346060/javax-json-add-new-jsonnumber-to-existing-jsonobject
     */
    private JsonObjectBuilder jsonObjectToBuilder(JsonObject jo) {
        JsonObjectBuilder job = Json.createObjectBuilder();

        for (Entry<String, JsonValue> entry : jo.entrySet()) {
            job.add(entry.getKey(), entry.getValue());
        }

        return job;
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
        int patrimonio = Integer.parseInt(request.getParameter("patrimonio"));
        patrimonio = 2;

        Dadoscatalogo dado = dadoscatalogoFacade.find(patrimonio);
        
        String palChave = new String();
        
        List<PalavrasChave> palsChave = pcFacade.findAllByPatrimonio(patrimonio);
        for(int i = 0; i < palsChave.size(); ++i) {
            palChave += palsChave.get(i).getPalchave();
            if(i < palsChave.size() - 1) {
                palChave += "; ";
            }
        }

        JsonObject jsonResp = jsonObjectToBuilder(dado.toJson())
                .add("palchave", palChave)
                .build();

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(jsonResp);
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
