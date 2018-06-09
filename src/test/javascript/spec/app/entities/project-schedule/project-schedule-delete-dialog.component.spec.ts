/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GcTestModule } from '../../../test.module';
import { ProjectScheduleDeleteDialogComponent } from 'app/entities/project-schedule/project-schedule-delete-dialog.component';
import { ProjectScheduleService } from 'app/entities/project-schedule/project-schedule.service';

describe('Component Tests', () => {
    describe('ProjectSchedule Management Delete Component', () => {
        let comp: ProjectScheduleDeleteDialogComponent;
        let fixture: ComponentFixture<ProjectScheduleDeleteDialogComponent>;
        let service: ProjectScheduleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GcTestModule],
                declarations: [ProjectScheduleDeleteDialogComponent],
                providers: [ProjectScheduleService]
            })
                .overrideTemplate(ProjectScheduleDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProjectScheduleDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjectScheduleService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
