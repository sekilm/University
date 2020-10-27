package exam;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = { "/servlet/login" })
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String pass = request.getParameter("password");

        UserRepository repo = new UserRepository();
        User user = repo.getUser(username);

        boolean valid = false;

        if(user != null && user.password.equals(pass))
            valid = true;

        //RequestDispatcher rd;

        if(valid) {
            session.setAttribute("username", username);

            //rd = request.getRequestDispatcher("/mainPage.jsp");
            response.sendRedirect("/mainPage.jsp");
        }
        else {
            session.setAttribute("message", "Wrong username or password");

            //rd = request.getRequestDispatcher("/index.jsp");
            response.sendRedirect("/index.jsp");
        }

        //rd.forward(request, response);
    }
}
