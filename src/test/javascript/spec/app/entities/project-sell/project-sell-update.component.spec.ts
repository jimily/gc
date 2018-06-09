/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GcTestModule } from '../../../test.module';
import { ProjectSellUpdateComponent } from 'app/entities/project-sell/project-sell-update.component';
import { ProjectSellService } from 'app/entities/project-sell/project-sell.service';
import { ProjectSell } from 'app/shared/model/project-sell.model';

describe('Component Tests', () => {
    describe('ProjectSell Management Update Component', () => {
        let comp: ProjectSellUpdateComponent;
        let fixture: ComponentFixture<ProjectSellUpdateComponent>;
        let service: ProjectSellService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GcTestModule],
                declarations: [ProjectSellUpdateComponent],
                providers: [ProjectSellService]
            })
                .overrideTemplate(ProjectSellUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProjectSellUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjectSellService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProjectSell(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.projectSell = entity;
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
                    const entity = new ProjectSell();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.projectSell = entity;
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
