import { Component, Input } from '@angular/core';

import { SecurityService } from './security.service';

/** Proxy component used to render sub-components based on user's roles. */
@Component({
    selector: 'p62-authorize',
    template: '<ng-content *ngIf="isAuthorized()"></ng-content>'
})
export class AuthorizeComponent {

    /** A comma-separated list of user roles. */
    @Input() areAllGranted: string = '';

    /** A comma-separated list of user roles. */
    @Input() areAnyGranted: string = '';

    /** A comma-separated list of user roles. */
    @Input() areNotGranted: string = '';

    constructor(private securityService: SecurityService) { }

    /**
     * Split role list, using comma separator.
     * Trim values, removing spaces.
     */
    private splitAndTrim(roles: string): Array<string> {
        return roles.split(',').map(x => x.trim());
    }

    /** Check that user has all roles. */
    private isAllGranted(): boolean {
        if (this.areAllGranted === '') return true;
        for (let role of this.splitAndTrim(this.areAllGranted))
            // If any role is missing: false
            if (this.securityService.getRoles().indexOf(role) < 0)
                return false;
        return true;
    }

    /** Check that user has any roles. */
    private isAnyGranted(): boolean {
        if (this.areAnyGranted === '') return true;
        for (let role of this.splitAndTrim(this.areAnyGranted))
            // If any role is present: true
            if (this.securityService.getRoles().indexOf(role) >= 0)
                return true;
        return false;
    }

    /** Check that user doesn't have all roles. */
    private isNotGranted(): boolean {
        if (this.areNotGranted === '') return true;
        for (let role of this.splitAndTrim(this.areNotGranted))
            if (this.securityService.getRoles().indexOf(role) >= 0)
                return false;
        return true;
    }

    isAuthorized(): boolean {
        return this.isAllGranted() && this.isAnyGranted() && this.isNotGranted();
    }

}