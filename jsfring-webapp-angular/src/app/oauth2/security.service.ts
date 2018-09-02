import {EventEmitter, Injectable} from '@angular/core';

/** Store information about user's security information. */
@Injectable()
export class SecurityService {

    private _token: string;

    get token() {
        return this._token;
    }

    set token(value: string) {
        this._token = value;
        if (value) {
            this.onConnect.emit(null);
        }
    }

    public onConnect: EventEmitter<any> = new EventEmitter<any>();

    isAuthenticated(): boolean {
        return this.token != null;
    }

}
