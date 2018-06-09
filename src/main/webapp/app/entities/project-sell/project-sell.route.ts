import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from 'app/core';
import { ProjectSell } from 'app/shared/model/project-sell.model';
import { ProjectSellService } from './project-sell.service';
import { ProjectSellComponent } from './project-sell.component';
import { ProjectSellDetailComponent } from './project-sell-detail.component';
import { ProjectSellUpdateComponent } from './project-sell-update.component';
import { ProjectSellDeletePopupComponent } from './project-sell-delete-dialog.component';

@Injectable()
export class ProjectSellResolvePagingParams implements Resolve<any> {
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
export class ProjectSellResolve implements Resolve<any> {
    constructor(private service: ProjectSellService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new ProjectSell();
    }
}

export const projectSellRoute: Routes = [
    {
        path: 'project-sell',
        component: ProjectSellComponent,
        resolve: {
            pagingParams: ProjectSellResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gcApp.projectSell.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'project-sell/:id/view',
        component: ProjectSellDetailComponent,
        resolve: {
            projectSell: ProjectSellResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gcApp.projectSell.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'project-sell/new',
        component: ProjectSellUpdateComponent,
        resolve: {
            projectSell: ProjectSellResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gcApp.projectSell.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'project-sell/:id/edit',
        component: ProjectSellUpdateComponent,
        resolve: {
            projectSell: ProjectSellResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gcApp.projectSell.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const projectSellPopupRoute: Routes = [
    {
        path: 'project-sell/:id/delete',
        component: ProjectSellDeletePopupComponent,
        resolve: {
            projectSell: ProjectSellResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gcApp.projectSell.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
