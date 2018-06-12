package rs.ac.bg.etf.sn120348.bp2.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "apartment", schema = "bp2", catalog = "")
public class ApartmentEntity {
    private int id;
    private int userId;
    private int addressId;
    private String name;
    private String description;
    private AccountUserEntity accountUserByUserId;
    private AddressEntity addressByAddressId;
    private Collection<RoomEntity> roomsById;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "address_id")
    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApartmentEntity that = (ApartmentEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (addressId != that.addressId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + addressId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public AccountUserEntity getAccountUserByUserId() {
        return accountUserByUserId;
    }

    public void setAccountUserByUserId(AccountUserEntity accountUserByUserId) {
        this.accountUserByUserId = accountUserByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public AddressEntity getAddressByAddressId() {
        return addressByAddressId;
    }

    public void setAddressByAddressId(AddressEntity addressByAddressId) {
        this.addressByAddressId = addressByAddressId;
    }

    @OneToMany(mappedBy = "apartmentByApartmentId")
    public Collection<RoomEntity> getRoomsById() {
        return roomsById;
    }

    public void setRoomsById(Collection<RoomEntity> roomsById) {
        this.roomsById = roomsById;
    }

    public String printAddress() {
        AddressEntity address = getAddressByAddressId();
        return String.format("%s, %d, %s, %s", address.getStreet(), address.getNumber(), address.getCity(), address.getCountry());
    }
}
