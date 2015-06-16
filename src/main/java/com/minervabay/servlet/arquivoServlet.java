package com.minervabay.servlet;

import com.minervabay.entity.Dadoscatalogo;
import com.minervabay.facade.DadoscatalogoFacade;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author thiago
 */
public class arquivoServlet extends HttpServlet {

    @EJB
    private DadoscatalogoFacade dadosFacade;

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
        response.setContentType("text/html;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        Integer patrimonio = Integer.parseInt(request.getParameter("patrimonio"));
        String nomearquivo = request.getParameter("nomearquivo");

        Dadoscatalogo dado = dadosFacade.find(patrimonio);

        if (dado == null) {
            response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
            return;
        }

        String arquivo = dado.getArquivo();

        if (arquivo != null && !arquivo.isEmpty()) {
            System.out.println("=====TODO: trying delete");
            // TODO: try delete
        }
        
        String extension;
        int i = nomearquivo.lastIndexOf('.');
        if (i > 0) {
            extension = nomearquivo.substring(i); // --> e.g. ".pdf"
        }
        else {
            response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
            return;
        }
        String nomearquivonobd = patrimonio.toString() + extension;    

        dado.setArquivo(nomearquivonobd);
        dadosFacade.edit(dado);

        System.out.println("======TODO: trying file storage");
        // TODO: try file storage
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
