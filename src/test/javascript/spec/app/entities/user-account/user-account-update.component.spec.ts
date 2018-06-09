/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GcTestModule } from '../../../test.module';
import { UserAccountUpdateComponent } from 'app/entities/user-account/user-account-update.component';
import { UserAccountService } from 'app/entities/user-account/user-account.service';
import { UserAccount } from 'app/shared/model/user-account.model';

describe('Component Tests', () => {
    describe('UserAccount Management Update Component', () => {
        let comp: UserAccountUpdateComponent;
        let fixture: ComponentFixture<UserAccountUpdateComponent>;
        let service: UserAccountService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GcTestModule],
                declarations: [UserAccountUpdateComponent],
                providers: [UserAccountService]
            })
                .overrideTemplate(UserAccountUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UserAccountUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserAccountService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new UserAccount(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.userAccount = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new UserAccount();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.userAccount = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
