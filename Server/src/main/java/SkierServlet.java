import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

@WebServlet("/skiers")
public class SkierServlet extends HttpServlet {
  private static final Gson gson = new Gson();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

    BufferedReader reader = req.getReader();
    LiftRide liftRide;
    try {
      liftRide = gson.fromJson(reader, LiftRide.class);
    } catch (JsonSyntaxException e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().write("{\"error\":\"Invalid JSON format\"}");
      return;
    }

    if (liftRide == null || !isValidLiftRide(liftRide)) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().write("{\"error\":\"Invalid request parameters\"}");
      return;
    }

    resp.setStatus(HttpServletResponse.SC_CREATED);
    PrintWriter out = resp.getWriter();
    out.print("{\"message\":\"Lift ride recorded successfully\"}");
    out.flush();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("Received GET request at /skiers");

    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

    PrintWriter out = resp.getWriter();
    out.print("{\"message\":\"GET method is working!\"}");
    out.flush();
  }

  private boolean isValidLiftRide(LiftRide liftRide) {
    return liftRide.getSkierID() >= 1 && liftRide.getSkierID() <= 100000 &&
        liftRide.getResortID() >= 1 && liftRide.getResortID() <= 10 &&
        liftRide.getLiftID() >= 1 && liftRide.getLiftID() <= 40 &&
        liftRide.getSeasonID() == 2025 &&
        liftRide.getDayID() == 1 &&
        liftRide.getTime() >= 1 && liftRide.getTime() <= 360;
  }
}

class LiftRide {
  private int skierID;
  private int resortID;
  private int liftID;
  private int seasonID;
  private int dayID;
  private int time;

  public int getSkierID() { return skierID; }
  public int getResortID() { return resortID; }
  public int getLiftID() { return liftID; }
  public int getSeasonID() { return seasonID; }
  public int getDayID() { return dayID; }
  public int getTime() { return time; }
}
