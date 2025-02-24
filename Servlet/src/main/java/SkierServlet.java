import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(value = "/skiers/*")
public class SkierServlet extends HttpServlet{
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
    String urlPath = req.getPathInfo();
    if (urlPath == null || urlPath.isEmpty()) {
      res.setContentType("application/json");
      res.setCharacterEncoding("UTF-8");
      res.getWriter().print("{ \"message\": \"string\"}");
      res.getWriter().flush();
      return;
    }

    String[] urlParts = urlPath.split("/");
    // and now validate url path and return the response status code
    // (and maybe also some value if input is valid)

    if (isUrlValid(urlParts)) {
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } else {
      res.setStatus(HttpServletResponse.SC_OK);
      // do any sophisticated processing with urlParts which contains all the url params
      // TODO: process url params in `urlParts`
      res.getWriter().write("It works!");
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
    String urlPath = req.getPathInfo();

    // check we have a URL!
    if (urlPath == null || urlPath.isEmpty()) {
      res.setContentType("application/json");
      res.setCharacterEncoding("UTF-8");
      res.getWriter().print("{ \"message\": \"string\"}");
      res.getWriter().flush();
      return;
    }

    String[] urlParts = urlPath.split("/");
    // and now validate url path and return the response status code
    // (and maybe also some value if input is valid)

    if (isUrlValid(urlParts)) {
      if (isValidData(urlParts)) {
        String message = "{ \"resortID\": " + urlParts[1] + ", \"seasonID\": " + urlParts[3] + ", \"dayID\": " + urlParts[5] + ", \"skierID\": " + urlParts[7] +" }";
        res.setStatus(HttpServletResponse.SC_CREATED);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().print(message);
        res.getWriter().flush();
      } else {
        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().print("{ \"message\": \"Bad Request\"}");
        res.getWriter().flush();
      }
    } else {
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
      res.setContentType("application/json");
      res.setCharacterEncoding("UTF-8");
      res.getWriter().print("{ \"message\": \"Dat Not Found\"}");
      res.getWriter().flush();
    }
  }

  private boolean isUrlValid(String[] urlParts) {
    // urlPath  = "/1/seasons/2019/day/1/skier/123"
    // urlParts = [, 1, seasons, 2019, day, 1, skier, 123]
    if (urlParts.length != 8) {
      return false;
    }
    if (!urlParts[2].equals("seasons") || !urlParts[4].equals("days") || !urlParts[6].equals("skiers")) {
      return false;
    }
    if (!urlParts[1].matches("(0|[1-9]\\d*)") || !urlParts[3].matches("(0|[1-9]\\d*)") || !urlParts[5].matches("(0|[1-9]\\d*)") || !urlParts[7].matches("(0|[1-9]\\d*)") ) {
      return false;
    }
    return true;
  }

  private  boolean isValidData(String[] urlParts) {
    // TODO: validate data
    return true;
  }
}