import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GcSharedModule } from 'app/shared';
import {
    UserAccountService,
    UserAccountComponent,
    UserAccountDetailComponent,
    UserAccountUpdateComponent,
    UserAccountDeletePopupComponent,
    UserAccountDeleteDialogComponent,
    userAccountRoute,
    userAccountPopupRoute,
    UserAccountResolve,
    UserAccountResolvePagingParams
} from './';

const ENTITY_STATES = [...userAccountRoute, ...userAccountPopupRoute];

@NgModule({
    imports: [GcSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UserAccountComponent,
        UserAccountDetailComponent,
        UserAccountUpdateComponent,
        UserAccountDeleteDialogComponent,
        UserAccountDeletePopupComponent
    ],
    entryComponents: [UserAccountComponent, UserAccountUpdateComponent, UserAccountDeleteDialogComponent, UserAccountDeletePopupComponent],
    providers: [UserAccountService, UserAccountResolve, UserAccountResolvePagingParams],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GcUserAccountModule {}
