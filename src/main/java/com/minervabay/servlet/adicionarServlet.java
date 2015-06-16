package com.minervabay.servlet;

import com.minervabay.entity.Dadoscatalogo;
import com.minervabay.entity.PalavrasChave;
import com.minervabay.facade.DadoscatalogoFacade;
import com.minervabay.facade.PalavrasChaveFacade;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author thiago
 */
public class adicionarServlet extends HttpServlet {

    @EJB
    private DadoscatalogoFacade dadosFacade;

    @EJB
    private PalavrasChaveFacade palchaveFacade;

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

        int patrimonio = Integer.parseInt(request.getParameter("patrimonio"));
        String titulo = request.getParameter("titulo");
        String autoria = request.getParameter("autoria");
        String veiculo = request.getParameter("veiculo");
        String datapublicacao = request.getParameter("datapublicacao");
        String palchave = request.getParameter("palchave");
        // TODO: mais dados

        Dadoscatalogo dado = dadosFacade.find(patrimonio);
        boolean existant;

        if (dado == null) {
            dado = new Dadoscatalogo(patrimonio);
            existant = false;
        } else {
            existant = true;
        }

        dado.setTitulo(titulo);
        dado.setAutoria(autoria);
        dado.setVeiculo(veiculo);

        if (datapublicacao.isEmpty()) {
            dado.setDataPublicacao(new Date());
        } else {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                dado.setDataPublicacao(df.parse(datapublicacao));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        
        palchaveFacade.removeByPatrimonio(patrimonio);
        
        if (!palchave.trim().isEmpty()) {
            String[] keywords = palchave.trim().split(";");
            for (String keyword : keywords) {
                keyword = keyword.trim();

                PalavrasChave p = new PalavrasChave();
                p.setPatrimonio(dado);
                p.setPalchave(keyword);
                palchaveFacade.create(p);
            }
        }

        try {
            if (existant) {
                dadosFacade.edit(dado);
            } else {
                dadosFacade.create(dado);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
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
