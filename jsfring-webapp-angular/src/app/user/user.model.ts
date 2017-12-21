export class User {
    email: string;
    password: string;
    active: boolean;
    lastConnection: Date;
    profiles: Array<number>;
}
