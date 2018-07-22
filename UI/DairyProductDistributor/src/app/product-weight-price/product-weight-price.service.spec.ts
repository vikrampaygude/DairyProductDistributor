import { TestBed, inject } from '@angular/core/testing';

import { ProductWeightPriceService } from './product-weight-price.service';

describe('ProductWeightPriceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ProductWeightPriceService]
    });
  });

  it('should be created', inject([ProductWeightPriceService], (service: ProductWeightPriceService) => {
    expect(service).toBeTruthy();
  }));
});
