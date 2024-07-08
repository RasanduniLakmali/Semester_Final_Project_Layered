package lk.ijse.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SupplierDTO {
    private String supplierId;
    private String supplierName;
    private String address;
    private String contact;
}
