package exam;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AddServlet", urlPatterns = { "/servlet/add" })
public class AddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        int id = Integer.parseInt(request.getParameter("topic-id"));
        String text = request.getParameter("topic-text");

        UserRepository repo = new UserRepository();

        repo.addPost(username, id, text);

        response.sendRedirect("/mainPage.jsp");
    }
}
