import { TestBed, inject } from '@angular/core/testing';

import { CustomPriceService } from './custom-price.service';

describe('CustomPriceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CustomPriceService]
    });
  });

  it('should be created', inject([CustomPriceService], (service: CustomPriceService) => {
    expect(service).toBeTruthy();
  }));
});
