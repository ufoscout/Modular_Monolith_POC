import {Component} from '@angular/core';

/**
 * Navigation bar component.
 */
@Component({
  selector: 'main-navbar',
  templateUrl: './navbar.component.html'
})
export class NavbarComponent {
  public isCollapsed = false;
  public relativeDate!: string;
  public creationDate!: number;
}

