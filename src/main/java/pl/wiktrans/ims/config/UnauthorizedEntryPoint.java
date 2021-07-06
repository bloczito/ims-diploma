package pl.wiktrans.ims.config;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest req,
                         HttpServletResponse res,
                         AuthenticationException exception) throws IOException, ServletException {

//        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        res.setContentType(MediaType.APPLICATION_JSON_VALUE);

        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());

    }
}
