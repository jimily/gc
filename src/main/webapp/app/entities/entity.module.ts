import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GcUserAccountModule } from './user-account/user-account.module';
import { GcProjectModule } from './project/project.module';
import { GcProjectScheduleModule } from './project-schedule/project-schedule.module';
import { GcProjectSellModule } from './project-sell/project-sell.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        GcUserAccountModule,
        GcProjectModule,
        GcProjectScheduleModule,
        GcProjectSellModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GcEntityModule {}
