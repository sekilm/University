package lab8;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "VoteServlet", urlPatterns = { "/servlet/vote" })
public class VoteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserRepository repo = new UserRepository();

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String imagePath = request.getParameter("image-name");

        repo.vote(imagePath, username);

        response.sendRedirect("/mainPage.jsp");
    }
}
