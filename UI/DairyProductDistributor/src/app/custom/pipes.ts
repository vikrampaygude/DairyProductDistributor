import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'productByBrand'
})
export class ProductByBrandFilterPipe implements PipeTransform {
  transform(items: any[], term): any {
      console.log('term', term +" "+(term && term !=0));
      return term && term !=0 
          ? items.filter(item => item.brandId == term)
          : items;
  }
}