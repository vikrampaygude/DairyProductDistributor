package org.product.distributor.services;

import org.apache.tomcat.jni.Local;
import org.product.distributor.dto.ShopkeeperBillDTO;
import org.product.distributor.model.Shopkeeper;
import org.product.distributor.model.ShopkeeperBill;
import org.product.distributor.model.ShopkeeperBillMapper;
import org.product.distributor.model.ShopkeeperOrder;
import org.product.distributor.repository.ShopkeeperBillRepo;
import org.product.distributor.repository.ShopkeeperOrderRepo;
import org.product.distributor.repository.ShopkeeperRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by vikram on 17/07/18.
 */
@Service
public class ShopkeeperBillService {

    private ShopkeeperBillRepo shopkeeperBillRepo;
    private ShopkeeperBillMapper shopkeeperBillMapper;
    private ShopkeeperRepo shopkeeperRepo;

    @Autowired
    private ShopkeeperOrderRepo shopkeeperOrderRepo;

    public ShopkeeperBillService(ShopkeeperBillRepo shopkeeperBillRepo, ShopkeeperBillMapper shopkeeperBillMapper, ShopkeeperRepo shopkeeperRepo) {
        this.shopkeeperBillRepo = shopkeeperBillRepo;
        this.shopkeeperBillMapper = shopkeeperBillMapper;
        this.shopkeeperRepo = shopkeeperRepo;
    }

    private void updateGrandTotal(){

    }

    public void saveShopkeeperBill(LocalDate date, Long shopkeeperId, Double totalAmount, Double totalPaid){

        Optional<ShopkeeperBill> prevBillOp = shopkeeperBillRepo.findByDateAndShopkeeper(date.minusDays(1), shopkeeperId);
        Optional<ShopkeeperBill> currentBillOp = shopkeeperBillRepo.findByDateAndShopkeeper(date, shopkeeperId);

        Shopkeeper shopkeeper = shopkeeperRepo.findById(shopkeeperId).get();

        if(prevBillOp.isPresent() == false && currentBillOp.isPresent() ==false){
            //simply save new bill
            ShopkeeperBill bill = new ShopkeeperBill();
            bill.setDate(date);
            bill.setGrandTotal(totalAmount);
            bill.setGrandPaid(totalPaid);
            bill.setGrandDue( totalAmount - totalPaid);
            bill.setShopkeeper(shopkeeper);


            shopkeeperBillRepo.save(bill);
        }
        else if(prevBillOp.isPresent() == false && currentBillOp.isPresent()){
            ShopkeeperBill bill = currentBillOp.get();
            bill.setGrandPaid(totalPaid);
            bill.setGrandTotal(totalAmount);
            bill.setGrandDue(totalAmount - totalPaid);
            bill.setShopkeeper(shopkeeper);

            shopkeeperBillRepo.save(bill);
        }else if(prevBillOp.isPresent()){
            ShopkeeperBill prevBill = prevBillOp.get();
            ShopkeeperBill currentBill = null;

            if(currentBillOp.isPresent())
                currentBill = currentBillOp.get();
            else {
                currentBill = new ShopkeeperBill();
                currentBill.setDate(date);
            }

            Double prevDue = prevBill.getGrandDue();

            currentBill.setGrandTotal( totalAmount + prevDue );
            currentBill.setGrandPaid(totalPaid);
            currentBill.setGrandDue(currentBill.getGrandTotal() - totalPaid);
            currentBill.setShopkeeper(shopkeeper);

            shopkeeperBillRepo.save(currentBill);
        }

        updateNextDayBill(shopkeeperBillRepo.findByDateAndShopkeeper(date, shopkeeperId).get());//this should always be present


    }


    private void updateNextDayBill(ShopkeeperBill currentBill){
        Optional<ShopkeeperBill> shopkeeperBillOptional = shopkeeperBillRepo.findByDateAndShopkeeper(currentBill.getDate().plusDays(1), currentBill.getShopkeeper().getId());

        shopkeeperBillOptional.ifPresent(nextShopkeeperBill -> {

            ShopkeeperOrder nextShopkeeperOrder = shopkeeperOrderRepo.findOneByShopkeeperIdAndDate(currentBill.getDate().plusDays(1), currentBill.getShopkeeper().getId()).get();

            Double due = currentBill.getGrandDue();

            nextShopkeeperBill.setGrandTotal(nextShopkeeperOrder.getTotalAmount() + due);
            nextShopkeeperBill.setGrandDue(nextShopkeeperBill.getGrandTotal() - nextShopkeeperBill.getGrandPaid());

            shopkeeperBillRepo.save(nextShopkeeperBill);
        });
    }

    public ShopkeeperBillDTO getByShopkeeperAndDate(LocalDate date, Long shopkeeperId){
        Optional<ShopkeeperBill> bill = shopkeeperBillRepo.findByDateAndShopkeeper(date, shopkeeperId);

        if(bill.isPresent())
            return shopkeeperBillMapper.map(bill.get());

        return null;
    }

    public void deleteByDateAndShopkeeperId(LocalDate date, Long shopkeeperId) {
        shopkeeperBillRepo.delete(date, shopkeeperId);
    }
}
