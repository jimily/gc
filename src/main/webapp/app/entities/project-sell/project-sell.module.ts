import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GcSharedModule } from 'app/shared';
import {
    ProjectSellService,
    ProjectSellComponent,
    ProjectSellDetailComponent,
    ProjectSellUpdateComponent,
    ProjectSellDeletePopupComponent,
    ProjectSellDeleteDialogComponent,
    projectSellRoute,
    projectSellPopupRoute,
    ProjectSellResolve,
    ProjectSellResolvePagingParams
} from './';

const ENTITY_STATES = [...projectSellRoute, ...projectSellPopupRoute];

@NgModule({
    imports: [GcSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProjectSellComponent,
        ProjectSellDetailComponent,
        ProjectSellUpdateComponent,
        ProjectSellDeleteDialogComponent,
        ProjectSellDeletePopupComponent
    ],
    entryComponents: [ProjectSellComponent, ProjectSellUpdateComponent, ProjectSellDeleteDialogComponent, ProjectSellDeletePopupComponent],
    providers: [ProjectSellService, ProjectSellResolve, ProjectSellResolvePagingParams],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GcProjectSellModule {}
