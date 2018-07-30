import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule }   from '@angular/forms';
import { HttpModule } from '@angular/http';
import { ErrorHandler} from '@angular/core';

import { AppComponent } from './app.component';
import { DistributorComponent } from './distributor/distributor.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
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
import { ProductWeightPrice } from './product-weight-price/product-weight-price';
import { ProductWeightPriceComponent } from './product-weight-price/product-weight-price.component';
import { ProductWeightPriceDialogComponent } from './product-weight-price/product-weight-price-dialog.component';
import { CustomPriceComponent } from './custom-price/custom-price.component';
import { CustomPriceDialogComponent } from './custom-price/custom-price-dialog.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { AuthInterceptor } from './auth/customhttpinterceptor';
import { ErrorInterceptor } from './auth/error.interceptor';
import { ProductByBrandFilterPipe } from './custom/pipes';
import { ErrorsHandler } from './notification/errors/errors-handler';
import { ErrorsComponent } from './notification/errors/errors.component';


@NgModule({
  declarations: [
    AppComponent,
    //register and login
    RegisterComponent,
    LoginComponent,
    //distributor
    DistributorComponent,
    DistributorDialogComponent,
    //distributor-area
    DistributorAreaComponent,
    DistributorAreaDialogComponent,
    //product-brand
    ProductBrandComponent,
    ProductBrandDialogComponent,
    //product
    ProductComponent,
    ProductDialogComponent,
    //product-quantity-price
    ProductWeightPriceComponent,
    ProductWeightPriceDialogComponent,
    //Shopkeeper
    ShopkeeperComponent,
    ShopkeeperDialogComponent,
    //custom-price
    CustomPriceComponent,
    CustomPriceDialogComponent,
    //order (home)
    OrderComponent,
    ProductByBrandFilterPipe,
    //global error componenet
    ErrorsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS,useClass: AuthInterceptor,multi: true},
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    {provide: ErrorHandler, useClass: ErrorsHandler}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
