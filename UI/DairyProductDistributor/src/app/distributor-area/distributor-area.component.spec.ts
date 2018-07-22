import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DistributorAreaComponent } from './distributor-area.component';

describe('DistributorAreaComponent', () => {
  let component: DistributorAreaComponent;
  let fixture: ComponentFixture<DistributorAreaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DistributorAreaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DistributorAreaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
