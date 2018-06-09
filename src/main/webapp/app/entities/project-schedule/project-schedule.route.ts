import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from 'app/core';
import { ProjectSchedule } from 'app/shared/model/project-schedule.model';
import { ProjectScheduleService } from './project-schedule.service';
import { ProjectScheduleComponent } from './project-schedule.component';
import { ProjectScheduleDetailComponent } from './project-schedule-detail.component';
import { ProjectScheduleUpdateComponent } from './project-schedule-update.component';
import { ProjectScheduleDeletePopupComponent } from './project-schedule-delete-dialog.component';

@Injectable()
export class ProjectScheduleResolvePagingParams implements Resolve<any> {
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
export class ProjectScheduleResolve implements Resolve<any> {
    constructor(private service: ProjectScheduleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new ProjectSchedule();
    }
}

export const projectScheduleRoute: Routes = [
    {
        path: 'project-schedule',
        component: ProjectScheduleComponent,
        resolve: {
            pagingParams: ProjectScheduleResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gcApp.projectSchedule.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'project-schedule/:id/view',
        component: ProjectScheduleDetailComponent,
        resolve: {
            projectSchedule: ProjectScheduleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gcApp.projectSchedule.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'project-schedule/new',
        component: ProjectScheduleUpdateComponent,
        resolve: {
            projectSchedule: ProjectScheduleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gcApp.projectSchedule.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'project-schedule/:id/edit',
        component: ProjectScheduleUpdateComponent,
        resolve: {
            projectSchedule: ProjectScheduleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gcApp.projectSchedule.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const projectSchedulePopupRoute: Routes = [
    {
        path: 'project-schedule/:id/delete',
        component: ProjectScheduleDeletePopupComponent,
        resolve: {
            projectSchedule: ProjectScheduleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gcApp.projectSchedule.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
