import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AdminDashboardComponent } from '../../features/admin-dashboard/admin-dashboard';
import { FormService } from '../../core/services/form';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';
import { of } from 'rxjs';
import { vi } from 'vitest';

describe('AdminDashboardComponent', () => {
  let component: AdminDashboardComponent;
  let fixture: ComponentFixture<AdminDashboardComponent>;

  // Mock de Vitest (sin jasmine)
  const formServiceMock = {
    getFormStructure: vi.fn(() => of([])),
    getAllSubmissions: vi.fn(() => of([])),
    getAdminUsers: vi.fn(() => of([])),
    getFormVersions: vi.fn(() => of([])),
    createInvitation: vi.fn((name: string, email: string) => of({ id: '123' }))
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminDashboardComponent],
      providers: [
        { provide: FormService, useValue: formServiceMock },
        provideHttpClient(),
        provideHttpClientTesting(),
        provideRouter([]) // Esto evita el error de "No match any routes"
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AdminDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('debe cambiar de pestaña correctamente', () => {
    component.setActiveTab('config');
    expect(component.activeTab).toBe('config');
  });

  it('debe llamar al servicio para generar una invitación', () => {
    component.newCandidateName = 'Juan';
    component.newCandidateEmail = 'test@example.com';
    component.generateInvitation();
    expect(formServiceMock.createInvitation).toHaveBeenCalledWith('Juan', 'test@example.com');
  });

  it('debe abrir el modal de rechazo', () => {
    const mockSubmission = { id: '1', candidateName: 'Juan' };
    component.openRejectModal(mockSubmission);
    expect(component.showRejectModal).toBe(true);
    expect(component.selectedSubmission).toEqual(mockSubmission);
  });
});