package lk.ijse.view.tdm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeTm {
    private String employeeId;
    private String employeeName;
    private String NIC;
    private String address;
    private String contact;
    private double salary;
    private String section;
}
