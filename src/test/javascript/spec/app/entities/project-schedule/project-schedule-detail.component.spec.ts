/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs/observable/of';

import { GcTestModule } from '../../../test.module';
import { ProjectScheduleDetailComponent } from 'app/entities/project-schedule/project-schedule-detail.component';
import { ProjectSchedule } from 'app/shared/model/project-schedule.model';

describe('Component Tests', () => {
    describe('ProjectSchedule Management Detail Component', () => {
        let comp: ProjectScheduleDetailComponent;
        let fixture: ComponentFixture<ProjectScheduleDetailComponent>;
        const route = ({ data: of({ projectSchedule: new ProjectSchedule(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GcTestModule],
                declarations: [ProjectScheduleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProjectScheduleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProjectScheduleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.projectSchedule).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
