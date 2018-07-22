export class ProductBrand {

    constructor(
      public id: number,
      public name: string, 
      public shortName: string,
      public brandName: string
      ) {  }
  
      public static getEmptyObject() : ProductBrand {
        return new ProductBrand(0,null,null,null);
      }
  }