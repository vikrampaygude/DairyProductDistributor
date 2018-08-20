import { ProductWeightPrice } from "../product-weight-price/product-weight-price";
import { DistributorArea } from "../distributor-area/distributor-area";
import { ProductAreaPrice } from "../order/product-area-price";

export class Product {

    constructor(
        public id : number,
        public name : string,
        public shortName: string,
        public brandId : string,
        public brandName : String,
        public unitOfMeasure: String,
        public sellingPrice : number ,
        public purchasePrice : number,
        public ProductWeightPriceList: ProductWeightPrice[],
        public distributorAreaDTOList: DistributorArea[],
        public productAreaPriceDTOList : ProductAreaPrice[],
        public uiSequence: number
      ) {  }
  
      public static getEmptyObject(): Product{
        return new Product(null,null,null,null,null,null,0,0,null,null,null,0);
      }
  
  }