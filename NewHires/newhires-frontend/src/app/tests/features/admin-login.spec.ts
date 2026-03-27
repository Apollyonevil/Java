import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AdminLoginComponent } from '../../features/admin-login/admin-login';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { AuthService } from '../../core/services/auth';

describe('AdminLoginComponent', () => {
  let component: AdminLoginComponent;
  let fixture: ComponentFixture<AdminLoginComponent>;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminLoginComponent],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        // Definimos la ruta 'admin' para que la navegación no falle
        provideRouter([
          { path: 'admin', component: AdminLoginComponent } 
        ]),
        AuthService
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AdminLoginComponent);
    component = fixture.componentInstance;
    httpMock = TestBed.inject(HttpTestingController);
    fixture.detectChanges();
  });

  it('debe realizar la petición de login con Basic Auth', () => {
    component.username = 'admin';
    component.password = '1234';
    component.login();

    // Verificamos que se llame a la URL correcta del backend
    const req = httpMock.expectOne('http://localhost:8080/api/admin/forms/submissions');
    expect(req.request.method).toBe('GET');
    
    // Verificamos que lleve la cabecera de Authorization
    expect(req.request.headers.has('Authorization')).toBe(true);
    
    req.flush({}); // Simulamos respuesta exitosa
    httpMock.verify();
  });
});