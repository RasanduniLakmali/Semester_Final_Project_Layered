package lk.ijse.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Supplier {
    private String supplierId;
    private String supplierName;
    private String address;
    private String contact;
}
