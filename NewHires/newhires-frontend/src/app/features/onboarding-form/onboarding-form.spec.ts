import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { OnboardingFormComponent } from './onboarding-form';
import { FormService } from '../../core/services/form';

describe('OnboardingFormComponent', () => {
  let component: OnboardingFormComponent;
  let fixture: ComponentFixture<OnboardingFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        OnboardingFormComponent,
        HttpClientTestingModule,
        ReactiveFormsModule,
        RouterTestingModule
      ],
      providers: [FormService]
    }).compileComponents();

    fixture = TestBed.createComponent(OnboardingFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});