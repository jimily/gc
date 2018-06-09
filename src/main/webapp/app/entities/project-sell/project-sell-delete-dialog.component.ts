import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjectSell } from 'app/shared/model/project-sell.model';
import { ProjectSellService } from './project-sell.service';

@Component({
    selector: 'jhi-project-sell-delete-dialog',
    templateUrl: './project-sell-delete-dialog.component.html'
})
export class ProjectSellDeleteDialogComponent {
    projectSell: IProjectSell;

    constructor(
        private projectSellService: ProjectSellService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.projectSellService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'projectSellListModification',
                content: 'Deleted an projectSell'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-project-sell-delete-popup',
    template: ''
})
export class ProjectSellDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private route: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.route.data.subscribe(({ projectSell }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProjectSellDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.projectSell = projectSell.body;
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
