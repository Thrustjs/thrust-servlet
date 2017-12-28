package br.com.softbox.thrust.servlet;

import java.io.IOException;

import javax.script.ScriptException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.softbox.thrust.core.ThrustCore;

@WebServlet(
        name="thrust",
        loadOnStartup = 1,
        urlPatterns = "/thrust/*",
        initParams = {
            @WebInitParam(name = "configFile", value = "config.json")
        }
)
@MultipartConfig(fileSizeThreshold=1024*1024*1, // 1MB
                 maxFileSize=1024*1024*1020,    // 1GB
                 maxRequestSize=-1L)            // unlimited
public class ThrustServlet extends HttpServlet {
    private static final long serialVersionUID = -1L;
    ThrustCore thrust;

    public ThrustServlet() throws IOException, ScriptException {
        thrust = new ThrustCore();
	}

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            thrust.invokeFunction("http.service", request, response);
		} catch(NoSuchMethodException e) {
			System.out.println("[ERROR] NoSuchMethodException occurred on 'http.service' function call.");
			e.printStackTrace();
		} catch(ScriptException se) {
			System.out.println("[ERROR] ScriptException occurred on 'http.service' function call.");
			se.printStackTrace();
		}            
    }

}
