package by.bsu.controller;

import java.io.Writer;
import java.net.http.HttpRequest;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.IServletWebExchange;

import jakarta.servlet.http.HttpServletRequest;

public class GetAccountsController implements IController {

    @Override
    public void process(IWebExchange webExchange, ITemplateEngine templateEngine, Writer writer) throws Exception {
        WebContext ctx = new WebContext(webExchange, webExchange.getLocale());
        System.out.println(ctx.getExchange().getRequest().getParameterMap());
    }
    
}
