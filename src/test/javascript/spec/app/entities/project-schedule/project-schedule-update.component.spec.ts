/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GcTestModule } from '../../../test.module';
import { ProjectScheduleUpdateComponent } from 'app/entities/project-schedule/project-schedule-update.component';
import { ProjectScheduleService } from 'app/entities/project-schedule/project-schedule.service';
import { ProjectSchedule } from 'app/shared/model/project-schedule.model';

describe('Component Tests', () => {
    describe('ProjectSchedule Management Update Component', () => {
        let comp: ProjectScheduleUpdateComponent;
        let fixture: ComponentFixture<ProjectScheduleUpdateComponent>;
        let service: ProjectScheduleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GcTestModule],
                declarations: [ProjectScheduleUpdateComponent],
                providers: [ProjectScheduleService]
            })
                .overrideTemplate(ProjectScheduleUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProjectScheduleUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjectScheduleService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProjectSchedule(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.projectSchedule = entity;
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
                    const entity = new ProjectSchedule();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.projectSchedule = entity;
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
