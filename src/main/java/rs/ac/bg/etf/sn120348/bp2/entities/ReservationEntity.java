package rs.ac.bg.etf.sn120348.bp2.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "reservation", schema = "bp2", catalog = "")
public class ReservationEntity {
    private int id;
    private int userId;
    private int roomId;
    private Timestamp startDate;
    private Timestamp endDate;
    private AccountUserEntity accountUserByUserId;
    private RoomEntity roomByRoomId;

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
    @Column(name = "room_id")
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Basic
    @Column(name = "start_date")
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date")
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReservationEntity that = (ReservationEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (roomId != that.roomId) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + roomId;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
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
    @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public RoomEntity getRoomByRoomId() {
        return roomByRoomId;
    }

    public void setRoomByRoomId(RoomEntity roomByRoomId) {
        this.roomByRoomId = roomByRoomId;
    }

    public String generateReservationTableRow() {
        return String.format("<td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%d</td><td>%s</td><td>%s</td>",
                accountUserByUserId.getFirstname(),
                accountUserByUserId.getLastname(),
                accountUserByUserId.getUsername(),
                accountUserByUserId.getEmail(),
                accountUserByUserId.getTelephone(),
                roomByRoomId.getNumber(),
                String.format("<a href=\"/seller/edit_apartment/%d\">%s</a>", roomByRoomId.getApartmentByApartmentId().getId(), roomByRoomId.getApartmentByApartmentId().getName()),
                roomByRoomId.getApartmentByApartmentId().printAddress());
    }

    public String generateTableRowForBuyer() {
        AccountUserEntity owner = roomByRoomId.getApartmentByApartmentId().getAccountUserByUserId();
        return String.format("<td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%d</td><td>%s</td><td>%s</td>",
                owner.getFirstname(),
                owner.getLastname(),
                owner.getUsername(),
                owner.getEmail(),
                owner.getTelephone(),
                roomByRoomId.getNumber(),
                String.format("<a href=\"/buyer/view_apartment/%d\">%s</a>", roomByRoomId.getApartmentByApartmentId().getId(), roomByRoomId.getApartmentByApartmentId().getName()),
                roomByRoomId.getApartmentByApartmentId().printAddress());
    }
}
