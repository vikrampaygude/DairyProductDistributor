package org.product.distributor.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

/**
 * Created by vikram on 05/07/18.
 *
 */
@Entity
@Data
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Product product;

    @Column
    private Double sellingPrice;

    @Column(nullable = false)
    private Double quantity;

    @OneToOne(fetch = FetchType.EAGER)
    private ProductWeightPrice productWeightPrice;

    @OneToOne
    private ShopkeeperCustomPrice shopkeeperCustomPrice;

    @ManyToOne
    @JoinColumn(name = "shopkeeper_order_id")
    private ShopkeeperOrder shopkeeperOrder;

    public static OrderProduct getObject(Product product, ShopkeeperOrder shopkeeperOrder){

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setSellingPrice(product.getSellingPrice());
        orderProduct.setQuantity(0.0);
        orderProduct.setShopkeeperOrder(shopkeeperOrder);

        return orderProduct;
    }

}
