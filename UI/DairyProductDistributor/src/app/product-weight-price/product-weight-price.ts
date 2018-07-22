export class ProductWeightPrice{
    
    constructor(
    public id : number,
    public sellingPrice: number,
    public purchasePrice: number,
    public quantity: number,
    public weight : number,
    public productId: number,
    public productName: string
    ){}
}