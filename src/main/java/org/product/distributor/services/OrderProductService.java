package org.product.distributor.services;

import org.product.distributor.dto.OrderProductDTO;
import org.product.distributor.mapper.OrderProductMapper;
import org.product.distributor.model.OrderProduct;
import org.product.distributor.repository.OrderProductRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikram on 05/07/18.
 *
 */
@Service
public class OrderProductService {

    private OrderProductRepo orderProductRepo;
    private OrderProductMapper orderProductMapper;

    public OrderProductService(OrderProductRepo orderProductRepo, OrderProductMapper orderProductMapper) {
        this.orderProductRepo = orderProductRepo;
        this.orderProductMapper = orderProductMapper;
    }

    public List<OrderProductDTO> getAll(){
        List<OrderProductDTO> orderProductDTOList = new ArrayList<>();
        orderProductRepo.findAll().forEach(d -> orderProductDTOList.add(orderProductMapper.getOrderProductDTO(d)));
        return orderProductDTOList;
    }

    public OrderProductDTO getById(Long id){
        return orderProductMapper.getOrderProductDTO(orderProductRepo.findById(id).get());
    }

    public OrderProduct saveOrderProduct(OrderProductDTO orderProductDTO){
        return orderProductRepo.save(orderProductMapper.getOrderProduct(orderProductDTO));
    }

    public OrderProduct updateOrderProduct(OrderProductDTO orderProductDTO){
        if(orderProductDTO.getId() == null || orderProductDTO.getId() ==0)
            throw new IllegalArgumentException();

        return orderProductRepo.save(orderProductMapper.getOrderProduct(orderProductDTO));
    }


}
