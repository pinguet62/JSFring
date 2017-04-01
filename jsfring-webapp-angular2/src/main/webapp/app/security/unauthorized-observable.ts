import {Injectable, EventEmitter} from "@angular/core";

/** Service to communicate when an "401 Unauthorized" HTTP error is thrown. */
@Injectable()
export class UnauthorizedObservable {

    public observable: EventEmitter<any> = new EventEmitter<any>();

}