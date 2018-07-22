import { ShopkeeperOrder } from "./shopkeeper-order";
import { Order } from "./order";

export class OrderGridRowdata {
    shopkeeperOrderDTO : ShopkeeperOrder;
    orderProductDTOS : Order[];
    billTotalPrice: number;
    billPaidPrice: number;
    billDuePrice: number;
}