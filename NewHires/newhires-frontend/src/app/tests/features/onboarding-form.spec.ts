import { ComponentFixture, TestBed } from '@angular/core/testing';
import { OnboardingFormComponent } from '../../features/onboarding-form/onboarding-form';
import { FormService } from '../../core/services/form';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter, ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { vi } from 'vitest';

describe('OnboardingFormComponent', () => {
  let component: OnboardingFormComponent;
  let fixture: ComponentFixture<OnboardingFormComponent>;

  // Mock manual para Vitest
  const formServiceMock = {
    getFormStructure: vi.fn(() => of([
      { id: '1', label: 'Nombre', type: 'TEXT', required: true, sortOrder: 1 }
    ])),
    submitForm: vi.fn()
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OnboardingFormComponent],
      providers: [
        { provide: FormService, useValue: formServiceMock },
        provideHttpClient(),
        provideHttpClientTesting(),
        provideRouter([]),
        {
          provide: ActivatedRoute,
          useValue: { snapshot: { queryParamMap: { get: () => 'test-token' } } }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(OnboardingFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('debe cargar la estructura del formulario al iniciar', () => {
    expect(component.fields.length).toBeGreaterThan(0);
    expect(component.dynamicForm.contains('1')).toBe(true);
  });

  it('debe validar tipos de archivo (PDF)', () => {
    const file = new File([''], 'test.txt', { type: 'text/plain' });
    const event = { target: { files: [file] } };
    
    component.onFileChange(event, '2', 'placeholder', 'pdf');
    
    expect(component.fileErrors['2']).toBe('Solo se permiten archivos PDF (.pdf)');
  });
});