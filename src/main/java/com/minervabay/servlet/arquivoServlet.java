package com.minervabay.servlet;

import com.minervabay.entity.Dadoscatalogo;
import com.minervabay.facade.DadoscatalogoFacade;
import static com.minervabay.util.Utils.getBookPath;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author thiago
 */
@MultipartConfig(maxFileSize = 50 * 1024 * 1024, maxRequestSize = 50 * 1024 * 1024, fileSizeThreshold = 5 * 1024 * 1024)
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

        Collection parts = request.getParts();

        Integer patrimonio = Integer.parseInt(request.getParameter("patrimonio"));
        String nomearquivo = request.getParameter("nomearquivo");
        Part arquivobinario = request.getPart("arquivobinario");

        Dadoscatalogo dado = dadosFacade.find(patrimonio);

        if (dado == null) {
            response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
            return;
        }

        String arquivo = dado.getArquivo();

        if (arquivo != null && !arquivo.isEmpty()) {
            try {
                new File(getBookPath(getServletContext(), arquivo)).delete();
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
                return;
            }
        }

        String extension;
        int i = nomearquivo.lastIndexOf('.');
        if (i > 0) {
            extension = nomearquivo.substring(i); // --> e.g. ".pdf"
        } else {
            response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
            return;
        }
        String nomearquivonobd = patrimonio.toString() + extension;

        dado.setArquivo(nomearquivonobd);
        dadosFacade.edit(dado);

        File file = new File(getBookPath(getServletContext(), nomearquivonobd));

        try (InputStream inStream = arquivobinario.getInputStream(); OutputStream outStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[100000];
            int bytesRead;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
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
