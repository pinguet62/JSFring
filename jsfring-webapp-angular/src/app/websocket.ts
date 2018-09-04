import {APP_INITIALIZER, Injectable, NgModule} from '@angular/core';
import {StompRService} from '@stomp/ng2-stompjs';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {environment} from '../environments/environment';
import {SecurityService} from './oauth2';

@Injectable()
export class WebSocketService extends StompRService {
    public readonly userRightsUpdated: Observable<string>;

    constructor(private securityService: SecurityService) {
        super();

        securityService.onConnect.subscribe(() => this.initStomp());

        this.userRightsUpdated = this.subscribe('/topic/USER_RIGHTS_UPDATED')
            .pipe(map(it => it.body));
    }

    private initStomp() {
        this.config = {
            url: environment.api.replace(/^http/, 'ws') + '/stomp?access_token=' + this.securityService.token,
            headers: {},
            heartbeat_in: 0,
            heartbeat_out: 0,
            reconnect_delay: 1000,
            debug: false,
        };
        this.initAndConnect();
    }
}

@NgModule({
    providers: [
        WebSocketService,
        {provide: APP_INITIALIZER, multi: true, deps: [WebSocketService], useFactory: (service: WebSocketService) => () => null}, // init on startup
    ]
})
export class WebSocketModule {
}
