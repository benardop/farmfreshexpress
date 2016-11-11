package FarmFresh.controllers;

import FarmFresh.business.*;
import FarmFresh.data.UserDB;
import FarmFresh.util.CookieUtil;
import FarmFresh.util.MailUtil;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Mom and Dad on 11/8/2016.
 */
public class OrderController extends HttpServlet {

    private static final String defaultURL = "/cart/cart.jsp";

    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        String url = "";
        if (requestURI.endsWith("/addItem")){
            url = addItem(request, response);
        }else if (requestURI.endsWith("/updateItem")) {
            url = updateItem(request, response);
        }else if (requestURI.endsWith("/removeItem")){
            url = removeItem(request, response);
        }else if (requestURI.endsWith("/checkUser")){
            url = checkUser(request, response);
        }else if (requestURI.endsWith("/processUser")){
            url = processUser(request, response);
        }else if (requestURI.endsWith("/displayInvoice")){
            url = displayInvoice(request, response);
        }else if (requestURI.endsWith("/displayUser")){
            url = "/cart/user.jsp";
        }else if (requestURI.endsWith("/displayCreditCard")){
            url = "/cart/creditCard.jsp";
        }else if (requestURI.endsWith("/completeOrder")){
            url = completeOrder(request, response);
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request,response);

    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        String url = defaultURL;

        if (requestURI.endsWith("/showCart")){
            url = showCart(request, response);
        }else if (requestURI.endsWith("/checkUser")){
            url = checkUser(request, response);
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);

    }

    private String addItem(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        Cart cart =(Cart)session.getAttribute("cart");

        if (cart == null)
            cart = new Cart();

        String productCode = request.getParameter("productCode");
//        Product product = ProductDB.selectProduct(productCode);
//
//        if (product != null){
//            LineItem lineItem = new LineItem();
//            lineItem.setProduct(product);
//            cart.addLineItem(lineItem);  //the quantity of the line item is not set at this time?  is it set to one?
//                                          // what if you are
//        }                                //adding another apple --- but you already have 3 apples in your cart?

        session.setAttribute("cart", cart);

        return defaultURL;  //defaultURL = "/cart/cart.jsp"
    }

    private String updateItem(HttpServletRequest request, HttpServletResponse response){

        String productCode = request.getParameter("productCode");
        String productQuantity = request.getParameter("productQuantity");

        HttpSession session = request.getSession();
        Cart cart =(Cart)session.getAttribute("cart");

        int cartQuantity;
        try{
            cartQuantity = Integer.parseInt(productQuantity);
            if (cartQuantity < 0)
                cartQuantity = 1;
        }catch (NumberFormatException ex){
            cartQuantity = 1;
        }

//        Product product = ProductDB.selectProduct(productCode);
//        if (product != null && cart != null){
//            LineItem lineItem = new LineItem();
//            lineItem.setProduct(product);
//            lineItem.setQuantity(cartQuantity);
//
//            if (cartQuantity > 0)
//                cart.addLineItem(lineItem);
//            else
//                cart.removeLineItem(lineItem);
//
//        }

        //BEN - shouldn't I push my updated cart to the session?
        //why is there not an else --- to complain about a product not existing or cart not existing?
        return defaultURL;  //defaultURL = "/cart/cart.jsp"
    }

    private String removeItem(HttpServletRequest request, HttpServletResponse response){

        HttpSession session = request.getSession();
        Cart cart =(Cart)session.getAttribute("cart");
        String productCode = request.getParameter("productCode");
//        Product product = ProductDB.selectProduct(productCode);
//        if (product != null && cart != null){
//                LineItem lineItem = new LineItem();
//                lineItem.setProduct(product);
//                cart.removeLineItem(lineItem);
//       }
        return defaultURL;  //defaultURL = "/cart/cart.jsp"
    }

    private String checkUser(HttpServletRequest request, HttpServletResponse response) {

        //Check to see if there is a user attached to the session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // if user exists and has a populated Address1 field - skip the user page
        String url = "/cart/user.jsp";
        if (user != null && !user.getAddress1().equals("")) {
            url = "/order/displayInvoice";
        } else {
            //Check for email Cookie - if the email cookie exists - pull the user from the DB
            Cookie[] cookies = request.getCookies();
            String email = CookieUtil.getCookieValue(cookies, "emailCookie"); //why not from params

            //if emailCookie does not exist - you need to get user to enter user information
            if (email.equals("")) {
                user = new User();
                url = "/cart/user.jsp";
            } else {
                user = UserDB.selectUser(email);
                //if user exists and user address1  is populated
                if (user != null && !user.getAddress1().equals("")) {
                    url = "/order/displayInvoice";
                }

            }
        }
        // Add the user to the session objects
        session.setAttribute("user", user);

        return defaultURL;  //defaultURL = "/cart/cart.jsp"
    }

    /**
     *
     * @param request
     * @param response
     * @return
     *
     */
    private String processUser(HttpServletRequest request, HttpServletResponse response){

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String companyName = request.getParameter("companyName");
        String address1 = request.getParameter("address1");
        String address2 = request.getParameter("address2");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zip = request.getParameter("zip");
        String country = request.getParameter("country");
        String creditCardType = request.getParameter("creditCardType");
        String creditCardNumber = request.getParameter("creditCardNumber");
        String creditCardExpirationDate = request.getParameter("creditCardExpirationDate");

        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        if(user == null){
            user = new User();
        }

//        if(UserDB.emailExists(email)) {
//            user = UserDB.selectUser(email);
//            user.setFirstName(firstName);
//            user.setLastName(lastName);
//            user.setEmail(email);
//            user.setCompanyName(companyName);
//            user.setAddress1(address1);
//            user.setAddress2(address2);
//            user.setCity(city);
//            user.setState(state);
//            user.setZip(zip);
//            user.setCountry(country);
//            user.setCreditCardType(creditCardType);
//            user.setCreditCardNumber(creditCardNumber);
//            user.setCreditCardExpirationDate(creditCardExpirationDate);
//            UserDB.update(user);
//        }else {
//            user.setFirstName(firstName);
//            user.setLastName(lastName);
//            user.setEmail(email);
//            user.setCompanyName(companyName);
//            user.setAddress1(address1);
//            user.setAddress2(address2);
//            user.setCity(city);
//            user.setState(state);
//            user.setZip(zip);
//            user.setCountry(country);
//            user.setCreditCardType(creditCardType);
//            user.setCreditCardNumber(creditCardNumber);
//            user.setCreditCardExpirationDate(creditCardExpirationDate);
//            UserDB.insert(user);
//        }

        session.setAttribute("user", user);

        return "/order/displayInvoice";
    }

    private String displayInvoice(HttpServletRequest request, HttpServletResponse response){

        //retrieve the user and cart from the session
        HttpSession session = request.getSession();
        User user =(User)session.getAttribute("user");
        Cart cart =(Cart)session.getAttribute("cart");

        //create date
        Date today = new Date();

        //create new invoice and attach user, cart and date to invoice
        Invoice invoice = new Invoice();
        invoice.setUser(user);
        invoice.setLineItemList(cart.getLineItems());
        invoice.setInvoiceDate(today);

        //attach invoice to session
        session.setAttribute("invoice", invoice);
        return "/cart/invoice.jsp";
    }

    //checking out
    private String completeOrder(HttpServletRequest request, HttpServletResponse response){
        // Get session obj and get invoice from session
        // Initialize the cardType, CardNum, CardExpMonth and Year using param
        // Create a user object and assign to invoice - call Invoice.getUser method.
        // Set the user creditcard info
        // if a record for the user exists based on email - update it
        // else write a new record for the user
        // set email cookie in the user's browser
        // set cart to null - we are done with it
        // send an email confirming the order

        HttpSession session = request.getSession();
        Invoice invoice =(Invoice)session.getAttribute("invoice");

        String creditCardType = request.getParameter("creditCardType");
        String creditCardNumber = request.getParameter("creditCardNumber");
        String creditCardExpMonth = request.getParameter("creditCardExpMonth");
        String creditCardExpYear = request.getParameter("creditCardExpYear");

        User user = invoice.getUser();
        user.setCreditCardType(creditCardType);
        user.setCreditCardNumber(creditCardNumber);
        user.setCreditCardExpirationDate(creditCardExpMonth + "/" + creditCardExpYear );

       // if the user exists, update it.
//        if(UserDB.emailExists(user.getEmail())) {
//            UserDB.update(user);
//        }else { // otherwise, write a new record for the user
//            UserDB.insert(user);
//        }

        invoice.setUser(user);

        // write a new invoice record to the DB
//        InvoiceDB.insert(invoice);

        //set the email cookie in the user's browser
        Cookie emailCookie = new Cookie("emailCookie", user.getEmail());
        emailCookie.setMaxAge(60 * 60 * 24 * 365 * 2 );  //TODO fix other setMax
        emailCookie.setPath("/");  // for the entire application

        response.addCookie(emailCookie);

        // remove all items from the user's cart
        session.setAttribute("cart", null);

        // send email to user to confirm the order
        String to = user.getEmail();
        String from = "FarmFreshExpress@gmail.com";
        String subject = "Farm Fresh Express Order ";  //TODO fix + invoiceNumber;
        String body = "Dear " + user.getFirstName() + "\n\n" +
                            "Thank you for shopping Farm Fresh Express.\n" +
                            "Your order should be arriving (2-3) days.";

        boolean isBodyHTML = false;

//        try{
//            MailUtil.sendMail(to, from, subject, body, isBodyHTML);
//        }catch (MessagingException e){
//            this.log("Unable to send email  \n" +
//            "Please check your system settings");
//        }

        return "/cart/complete.jsp";
    }

    private String showCart(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        Cart cart =(Cart)session.getAttribute("cart");

        if (cart == null || cart.getCount() == 0){
            request.setAttribute("emptyCart", "Your cart is empty.");
        }else {
            request.getSession().setAttribute("cart", cart);
        }
        return defaultURL;  //defaultURL = "/cart/cart.jsp"
    }


}