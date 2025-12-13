package by.bsu.controller;

import java.io.Writer;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.IWebRequest;

import by.bsu.daoPhysical.DaoPayments;
import by.bsu.servlet.helpers;

public class GetSumPayments implements IController {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void process(IWebExchange webExchange, ITemplateEngine templateEngine, Writer writer) throws Exception {
        WebContext ctx = new WebContext(webExchange, webExchange.getLocale());
        IWebRequest wr = ctx.getExchange().getRequest();
        final String clientVarName = "ClientId";
        if (wr.containsParameter(clientVarName)) {
            int ClientId = Integer.parseInt(wr.getParameterValue(clientVarName));
            Date from = helpers.convertToSqlDate(wr.getParameterValue("dateFrom"));
            Date to = helpers.convertToSqlDate(wr.getParameterValue("dateTo"));
            
            DaoPayments dp = new DaoPayments();
            Double result = dp.getClientPayments(ClientId, from, to);

            ctx.setVariable("sum", result);
        } else {
            LOGGER.info("Get request sends website");
        }

        templateEngine.process("sumPayments", ctx, writer);
    }
    
}
