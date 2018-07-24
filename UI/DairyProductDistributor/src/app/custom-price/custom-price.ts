export class CustomPrice {

  public id: number;
  public shopkeeperOrderId: number; 
  public productId : number;
  public price : number;
  public shopkeeperName: string;
  public shopkeeperAreaName: string;
  public productName: string;
  public areaId: number;
  public orderProductId: number;
  public productWeightPriceId: number;

    constructor(
      ) {  }


    public static getEmptyObject(): CustomPrice{

        return new CustomPrice();
    }
  
  }