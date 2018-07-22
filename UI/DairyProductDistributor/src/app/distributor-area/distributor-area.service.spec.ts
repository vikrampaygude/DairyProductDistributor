import { TestBed, inject } from '@angular/core/testing';

import { DistributorAreaService } from './distributor-area.service';

describe('DistributorAreaService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DistributorAreaService]
    });
  });

  it('should be created', inject([DistributorAreaService], (service: DistributorAreaService) => {
    expect(service).toBeTruthy();
  }));
});
