package rs.ac.bg.etf.sn120348.bp2.bookings;

import org.hibernate.validator.constraints.NotEmpty;

public class ApartmentEditForm {

    private int id;

    @NotEmpty(message = "Obavezno polje")
    private String name;

    @NotEmpty(message = "Obavezno polje")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
