import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProjectSell } from 'app/shared/model/project-sell.model';

type EntityResponseType = HttpResponse<IProjectSell>;
type EntityArrayResponseType = HttpResponse<IProjectSell[]>;

@Injectable()
export class ProjectSellService {
    private resourceUrl = SERVER_API_URL + 'api/project-sells';

    constructor(private http: HttpClient) {}

    create(projectSell: IProjectSell): Observable<EntityResponseType> {
        return this.http.post<IProjectSell>(this.resourceUrl, projectSell, { observe: 'response' });
    }

    update(projectSell: IProjectSell): Observable<EntityResponseType> {
        return this.http.put<IProjectSell>(this.resourceUrl, projectSell, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProjectSell>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProjectSell[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
