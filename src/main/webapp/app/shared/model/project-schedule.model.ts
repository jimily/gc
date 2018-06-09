export interface IProjectSchedule {
    id?: number;
    userId?: number;
    projectId?: number;
    balance?: number;
    txHash?: string;
    createTime?: number;
    updateTime?: number;
}

export class ProjectSchedule implements IProjectSchedule {
    constructor(
        public id?: number,
        public userId?: number,
        public projectId?: number,
        public balance?: number,
        public txHash?: string,
        public createTime?: number,
        public updateTime?: number
    ) {}
}
