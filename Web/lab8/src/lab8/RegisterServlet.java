package lab8;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = { "/servlet/register" })
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");

        UserRepository repo = new UserRepository();

        boolean valid = true;

        String pass = request.getParameter("password");
        String cpass = request.getParameter("confirm-password");
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        String email = request.getParameter("email");

        if(username.equals("") || pass.equals("") || cpass.equals("") || firstName.equals("") || lastName.equals("") ||
                email.equals(""))
            valid = false;

        if(repo.checkUsername(username))
            valid = false;

        if(!pass.equals(cpass))
            valid = false;

        if(valid) {
            repo.addUser(new User(username, pass, firstName, lastName, email));
            System.out.println(new User(username, pass, firstName, lastName, email));

            response.sendRedirect("/index.jsp");
        }
        else {
            session.setAttribute("message", "The username is already taken, please try another");

            response.sendRedirect("/register.jsp");
        }
    }
}