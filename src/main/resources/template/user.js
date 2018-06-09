function main(inputData) {
    callBackLog("inputData: " + inputData);
    try {
        var input = JSON.parse(inputData);
    } catch (error) {
        return;
    }

    var action = input.action;
    switch (action) {
        case ACTION.FINISHRAISE :
            finishRaise(input);
            break;
        case ACTION.INCOMEOFINVESTE :
            incomeOfInveste(input);
            break;
        case ACTION.SALECOPYRIGHT :
            saleCopyright(input);
            break;
        case ACTIONN.INCOMEOFIDEA :
            incomeOfIdea(input);
            break;
        default :
            throw "{'code':'100100','msg':'无效的操作类型'}";
    }
};

/** 众筹结束,合约调用该函数，为创意者增加收入 **/
function finishRaise(input) {
    var project = input.project;
    var amount = input.amount;
    //检查众筹状态
    var ideaRecord = getMetadataValue(project);
    if (ideaRecord.status == CONTRACT_STATUS.FINISH) {
        throw "{'code':'100105','msg':'合约已结束'}";
    }
    ideaRecord.status = CONTRACT_STATUS.FINISH;
    ideaRecord.income = ideaRecord.income + amount;

    var transaction = {
        'operations': [{
            'type': 4,
            'set_metadata': {'key': project, 'value': JSON.stringify(ideaRecord)}
        },{
            'type': 4,
            'set_metadata': {'key': 'income', 'value': amount}
        }]
    };

    var isSuccess = callBackDoOperation(transaction);
    if (!isSuccess) {
        throw "{'code':'100115','msg':'合约结束-清算给创意者失败'}";
    }

}

function getAmount(key) {
    var amount = callBackGetAccountMetaData(thisAddress, key);
    if (amount) {
        return amount.value;
    }
    return 0;
};

/** 参与的众筹产生收益 合约调用**/
function incomeOfInveste(input) {
    var project = input.project;
    var amount = input.amount;
    var contractAddress = input.contractAddress;
    //检查入参金额
    if (amount < 0) {
        throw "{'code':'100105','msg':'金额不对'}";
    }
    var investRecord = getRecords(project);
    //检查是否为创意合约地址发起
    if (contractAddress != investRecord.contractAddress) {
        throw "{'code':'100106','msg':'发起地址不对'}";
    }
    investRecord.income = investRecord.income + amount;
    var thisAddressIncome = getAmount('income');
    thisAddressIncome = thisAddressIncome + amount;

    var transaction = {
        'operations': [{
            'type': 4,
            'set_metadata': {'key': project, 'value': investRecord}
        },{
            'type': 4,
            'set_metadata': {'key': 'income', 'value': thisAddressIncome}
        }]
    };

    callBackDoOperation(transaction);
};


function getRecords(key) {
    var records = [];

    var metadata = callBackGetAccountMetaData(thisAddress, key);
    if (metadata) {
        records = JSON.parse(metadata.value);
    }

    return records;
};
function getMetadataValue(key) {

    var records = {};

    var metadata = callBackGetAccountMetaData(thisAddress, key);
    if (metadata) {
        records = JSON.parse(metadata.value);
    }

    return records;
}
/** 贩卖版权（被购买）**/
function saleCopyright(input) {
    var buyer = sender;
    var project = input.project;
    var copyright = input.copyright;
    //检查对应的创意是否存在
    var projectRecord = getMetadataValue(project);
    if (projectRecord == undefined) {
        throw "{'code':'100109','msg':'无权限'}";
    }

    callBackDoOperation({
        'operations': [{
            'type': 4,
            'set_metadata': {'key': 'jr', 'value': copyright}
        }]
    });
    //检查待登记的版权类型
};

/** 自己的创意产生收益 合约调用**/
function incomeOfIdea(input) {
    var project = input.project;
    var amount = input.amount;
    var contractAddress = input.contractAddress;
    //检查入参金额
    if (amount < 0) {
        throw "{'code':'100105','msg':'金额不对'}";
    }
    var investRecord = getRecords(project);
    //检查是否为创意合约地址发起
    if (contractAddress != investRecord.contractAddress) {
        throw "{'code':'100106','msg':'发起地址不对'}";
    }
    investRecord.income = investRecord.income + amount;
    var thisAddressIncome = getAmount('income');
    thisAddressIncome = thisAddressIncome + amount;

    var transaction = {
        'operations': [{
            'type': 4,
            'set_metadata': {'key': project, 'value': investRecord}
        },{
            'type': 4,
            'set_metadata': {'key': 'income', 'value': thisAddressIncome}
        }]
    };

    callBackDoOperation(transaction);
};

const CONTRACT_STATUS = {
    CROWDFUNDING : 'crowdfunding',
    FINISH : 'finish'
};

const ACTION = {
    FINISHRAISE : 'finishRaise',
    INVEST : 'invest',
    INCOMEOFINVESTE : 'incomeOfInveste',
    SALECOPYRIGHT : 'saleCopyright',
    INCOMEOFIDEA : 'incomeOfIdea'
};

const SALE_STATUS = {
    SALEING : 'saleing',
    SOLD : 'sold',
    NOTFORSALE : 'notForSale'
};
