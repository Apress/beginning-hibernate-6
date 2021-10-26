package chapter11.servlets;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

abstract class ServletBase extends HttpServlet {
  protected ObjectMapper mapper = new ObjectMapper()
    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  {
    mapper.registerModule(new JavaTimeModule());
  }

  /* simple validation of parameters */
  protected Map<String, String> getValidatedParameters(
    HttpServletRequest req,
    String... fields
  ) {
    Map<String, String> map = new HashMap<>();
    List<String> badFields = new ArrayList<>();
    for (String fieldName : fields) {
      String value = req.getParameter(fieldName);
      if (value == null || value.isEmpty()) {
        badFields.add(fieldName);
      } else {
        map.put(fieldName, value);
      }
    }
    if (badFields.size() > 0) {
      throw new RuntimeException(
        "bad fields provided: " + badFields
      );
    }
    return map;
  }

  /* write out a valid response */
  protected void write(
    HttpServletResponse r,
    int code,
    Object entity
  ) throws IOException {
    r.setContentType("application/json");
    r.setStatus(code);
    r.getWriter().write(mapper
      .writerWithDefaultPrettyPrinter()
      .writeValueAsString(entity)
    );
  }

  /* write out an exception */
  protected final void writeError(
    HttpServletResponse resp,
    Throwable throwable
  ) throws IOException {
    write(resp,
      HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
      Map.of("error", throwable.getMessage())
    );
  }
}
