export class ShopkeeperOrder {

    constructor(
        public id : number,
        public date: Date,
        public status : string,
        public totalAmount : number ,
        public paidAmount : number,
        public dueAmount  : number,
        public shopkeeperId : number,
        public shopkeeperName : string
      ) {  }
  
      public static getEmptyObject(): ShopkeeperOrder{
        return new ShopkeeperOrder(0,null,null,0,0,0,0,null);
      }  
  }