package rs.ac.bg.etf.sn120348.bp2.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "room", schema = "bp2", catalog = "")
public class RoomEntity {
    private int id;
    private int apartmentId;
    private Integer number;
    private Integer capacity;
    private String description;
    private byte locked;
    private Collection<ReservationEntity> reservationsById;
    private ApartmentEntity apartmentByApartmentId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "apartment_id")
    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }

    @Basic
    @Column(name = "number")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Basic
    @Column(name = "capacity")
    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

        RoomEntity that = (RoomEntity) o;

        if (id != that.id) return false;
        if (apartmentId != that.apartmentId) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (capacity != null ? !capacity.equals(that.capacity) : that.capacity != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (locked != that.locked) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + apartmentId;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + locked;
        return result;
    }

    @OneToMany(mappedBy = "roomByRoomId")
    public Collection<ReservationEntity> getReservationsById() {
        return reservationsById;
    }

    public void setReservationsById(Collection<ReservationEntity> reservationsById) {
        this.reservationsById = reservationsById;
    }

    @ManyToOne
    @JoinColumn(name = "apartment_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public ApartmentEntity getApartmentByApartmentId() {
        return apartmentByApartmentId;
    }

    public void setApartmentByApartmentId(ApartmentEntity apartmentByApartmentId) {
        this.apartmentByApartmentId = apartmentByApartmentId;
    }

    public String extraFieldHTML() {
        if (getReservationsById() == null || getReservationsById().isEmpty()) {
            String lockLabel = locked > 0 ? "Otključaj" : "Zaključaj";
            return String.format("<a href=\"/seller/change_lock/%d\">%s</a>", this.id, lockLabel);
        }
        else {
            return String.format("<a href=\"/seller/view_reservation/%d\">Rezervacije</a>", this.id);
        }
    }
}
