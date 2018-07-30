import { Component, OnInit } from '@angular/core';
import {Product} from './product';
import {ProductService} from './product.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ProductBrand } from '../product-brand/product-brand';
import { ProductBrandService } from '../product-brand/product-brand.service';
import { DistributorArea } from '../distributor-area/distributor-area';
import { DistributorAreaService } from '../distributor-area/distributor-area.service';
import { NotificationService } from '../notification/notification.service';

@Component({
  selector: 'app-product-dialog',
  templateUrl: './product-dialog.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductDialogComponent implements OnInit {
  productBrands : ProductBrand[];
  model = Product.getEmptyObject();
  distributorAreaList : DistributorArea[];
  distributorAreaCheckList = new Array();



  submitted = false;

  onSubmit() { 
    this.submitted = true;
    this.model.distributorAreaDTOList = [];
      
    this.distributorAreaCheckList.forEach(distributorCheck => {
      if(distributorCheck.checked)
        this.distributorAreaCheckList.forEach(area =>{
            if(distributorCheck.id == area.id)
              this.model.distributorAreaDTOList.push(area);
        })
    })
    this.service.save(this.model).subscribe(val => {
      this.router.navigate(['/products']);
      this.notification.notifySuccess("Product added/updated successfully !")
    });
    
  }

  private markCheckedDistributorArea(){
    this.distributorAreaCheckList.forEach(distAreaCheck =>
    {
      this.model.distributorAreaDTOList && this.model.distributorAreaDTOList.forEach(distAreaM => {
        if((distAreaM.id == distAreaCheck.id)){
          distAreaCheck.checked = true;
        }
        
      })
    });
  }
  constructor(public service : ProductService, public router : Router, private route: ActivatedRoute,
    public productBrandService : ProductBrandService,
    public areaService: DistributorAreaService,
    public notification: NotificationService) { 
      if(this.route.snapshot.params.id){
        service.getById(this.route.snapshot.params.id).subscribe(obj => {
          this.model = obj;
          this.populateDistAreaCheckList();
        });
      }else{
        this.populateDistAreaCheckList();
      }

    this.productBrandService.getAllProductBrands().subscribe(productBrands => this.productBrands = productBrands );

  }

  private  populateDistAreaCheckList(){
    this.areaService.getAllDistributorAreas().subscribe(
      areaList=> 
      { 
        this.distributorAreaList = areaList;
        areaList.forEach(distributorArea => {
          this.distributorAreaCheckList.push({"id" : distributorArea.id,"name": distributorArea.name, checked:false });
        });
        this.markCheckedDistributorArea();
      });
  }

  ngOnInit() { }

}