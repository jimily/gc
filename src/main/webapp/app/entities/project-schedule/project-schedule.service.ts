import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProjectSchedule } from 'app/shared/model/project-schedule.model';

type EntityResponseType = HttpResponse<IProjectSchedule>;
type EntityArrayResponseType = HttpResponse<IProjectSchedule[]>;

@Injectable()
export class ProjectScheduleService {
    private resourceUrl = SERVER_API_URL + 'api/project-schedules';

    constructor(private http: HttpClient) {}

    create(projectSchedule: IProjectSchedule): Observable<EntityResponseType> {
        return this.http.post<IProjectSchedule>(this.resourceUrl, projectSchedule, { observe: 'response' });
    }

    update(projectSchedule: IProjectSchedule): Observable<EntityResponseType> {
        return this.http.put<IProjectSchedule>(this.resourceUrl, projectSchedule, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProjectSchedule>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProjectSchedule[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
