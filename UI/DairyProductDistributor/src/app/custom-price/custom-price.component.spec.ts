import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomPriceComponent } from './custom-price.component';

describe('CustomPriceComponent', () => {
  let component: CustomPriceComponent;
  let fixture: ComponentFixture<CustomPriceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomPriceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomPriceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
