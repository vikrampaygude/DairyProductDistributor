import { ShopkeeperOrder } from "./shopkeeper-order";
import { Order } from "./order";
import { OrderGridRowdata } from "./order-grid-rowdata";
import { TotalGridCell } from "./grid-total-cell";

export class OrderGridData{

    date : string;
    dailySellRowDataDTOList : OrderGridRowdata[];
    dailySellGridTotalDTOS : TotalGridCell[];
    grandTotalAmount : number;
    grandTotalPaidAmount : number;
    grandTotalDueAmount : number;
    hasFutureOrder : boolean;

    constructor(){

    }
}