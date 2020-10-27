package lab8;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.Paths;

@WebServlet(name = "UploadServlet", urlPatterns = { "/servlet/upload" })
@MultipartConfig
public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        Part image = request.getPart("image");
        String filename = Paths.get(image.getSubmittedFileName()).getFileName().toString();
        InputStream fileContent = image.getInputStream();

        byte[] buffer = new byte[fileContent.available()];
        fileContent.read(buffer);

        File targetFile = new File("H:\\School\\Facultate\\sem 4\\Web\\lab8\\images\\" + filename);
        OutputStream outputStream = new FileOutputStream(targetFile);
        outputStream.write(buffer);
        outputStream.close();

        UserRepository repo = new UserRepository();

        repo.addImageToUser(filename, username);

        response.sendRedirect("/uploadSuccess.jsp");
    }
}
