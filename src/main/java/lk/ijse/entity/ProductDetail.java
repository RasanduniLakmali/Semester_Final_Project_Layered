package lk.ijse.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDetail {
    private String productId;
    private String materialId;


    public ProductDetail(String productId) {
    }
}
