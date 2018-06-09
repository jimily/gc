export interface IProjectSell {
    id?: number;
    projectId?: number;
    copyrightSellType?: number;
    sellingPrice?: number;
    txHash?: string;
    description?: string;
    ownerUserId?: number;
    buyerId?: number;
    isSell?: number;
    createTime?: number;
    updateTime?: number;
}

export class ProjectSell implements IProjectSell {
    constructor(
        public id?: number,
        public projectId?: number,
        public copyrightSellType?: number,
        public sellingPrice?: number,
        public txHash?: string,
        public description?: string,
        public ownerUserId?: number,
        public buyerId?: number,
        public isSell?: number,
        public createTime?: number,
        public updateTime?: number
    ) {}
}
