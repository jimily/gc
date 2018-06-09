import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectSell } from 'app/shared/model/project-sell.model';

@Component({
    selector: 'jhi-project-sell-detail',
    templateUrl: './project-sell-detail.component.html'
})
export class ProjectSellDetailComponent implements OnInit {
    projectSell: IProjectSell;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ projectSell }) => {
            this.projectSell = projectSell.body ? projectSell.body : projectSell;
        });
    }

    previousState() {
        window.history.back();
    }
}
