import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { IProjectSell } from 'app/shared/model/project-sell.model';
import { ProjectSellService } from './project-sell.service';

@Component({
    selector: 'jhi-project-sell-update',
    templateUrl: './project-sell-update.component.html'
})
export class ProjectSellUpdateComponent implements OnInit {
    private _projectSell: IProjectSell;
    isSaving: boolean;

    constructor(private projectSellService: ProjectSellService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ projectSell }) => {
            this.projectSell = projectSell.body ? projectSell.body : projectSell;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.projectSell.id !== undefined) {
            this.subscribeToSaveResponse(this.projectSellService.update(this.projectSell));
        } else {
            this.subscribeToSaveResponse(this.projectSellService.create(this.projectSell));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProjectSell>>) {
        result.subscribe((res: HttpResponse<IProjectSell>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get projectSell() {
        return this._projectSell;
    }

    set projectSell(projectSell: IProjectSell) {
        this._projectSell = projectSell;
    }
}
