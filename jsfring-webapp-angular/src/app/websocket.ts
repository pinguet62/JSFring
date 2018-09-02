import {APP_INITIALIZER, Injectable, NgModule} from '@angular/core';
import {StompService} from '@stomp/ng2-stompjs';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {environment} from '../environments/environment';

@Injectable()
export class WebSocketService extends StompService {
    public readonly userRightsUpdated: Observable<string>;

    constructor() {
        super({
            url: environment.api.replace(new RegExp('https?'), 'ws') + '/stomp',
            headers: null,
            heartbeat_in: 0,
            heartbeat_out: 0,
            reconnect_delay: 1000,
            debug: false,
        });
        this.userRightsUpdated = this.subscribe('/topic/USER_RIGHTS_UPDATED')
            .pipe(map(it => it.body));
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
