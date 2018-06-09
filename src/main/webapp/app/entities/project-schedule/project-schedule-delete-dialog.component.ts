import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjectSchedule } from 'app/shared/model/project-schedule.model';
import { ProjectScheduleService } from './project-schedule.service';

@Component({
    selector: 'jhi-project-schedule-delete-dialog',
    templateUrl: './project-schedule-delete-dialog.component.html'
})
export class ProjectScheduleDeleteDialogComponent {
    projectSchedule: IProjectSchedule;

    constructor(
        private projectScheduleService: ProjectScheduleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.projectScheduleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'projectScheduleListModification',
                content: 'Deleted an projectSchedule'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-project-schedule-delete-popup',
    template: ''
})
export class ProjectScheduleDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private route: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.route.data.subscribe(({ projectSchedule }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProjectScheduleDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.projectSchedule = projectSchedule.body;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
