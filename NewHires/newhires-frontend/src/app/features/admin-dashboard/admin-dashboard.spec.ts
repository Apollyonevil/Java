import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { AdminDashboardComponent } from './admin-dashboard'; // 1. Nombre de clase corregido
import { FormService } from '../../core/services/form';

describe('AdminDashboardComponent', () => {
  let component: AdminDashboardComponent;
  let fixture: ComponentFixture<AdminDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      // Al ser un componente Standalone, va en 'imports'
      imports: [
        AdminDashboardComponent, 
        HttpClientTestingModule // Necesario para que el servicio FormService funcione en el test
      ],
      providers: [FormService]
    }).compileComponents();

    fixture = TestBed.createComponent(AdminDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges(); // Ejecuta ngOnInit y carga inicial
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});