import { Component, OnInit } from '@angular/core';
import { product } from '../shared/models/product';
import { ProductService } from '../shared/services/product.service';

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css'],
})
export class ShopComponent implements OnInit {
  products!: product[];
  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.reloadData();
  }
  reloadData() {
    this.productService.getAllProducts().subscribe((data) => {
      this.products = data;
      console.log(this.products[1].name);
    });
  }
}
