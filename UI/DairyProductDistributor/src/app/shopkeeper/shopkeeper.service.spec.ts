import { TestBed, inject } from '@angular/core/testing';

import { ShopkeeperService } from './shopkeeper.service';

describe('ShopkeeperService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ShopkeeperService]
    });
  });

  it('should be created', inject([ShopkeeperService], (service: ShopkeeperService) => {
    expect(service).toBeTruthy();
  }));
});
