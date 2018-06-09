import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from 'app/core';
import { UserAccount } from 'app/shared/model/user-account.model';
import { UserAccountService } from './user-account.service';
import { UserAccountComponent } from './user-account.component';
import { UserAccountDetailComponent } from './user-account-detail.component';
import { UserAccountUpdateComponent } from './user-account-update.component';
import { UserAccountDeletePopupComponent } from './user-account-delete-dialog.component';

@Injectable()
export class UserAccountResolvePagingParams implements Resolve<any> {
    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
        };
    }
}

@Injectable()
export class UserAccountResolve implements Resolve<any> {
    constructor(private service: UserAccountService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new UserAccount();
    }
}

export const userAccountRoute: Routes = [
    {
        path: 'user-account',
        component: UserAccountComponent,
        resolve: {
            pagingParams: UserAccountResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gcApp.userAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-account/:id/view',
        component: UserAccountDetailComponent,
        resolve: {
            userAccount: UserAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gcApp.userAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-account/new',
        component: UserAccountUpdateComponent,
        resolve: {
            userAccount: UserAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gcApp.userAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-account/:id/edit',
        component: UserAccountUpdateComponent,
        resolve: {
            userAccount: UserAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gcApp.userAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userAccountPopupRoute: Routes = [
    {
        path: 'user-account/:id/delete',
        component: UserAccountDeletePopupComponent,
        resolve: {
            userAccount: UserAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gcApp.userAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
