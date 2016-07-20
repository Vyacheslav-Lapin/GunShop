package listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionsListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().getServletContext().log("Session created! - " + se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        se.getSession().getServletContext().log("Session destroyed! - " + se.getSession().getId());
    }
}
