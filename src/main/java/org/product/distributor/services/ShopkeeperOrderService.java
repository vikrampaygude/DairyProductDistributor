package org.product.distributor.services;

import org.product.distributor.dto.ShopkeeperOrderDTO;
import org.product.distributor.mapper.ShopkeeperOrderMapper;
import org.product.distributor.model.OrderProduct;
import org.product.distributor.model.ShopkeeperOrder;
import org.product.distributor.repository.OrderProductRepo;
import org.product.distributor.repository.ShopkeeperOrderRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by vikram on 05/07/18.
 *
 */
@Service
@Transactional
public class ShopkeeperOrderService {


    private ShopkeeperOrderRepo shopkeeperOrderRepo;
    private ShopkeeperOrderMapper shopkeeperOrderMapper;
    private OrderProductRepo orderProductRepo;
    private ShopkeeperBillService shopkeeperBillService;


    public ShopkeeperOrderService(ShopkeeperOrderRepo shopkeeperOrderRepo, ShopkeeperOrderMapper shopkeeperOrderMapper, OrderProductRepo orderProductRepo, ShopkeeperBillService shopkeeperBillService) {
        this.shopkeeperOrderRepo = shopkeeperOrderRepo;
        this.shopkeeperOrderMapper = shopkeeperOrderMapper;
        this.orderProductRepo = orderProductRepo;
        this.shopkeeperBillService = shopkeeperBillService;
    }

    public List<ShopkeeperOrderDTO> getAll(){
        List<ShopkeeperOrderDTO> shopkeeperOrderDTOList = new ArrayList<>();
        shopkeeperOrderRepo.findAll().forEach(d -> shopkeeperOrderDTOList.add(shopkeeperOrderMapper.getShopkeeperOrderDTO(d)));
        return shopkeeperOrderDTOList;
    }

    public ShopkeeperOrderDTO getById(Long id){
        return shopkeeperOrderMapper.getShopkeeperOrderDTO(shopkeeperOrderRepo.findById(id).get());
    }

    public ShopkeeperOrder saveShopkeeperOrder(ShopkeeperOrderDTO shopkeeperOrderDTO){
        return shopkeeperOrderRepo.save(shopkeeperOrderMapper.getShopkeeperOrder(shopkeeperOrderDTO));
    }

    public ShopkeeperOrder updateShopkeeperOrder(ShopkeeperOrderDTO shopkeeperOrderDTO){
        if(shopkeeperOrderDTO.getId() == null || shopkeeperOrderDTO.getId() ==0)
            throw new IllegalArgumentException();

        return shopkeeperOrderRepo.save(shopkeeperOrderMapper.getShopkeeperOrder(shopkeeperOrderDTO));
    }

    public ShopkeeperOrderDTO calculateAndSaveOrderBill(Long id){

        Optional<ShopkeeperOrder> shopkeeperOrderOptional = shopkeeperOrderRepo.findById(id);
        if(shopkeeperOrderOptional.isPresent() == false)
            return null;

        ShopkeeperOrder shopkeeperOrder = shopkeeperOrderOptional.get();

        List<OrderProduct> orderProductList =  orderProductRepo.findByShopkeeperOrderId(id);

        Double total = 0.0;
        Double due   = 0.0;

        //we need to calculate total bill, paid bill and due bill
        for(OrderProduct orderProduct : orderProductList){
            total = total + (orderProduct.getQuantity() * orderProduct.getSellingPrice());
        }

        shopkeeperOrder.setTotalAmount(total);
        shopkeeperOrder.setDueAmount(total - (shopkeeperOrder.getPaidAmount()==null?0.0: shopkeeperOrder.getPaidAmount()) );

        shopkeeperOrderRepo.save(shopkeeperOrder);

        // update bill
        shopkeeperBillService.saveShopkeeperBill(shopkeeperOrder.getDate(), shopkeeperOrder.getShopkeeper().getId(), shopkeeperOrder.getTotalAmount(), shopkeeperOrder.getPaidAmount());

        return shopkeeperOrderMapper.getShopkeeperOrderDTO(shopkeeperOrder);
    }

    /**
     * TODO: VALIDATION needs to be done
     */

    public ShopkeeperOrderDTO updatePaidPrice(ShopkeeperOrderDTO shopkeeperOrderDTO) {
        ShopkeeperOrder shopkeeperOrder = null;
        Optional<ShopkeeperOrder> shopkeeperOrderOptional = shopkeeperOrderRepo.findById(shopkeeperOrderDTO.getId());
        if(shopkeeperOrderOptional.isPresent()){
            shopkeeperOrder = shopkeeperOrderOptional.get();
        }else {
            throw new InvalidParameterException("Invalid input provided to update paid amount. No value present in data for given order");
        }
        shopkeeperOrder.setPaidAmount(shopkeeperOrderDTO.getPaidAmount());
        shopkeeperOrder.setDueAmount(shopkeeperOrderDTO.getTotalAmount() - shopkeeperOrderDTO.getPaidAmount() ); //all validation needs to be performed here...

        shopkeeperOrder = shopkeeperOrderRepo.save(shopkeeperOrder);

        // update bill
        shopkeeperBillService.saveShopkeeperBill(shopkeeperOrder.getDate(), shopkeeperOrder.getShopkeeper().getId(), shopkeeperOrder.getTotalAmount(), shopkeeperOrder.getPaidAmount());

        return shopkeeperOrderMapper.getShopkeeperOrderDTO(shopkeeperOrder);
    }

    public ShopkeeperOrder getShopkeeperOrder(LocalDate date, Long shopkeeperId){
        return shopkeeperOrderRepo.findOneByShopkeeperIdAndDate(date, shopkeeperId).get();
    }

    public void deleteById(Long shopkeeperOrderId){
        shopkeeperOrderRepo.deleteById(shopkeeperOrderId);
    }
}
