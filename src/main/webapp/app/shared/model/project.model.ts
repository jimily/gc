export interface IProject {
    id?: number;
    name?: string;
    type?: number;
    description?: string;
    copyrightNo?: string;
    ratio?: number;
    gross?: number;
    onceBuyLimit?: number;
    balance?: number;
    isEnd?: number;
    contractAddress?: string;
    userId?: number;
    createTime?: number;
    updateTime?: number;
}

export class Project implements IProject {
    constructor(
        public id?: number,
        public name?: string,
        public type?: number,
        public description?: string,
        public copyrightNo?: string,
        public ratio?: number,
        public gross?: number,
        public onceBuyLimit?: number,
        public balance?: number,
        public isEnd?: number,
        public contractAddress?: string,
        public userId?: number,
        public createTime?: number,
        public updateTime?: number
    ) {}
}
