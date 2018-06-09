import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GcSharedModule } from 'app/shared';
import {
    ProjectScheduleService,
    ProjectScheduleComponent,
    ProjectScheduleDetailComponent,
    ProjectScheduleUpdateComponent,
    ProjectScheduleDeletePopupComponent,
    ProjectScheduleDeleteDialogComponent,
    projectScheduleRoute,
    projectSchedulePopupRoute,
    ProjectScheduleResolve,
    ProjectScheduleResolvePagingParams
} from './';

const ENTITY_STATES = [...projectScheduleRoute, ...projectSchedulePopupRoute];

@NgModule({
    imports: [GcSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProjectScheduleComponent,
        ProjectScheduleDetailComponent,
        ProjectScheduleUpdateComponent,
        ProjectScheduleDeleteDialogComponent,
        ProjectScheduleDeletePopupComponent
    ],
    entryComponents: [
        ProjectScheduleComponent,
        ProjectScheduleUpdateComponent,
        ProjectScheduleDeleteDialogComponent,
        ProjectScheduleDeletePopupComponent
    ],
    providers: [ProjectScheduleService, ProjectScheduleResolve, ProjectScheduleResolvePagingParams],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GcProjectScheduleModule {}
