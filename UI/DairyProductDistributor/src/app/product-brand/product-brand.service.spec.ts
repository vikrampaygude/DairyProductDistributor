import { TestBed, inject } from '@angular/core/testing';

import { ProductBrandService } from './product-brand.service';

describe('ProductBrandService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ProductBrandService]
    });
  });

  it('should be created', inject([ProductBrandService], (service: ProductBrandService) => {
    expect(service).toBeTruthy();
  }));
});
