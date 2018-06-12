package rs.ac.bg.etf.sn120348.bp2.entities;

import rs.ac.bg.etf.sn120348.bp2.login.UserForm;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "account_user", schema = "bp2", catalog = "")
public class AccountUserEntity {
    private int id;
    private String username;
    private String passHash;
    private int roleId;
    private String firstname;
    private String lastname;
    private String email;
    private String telephone;
    private String pos;
    private String cardNumber;
    private String cardType;
    private byte locked;
    private AccountRoleEntity accountRoleByRoleId;
    private Collection<ApartmentEntity> apartmentsById;
    private Collection<ReservationEntity> reservationsById;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "pass_hash")
    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    @Basic
    @Column(name = "role_id")
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "firstname")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "lastname")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "telephone")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "pos")
    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    @Basic
    @Column(name = "card_number")
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Basic
    @Column(name = "card_type")
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Basic
    @Column(name = "locked")
    public byte getLocked() {
        return locked;
    }

    public void setLocked(byte locked) {
        this.locked = locked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountUserEntity that = (AccountUserEntity) o;

        if (id != that.id) return false;
        if (roleId != that.roleId) return false;
        if (locked != that.locked) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (passHash != null ? !passHash.equals(that.passHash) : that.passHash != null) return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        if (pos != null ? !pos.equals(that.pos) : that.pos != null) return false;
        if (cardNumber != null ? !cardNumber.equals(that.cardNumber) : that.cardNumber != null) return false;
        if (cardType != null ? !cardType.equals(that.cardType) : that.cardType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (passHash != null ? passHash.hashCode() : 0);
        result = 31 * result + roleId;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (pos != null ? pos.hashCode() : 0);
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = 31 * result + (cardType != null ? cardType.hashCode() : 0);
        result = 31 * result + (int) locked;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public AccountRoleEntity getAccountRoleByRoleId() {
        return accountRoleByRoleId;
    }

    public void setAccountRoleByRoleId(AccountRoleEntity accountRoleByRoleId) {
        this.accountRoleByRoleId = accountRoleByRoleId;
    }

    @OneToMany(mappedBy = "accountUserByUserId")
    public Collection<ApartmentEntity> getApartmentsById() {
        return apartmentsById;
    }

    public void setApartmentsById(Collection<ApartmentEntity> apartmentsById) {
        this.apartmentsById = apartmentsById;
    }

    @OneToMany(mappedBy = "accountUserByUserId")
    public Collection<ReservationEntity> getReservationsById() {
        return reservationsById;
    }

    public void setReservationsById(Collection<ReservationEntity> reservationsById) {
        this.reservationsById = reservationsById;
    }

    public static AccountUserEntity convertUserFormToAccountUserEntity(UserForm form) {
        AccountUserEntity accountUserEntity = new AccountUserEntity();
        accountUserEntity.firstname = form.getFirstName();
        accountUserEntity.lastname = form.getLastName();
        accountUserEntity.username = form.getUsername();
        accountUserEntity.email = form.getEmail();
        accountUserEntity.passHash = form.getPassword();
        accountUserEntity.telephone = form.getTelephone();
        accountUserEntity.pos = form.getPos();
        accountUserEntity.cardNumber = form.getCardNumber();
        accountUserEntity.cardType = form.getCardType();
        return accountUserEntity;
    }
}
