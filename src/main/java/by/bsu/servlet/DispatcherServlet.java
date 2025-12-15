package by.bsu.servlet;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import by.bsu.controller.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebApplication;
import org.thymeleaf.web.IWebRequest;
import org.thymeleaf.web.servlet.IServletWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Date;

@WebServlet(urlPatterns = "/pages/*", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final long serialVersionUID = 1L;
	private JakartaServletWebApplication application;
	private ITemplateEngine templateEngine;
	
	
	
	
	@Override
	public void init(){
		this.application =
		JakartaServletWebApplication.buildApplication(getServletContext());
		
		this.templateEngine = buildTemplateEngine(this.application);
		
	}
	
	private Cookie GetCookie(String name, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
			Cookie myCookie = null;
			
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (name.equals(cookie.getName())) {
						myCookie = cookie;
						break;
					}
				}
			}

			return myCookie;
	}
	
	
	private ITemplateEngine buildTemplateEngine(final IWebApplication application) {
		final WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);
		
		// HTML is the default mode, but we will set it anyway for better understanding of code
		templateResolver.setTemplateMode(TemplateMode.HTML);
		// This will convert "home" to "/WEB-INF/templates/home.html"
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		// Set template cache TTL to 1 hour. If not set, entries would live in cache until expelled by LRU
		templateResolver.setCacheTTLMs(Long.valueOf(3600000L));
		
		// Cache is set to true by default. Set to false if you want templates to
		// be automatically updated when modified.
		templateResolver.setCacheable(true);
		
		final TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		
		return templateEngine;
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException{
		doGet(request,response);
	}
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws  ServletException{
		try {
			
			request.getSession().setAttribute("calend", 
			Calendar.getInstance());
			
			HttpSession session = request.getSession(true);
			LOGGER.info("user {} wants to do request" ,session.getId());
			
			Cookie visitsCookie = GetCookie("Visits", request);
			
			int currentValue = 0;
			if (visitsCookie != null) {
				currentValue = Integer.parseInt(visitsCookie.getValue());
			}

			LOGGER.info("user visits {}", currentValue);
			Cookie newCookie = new Cookie("Visits", String.valueOf(currentValue + 1));
			response.addCookie(newCookie);

			
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
			Date currentDate = new Date();
			LOGGER.info("user visits {}", currentDate);
			Cookie dateCookie = new Cookie("VisitTime", formatter.format(currentDate));
			response.addCookie(dateCookie);

			
			final IServletWebExchange webExchange = this.application.buildExchange(request, response);
			final IWebRequest webRequest = webExchange.getRequest();
			final Writer writer = response.getWriter();
			
			IController controller = ControllerMappings.resolveControllerForRequest(webRequest);
			
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			
			controller.process(webExchange, templateEngine, writer);
			
			
		} catch (Exception e) {
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} catch (final IOException ignored) {
				// Just ignore this
			}
			throw new ServletException(e);
		}
	}
	
	
}
