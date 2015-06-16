package com.minervabay.servlet;

import com.minervabay.entity.Dadoscatalogo;
import com.minervabay.facade.DadoscatalogoFacade;
import static com.minervabay.util.Utils.getBookPath;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author thiago
 */
public class abrirArquivoServlet extends HttpServlet {

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

        Integer patrimonio = Integer.parseInt(request.getParameter("patrimonio"));
        Dadoscatalogo dado = dadosFacade.find(patrimonio);
        String arquivo = dado.getArquivo();

        if (arquivo != null && !arquivo.isEmpty()) {
            try {
                File bookfile = new File(getBookPath(getServletContext(), arquivo));

                String mimeType = getServletContext().getMimeType(bookfile.getAbsolutePath());

                // set to binary type if MIME mapping not found
                if (mimeType == null) {
                    mimeType = "application/octet-stream";
                }

                response.setContentType(mimeType);
                response.setContentLength((int) bookfile.length());
                response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", bookfile.getName()));

                try (FileInputStream inStream = new FileInputStream(bookfile); OutputStream outStream = response.getOutputStream()) {
                    byte[] buffer = new byte[100000];
                    int bytesRead;
                    while ((bytesRead = inStream.read(buffer)) != -1) {
                        outStream.write(buffer, 0, bytesRead);
                    }
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
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
