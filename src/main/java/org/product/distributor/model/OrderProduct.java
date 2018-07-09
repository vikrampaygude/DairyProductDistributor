package org.product.distributor.model;

import lombok.Data;

import javax.persistence.*;

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

    @Column
    private Double quantity;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopkeeper_order_id")
    private ShopkeeperOrder shopkeeperOrder;

    public static OrderProduct getObject(Product product, ShopkeeperOrder shopkeeperOrder){

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setShopkeeperOrder(shopkeeperOrder);

        return orderProduct;
    }

}
