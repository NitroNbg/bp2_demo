package rs.ac.bg.etf.sn120348.bp2.bookings;

import java.sql.Date;

public class SearchRoomForm {
    private int id;
    private java.sql.Date start_date;
    private java.sql.Date end_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
}
