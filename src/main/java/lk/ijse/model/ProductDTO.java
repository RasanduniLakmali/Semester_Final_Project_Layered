package lk.ijse.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private String productId;
    private String productName;
    private double unitPrice;
    private int qtyOnHand;

}
