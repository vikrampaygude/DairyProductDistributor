import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DistributorComponent } from './distributor/distributor.component';
import { DistributorDialogComponent } from './distributor/distributor-dialog.component';
import { DistributorAreaComponent } from './distributor-area/distributor-area.component';
import { DistributorAreaDialogComponent } from './distributor-area/distributor-area-dialog.component';
import { ProductBrandComponent } from './product-brand/product-brand.component';
import { ProductBrandDialogComponent } from './product-brand/product-brand-dialog.component';
import { ProductComponent } from './product/product.component';
import { ProductDialogComponent } from './product/product-dialog.component';
import { ShopkeeperComponent } from './shopkeeper/shopkeeper.component';
import { ShopkeeperDialogComponent } from './shopkeeper/shopkeeper-dialog.component';
import { OrderComponent } from './order/order.component';
import { ProductWeightPriceComponent } from './product-weight-price/product-weight-price.component';
import { ProductWeightPriceDialogComponent } from './product-weight-price/product-weight-price-dialog.component';
import { CustomPriceComponent } from './custom-price/custom-price.component';
import { CustomPriceDialogComponent } from './custom-price/custom-price-dialog.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';


const routes: Routes = [
  //register
  {path: 'register', component: RegisterComponent},
  //login
  {path: 'login', component: LoginComponent},
  //distributor
  { path: 'distributors', component: DistributorComponent },
  { path: 'distributor/new', component: DistributorDialogComponent},
  { path: 'distributor/edit/:id', component: DistributorDialogComponent},
  //distributor-area
  { path: 'distributor-areas', component: DistributorAreaComponent },
  { path: 'distributor-area/new', component: DistributorAreaDialogComponent},
  { path: 'distributor-area/edit/:id', component: DistributorAreaDialogComponent},
  //product-brand
  { path: 'product-brands', component: ProductBrandComponent },
  { path: 'product-brand/new', component: ProductBrandDialogComponent},
  { path: 'product-brand/edit/:id', component: ProductBrandDialogComponent},
  //product
  { path: 'products', component: ProductComponent },
  { path: 'product/new', component: ProductDialogComponent},
  { path: 'product/edit/:id', component: ProductDialogComponent},
  //product-weight-price
  { path: 'product/:productId/weight-price', component: ProductWeightPriceComponent },
  { path: 'product/:productId/weight-price/new', component: ProductWeightPriceDialogComponent},
  { path: 'product/:productId/weight-price/edit/:id', component: ProductWeightPriceDialogComponent},
  //shopkeepers
  { path: 'shopkeepers', component: ShopkeeperComponent },
  { path: 'shopkeeper/new', component: ShopkeeperDialogComponent},
  { path: 'shopkeeper/edit/:id', component: ShopkeeperDialogComponent},
  //custom-price
  { path: 'custom-prices', component: CustomPriceComponent },
  { path: 'custom-price/new', component: CustomPriceDialogComponent},
  { path: 'custom-price/edit/:id', component: CustomPriceDialogComponent},

  //order (Home page)
  {path:'orders', component: OrderComponent}
  
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class AppRoutingModule { }
