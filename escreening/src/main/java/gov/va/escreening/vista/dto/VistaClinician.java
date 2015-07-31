package gov.va.escreening.vista.dto;

/**
 * Created by pouncilt on 4/11/14.
 */
public class VistaClinician {
    private Long ien;
    private String firstName;
    private String lastName;
    private String title;

    public VistaClinician(Long ien, String firstName, String lastName,  String title) {
        this.ien = ien;
        this.lastName = lastName;
        this.firstName = firstName;
        this.title = title;
    }

    public Long getIEN() {
        return ien;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTitle() {
        return title;
    }
}
