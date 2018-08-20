export class ProductAreaPrice{
    constructor(public id: number,
        public productId: number,
        public areaId: number,
        public price: number,
        public productName: string,
        public areaName: string,
        public date: string){
    }

    static getInstance(): ProductAreaPrice{
        return new ProductAreaPrice(null, null, null, null, null, null, null);
    }
}