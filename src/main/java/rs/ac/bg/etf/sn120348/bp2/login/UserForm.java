package rs.ac.bg.etf.sn120348.bp2.login;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserForm {

    @NotEmpty(message = "Obavezno polje")
    private String firstName;

    @NotEmpty(message = "Obavezno polje")
    private String lastName;

    @NotEmpty(message = "Obavezno polje")
    private String username;

    @NotEmpty(message = "Obavezno polje")
    private String password;

    @NotEmpty(message = "Obavezno polje")
    @Email(message = "Mora biti ispravan email")
    private String email;

    @NotEmpty(message = "Obavezno polje")
    private String telephone;

    private int roleId;

    private String pos;

    private String cardNumber;

    private String cardType;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
