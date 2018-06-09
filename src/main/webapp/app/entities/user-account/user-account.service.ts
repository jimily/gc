import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserAccount } from 'app/shared/model/user-account.model';

type EntityResponseType = HttpResponse<IUserAccount>;
type EntityArrayResponseType = HttpResponse<IUserAccount[]>;

@Injectable()
export class UserAccountService {
    private resourceUrl = SERVER_API_URL + 'api/user-accounts';

    constructor(private http: HttpClient) {}

    create(userAccount: IUserAccount): Observable<EntityResponseType> {
        return this.http.post<IUserAccount>(this.resourceUrl, userAccount, { observe: 'response' });
    }

    update(userAccount: IUserAccount): Observable<EntityResponseType> {
        return this.http.put<IUserAccount>(this.resourceUrl, userAccount, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IUserAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUserAccount[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
