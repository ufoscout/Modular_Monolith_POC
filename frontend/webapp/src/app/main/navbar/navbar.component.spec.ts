import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { MainModule } from '../main.module';

import { NavbarComponent } from './navbar.component';

describe('NavbarComponent', () => {

    let fixture: ComponentFixture<NavbarComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [
                MainModule,
                RouterTestingModule
            ]
        });
        TestBed.compileComponents();
        fixture = TestBed.createComponent(NavbarComponent);
    }));

    it('Given the NavbarComponent, when it is created, then should render correctly', () => {
        fixture.detectChanges();
        expect(fixture.nativeElement).toBeDefined();
    });

});
