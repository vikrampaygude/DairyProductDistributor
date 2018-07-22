import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShopkeeperComponent } from './shopkeeper.component';

describe('SphopkeeperComponent', () => {
  let component: ShopkeeperComponent;
  let fixture: ComponentFixture<ShopkeeperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShopkeeperComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShopkeeperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
