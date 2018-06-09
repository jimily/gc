export interface IUserAccount {
    id?: number;
    userId?: number;
    asset?: number;
    pubKey?: string;
    privateKey?: string;
    blockchainAddress?: string;
    createTime?: number;
    updateTime?: number;
}

export class UserAccount implements IUserAccount {
    constructor(
        public id?: number,
        public userId?: number,
        public asset?: number,
        public pubKey?: string,
        public privateKey?: string,
        public blockchainAddress?: string,
        public createTime?: number,
        public updateTime?: number
    ) {}
}
