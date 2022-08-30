package com.brianniyue.backend.servlet;

import com.brianniyue.backend.external.TwitchException;
import com.brianniyue.backend.external.TwitchClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        TwitchClient client = new TwitchClient();

        String gameId = request.getParameter("game_id");
        String gameName = request.getParameter("game_name");
        if (gameName != null){
            gameId = client.searchGame(gameName).getId();
        }
        if (gameId == null && gameName == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            ServletUtil.writeItemMap(response, client.searchItems(gameId));
        } catch (TwitchException e) {
            throw new ServletException(e);
        }
    }
}