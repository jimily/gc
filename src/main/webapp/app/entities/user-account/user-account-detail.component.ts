import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserAccount } from 'app/shared/model/user-account.model';

@Component({
    selector: 'jhi-user-account-detail',
    templateUrl: './user-account-detail.component.html'
})
export class UserAccountDetailComponent implements OnInit {
    userAccount: IUserAccount;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ userAccount }) => {
            this.userAccount = userAccount.body ? userAccount.body : userAccount;
        });
    }

    previousState() {
        window.history.back();
    }
}
