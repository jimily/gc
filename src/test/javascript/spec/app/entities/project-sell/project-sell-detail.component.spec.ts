/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs/observable/of';

import { GcTestModule } from '../../../test.module';
import { ProjectSellDetailComponent } from 'app/entities/project-sell/project-sell-detail.component';
import { ProjectSell } from 'app/shared/model/project-sell.model';

describe('Component Tests', () => {
    describe('ProjectSell Management Detail Component', () => {
        let comp: ProjectSellDetailComponent;
        let fixture: ComponentFixture<ProjectSellDetailComponent>;
        const route = ({ data: of({ projectSell: new ProjectSell(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GcTestModule],
                declarations: [ProjectSellDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProjectSellDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProjectSellDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.projectSell).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
