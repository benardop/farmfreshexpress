package farmfresh.business;

import java.util.Date;

/**
 * Created by Mom and Dad on 11/4/2016.
 */
public class User {

    // INSTANCE VARIABLES
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String companyName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String creditCardType;
    private String creditCardNumber;
    private String creditCardExpMonth;
    private String creditCardExpYear;
    private boolean subscribedToNewsletter;

    // GETTERS AND SETTERS
    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getAddress1() { return address1; }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }

    public String getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardExpMonth() {
        return creditCardExpMonth;
    }

    public void setCreditCardExpMonth(String creditCardExpMonth) {
        this.creditCardExpMonth = creditCardExpMonth;
    }

    public String getCreditCardExpYear() {
        return creditCardExpYear;
    }

    public void setCreditCardExpYear(String creditCardExpYear) {
        this.creditCardExpYear = creditCardExpYear;
    }

    public boolean isSubscribedToNewsletter() {
        return subscribedToNewsletter;
    }

    public void setSubscribedToNewsletter(boolean subscribedToNewsletter) {
        this.subscribedToNewsletter = subscribedToNewsletter;
    }

    //PUBLIC: SPECIALTY FUNCTIONALITY
    public String getShipToHTMLFormat(){
        String addressHTML = getFirstName() + " "
                            + getLastName() + "</br>"
                            + getAddress1() + "</br>";

        String address2 = getAddress2();
        if (address2 != null && !address2.equals("")) {
            addressHTML += address2 + "</br>" ;
        }

        addressHTML += getCity() + ", "
                    + getState() + "</br>"
                    + getZip();

        return addressHTML;
    }

}
