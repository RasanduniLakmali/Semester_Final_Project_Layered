package lk.ijse.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehicleDTO {
    private String vehicleId;
    private String employeeId;
    private String vehicleNumber;
    private String model;
}
