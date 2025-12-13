package by.bsu.servlet;

import java.util.HashMap;
import java.util.Map;

import org.thymeleaf.web.IWebRequest;

import by.bsu.controller.*;


public class ControllerMappings {
 

    private static Map<String, IController> controllersByURL;


    static {
        controllersByURL = new HashMap<String, IController>();
        controllersByURL.put("/pages/accounts", new GetAccountsController());
        controllersByURL.put("/pages/sumPayments", new GetSumPayments());
       
    //    controllersByURL.put("/order/list", new OrderListController());
       
    }
    

    
    public static IController resolveControllerForRequest(final IWebRequest request) {
        final String path = request.getPathWithinApplication();
        System.out.println(path);
        return controllersByURL.get(path);
    }

    private ControllerMappings() {
        super();
    }


}
