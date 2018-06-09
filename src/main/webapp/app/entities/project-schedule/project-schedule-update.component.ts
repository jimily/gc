import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { IProjectSchedule } from 'app/shared/model/project-schedule.model';
import { ProjectScheduleService } from './project-schedule.service';

@Component({
    selector: 'jhi-project-schedule-update',
    templateUrl: './project-schedule-update.component.html'
})
export class ProjectScheduleUpdateComponent implements OnInit {
    private _projectSchedule: IProjectSchedule;
    isSaving: boolean;

    constructor(private projectScheduleService: ProjectScheduleService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ projectSchedule }) => {
            this.projectSchedule = projectSchedule.body ? projectSchedule.body : projectSchedule;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.projectSchedule.id !== undefined) {
            this.subscribeToSaveResponse(this.projectScheduleService.update(this.projectSchedule));
        } else {
            this.subscribeToSaveResponse(this.projectScheduleService.create(this.projectSchedule));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProjectSchedule>>) {
        result.subscribe((res: HttpResponse<IProjectSchedule>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get projectSchedule() {
        return this._projectSchedule;
    }

    set projectSchedule(projectSchedule: IProjectSchedule) {
        this._projectSchedule = projectSchedule;
    }
}
