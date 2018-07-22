export class Order {

    constructor(
        public id : number,
        public productId: number,
        public productName : string,
        public productBrandName: string,
        public sellingPrice : number ,
        public quantity : number,
        public unitOfMeasure: number,
        public orderId  : number,
        public customSellingPrice : number,
        public productWeightPriceId: number,
        public productWeight: number,
        public customPriceId: number,
        public byWeightOrders: Order[],
      ) {  }
  
      public static getEmptyObject(): Order{
        return new Order(0,0,null,null,null,0,0,0,0,0,0,null,null);
      }  
  }