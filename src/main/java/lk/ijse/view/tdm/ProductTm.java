package lk.ijse.view.tdm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductTm {
    private String productId;
    private String productName;
    private double unitPrice;
    private int qtyOnHand;
}
