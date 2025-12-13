package by.bsu.controller;

import java.io.Writer;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.IWebRequest;

public class GetAccountBalanceController implements IController {

    @Override
    public void process(IWebExchange webExchange, ITemplateEngine templateEngine, Writer writer) throws Exception {
        WebContext ctx = new WebContext(webExchange, webExchange.getLocale());
        IWebRequest wr = ctx.getExchange().getRequest();
        
    }
    
}
