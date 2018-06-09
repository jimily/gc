import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectSchedule } from 'app/shared/model/project-schedule.model';

@Component({
    selector: 'jhi-project-schedule-detail',
    templateUrl: './project-schedule-detail.component.html'
})
export class ProjectScheduleDetailComponent implements OnInit {
    projectSchedule: IProjectSchedule;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ projectSchedule }) => {
            this.projectSchedule = projectSchedule.body ? projectSchedule.body : projectSchedule;
        });
    }

    previousState() {
        window.history.back();
    }
}
