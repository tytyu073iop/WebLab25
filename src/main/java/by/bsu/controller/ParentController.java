package by.bsu.controller;

import java.io.Writer;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.IWebRequest;

public class ParentController implements IController {
    private static final Logger LOGGER = LogManager.getLogger();
    protected List<String> arguments;
    protected String template;

    ParentController(List<String> arguments, String template) {
        this.arguments = arguments;
        this.template = template;
    }

    @Override
    public void process(IWebExchange webExchange, ITemplateEngine templateEngine, Writer writer) throws Exception {
        WebContext ctx = new WebContext(webExchange, webExchange.getLocale());
        IWebRequest wr = ctx.getExchange().getRequest();
        if (wr.containsParameter(arguments.getFirst())) {
            fulfillRecuest(ctx);
        } else {
            LOGGER.info("Get request sends website");
        }

        templateEngine.process(template, ctx, writer);
    }

    protected void fulfillRecuest(WebContext ctx) {
        LOGGER.error("No realization");
    }
    
}
