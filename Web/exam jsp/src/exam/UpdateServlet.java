package exam;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "UpdateServlet", urlPatterns = { "/servlet/update" })
public class UpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        int topicid = Integer.parseInt(request.getParameter("topic-id"));
        int id = Integer.parseInt(request.getParameter("post-id"));
        String text = request.getParameter("topic-text");

        UserRepository repo = new UserRepository();

        repo.update(id, username, topicid, text);

        response.sendRedirect("/mainPage.jsp");
    }
}
