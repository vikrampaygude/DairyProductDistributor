import { ShopkeeperOrder } from "./shopkeeper-order";
import { Order } from "./order";
import { OrderGridRowdata } from "./order-grid-rowdata";
import { TotalGridCell } from "./grid-total-cell";
import { Product } from "../product/product";

export class OrderGridData{

    date : string;
    productDTOS : Product[];
    dailySellRowDataDTOList : OrderGridRowdata[];
    dailySellGridTotalDTOS : TotalGridCell[];
    grandTotalAmount : number;
    grandTotalPaidAmount : number;
    grandTotalDueAmount : number;
    hasFutureOrder : boolean;

    constructor(){

    }
}