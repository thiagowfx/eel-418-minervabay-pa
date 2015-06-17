package com.minervabay.servlet;

import com.minervabay.entity.Dadoscatalogo;
import com.minervabay.facade.ComentariosFacade;
import com.minervabay.facade.DadoscatalogoFacade;
import com.minervabay.facade.PalavrasChaveFacade;
import static com.minervabay.util.Utils.getBookPath;
import java.io.File;
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
public class excluirServlet extends HttpServlet {

    @EJB
    private DadoscatalogoFacade dadosFacade;
    
    @EJB
    private PalavrasChaveFacade palavrasFacade;
    
    @EJB
    private ComentariosFacade comentariosFacade;

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

        int patrimonio = Integer.parseInt(request.getParameter("patrimonio"));
        Dadoscatalogo dado = dadosFacade.find(patrimonio);

        if (dado != null) {
            palavrasFacade.removeByPatrimonio(patrimonio);
            comentariosFacade.removeByPatrimonio(patrimonio);
            dadosFacade.remove(dado);

            String arquivo = dado.getArquivo();
            if (arquivo != null && !arquivo.isEmpty()) {
                try {
                    new File(getBookPath(getServletContext(), arquivo)).delete();
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
                    return;
                }
            }

            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
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
