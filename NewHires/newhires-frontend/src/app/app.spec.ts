import { TestBed } from '@angular/core/testing';
import { AppComponent } from './app';
import { provideRouter, Router } from '@angular/router';
import { vi } from 'vitest';

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppComponent],
      providers: [provideRouter([])]
    }).compileComponents();
  });

  it('debe crear la aplicación', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('debe devolver la ruta /admin si la URL actual contiene admin', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    const router = TestBed.inject(Router);
    
    // Sintaxis Vitest para espiar getters
    vi.spyOn(router, 'url', 'get').mockReturnValue('/admin/dashboard');
    
    expect(app.getLogoRoute()).toBe('/admin');
  });

  it('debe devolver la ruta raíz si la URL no es de admin', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    const router = TestBed.inject(Router);
    
    vi.spyOn(router, 'url', 'get').mockReturnValue('/onboarding');
    
    expect(app.getLogoRoute()).toBe('/');
  });
});