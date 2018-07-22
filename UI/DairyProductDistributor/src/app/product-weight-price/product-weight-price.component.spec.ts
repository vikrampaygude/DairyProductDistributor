import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductWeightPriceComponent } from './product-weight-price.component';

describe('ProductWeightPriceComponent', () => {
  let component: ProductWeightPriceComponent;
  let fixture: ComponentFixture<ProductWeightPriceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductWeightPriceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductWeightPriceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
