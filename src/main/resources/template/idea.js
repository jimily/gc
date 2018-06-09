const registrant = '{temp_registrant}';

function main(inputData) {
    callBackLog("inputData: " + inputData);
    try {
        var input = JSON.parse(inputData);
    } catch (error) {
        return;
    }

    var action = input.action;
    switch (action) {
        case ACTION.CREATE :
            create(input);
            break;
        case ACTION.INVEST :
            invest(input)
            break;
        case ACTION.FINISH :
            finish();
            break;
        case ACTION.PROFIT :
            profit(input);
            break;
        default :
            throw "{'code':'100100','msg':'无效的操作类型'}";
    }
}
/** 创意登记**/
function create(input) {
    if (sender != registrant) {
        throw "{'code':'100104','msg':'该操作需要是创意者本人哟'}";
    }

    var info = {
        "name": input.name,//项目名
        "type": 'ideas',//合约类型
        "classify": input.classify,//项目类别
        "rate": input.rate,   //众筹占比
        "registrant": registrant//登记人地址
    }

    var total = parseInt(input.totalCost * input.rate / 100);
    var transaction = {
        'operations': [{
            'type': 4,
            'set_metadata': {'key': 'info', 'value': JSON.stringify(info)}
        },{
            'type': 4,
            'set_metadata': {'key': 'contractStatus', 'value': CONTRACT_STATUS.CROWDFUNDING}
        },{
            'type': 4,
            'set_metadata': {'key': 'totalCost', 'value': input.totalCost}
        },{
            'type': 4,
            'set_metadata': {'key': 'total', 'value': total}
        },{
            'type': 4,
            'set_metadata': {'key': 'balance', 'value': total}
        },{
            'type': 4,
            'set_metadata': {'key': 'lowestLimit', 'value': input.lowestLimit}
        }]
    };

    callBackDoOperation(transaction);

}

function getAmount(key) {
    var amount = callBackGetAccountMetaData(thisAddress, key);
    if (amount) {
        return amount.value;
    }
    return 0;
};

/** 创意众筹**/
function invest(input) {
    //投资人
    var investor = sender;
    //投资金额
    var amount = input.amount;
    //检查合约状态
    checkContractStatus();
    //检查合约余额
    var balance = getAmount('balance');
    if (balance == 0 || balance < amount) {
        throw "{'code':'100102','msg':'太有钱啦，不够额度给您投资'}";
    }
    //单次购买下限
    var lowestLimit = getAmount('lowestLimit');
    if (amount < lowestLimit) {
        throw "{'code':'100103','msg':'这是一个有底线的项目'}";
    }

    //获取总成本
    var totalCost = getAmount('totalCost');
    //计算本次投资得到的占比
    var investRate = amount / totalCost * 100;
    var investRecords = getInvestorRecords();
    var isExist = false;
    for (var i = 0; i < investRecords.length; i++) {
        var investRecord = investRecords[i];
        if (investRecord.address == investor) {
            isExist = true;
            investRecord.amount = investRecord.amount + amount;
            investRecord.rate = investRecord.rate + investRate;
        }
    }
    if (isExist == false) {
        investRecords.push({
            'address':investor,
            'amount':amount,
            'rate':investRate
        })
    }

    balance = balance - amount;
    //新增/修改该投资者的记录
    var transaction = {
        'operations': [{
            'type': 4,
            'set_metadata': {'key': 'investors', 'value': JSON.stringify(investRecords)}
        },{
            'type':4,
            'set_metadata': {'key':'balance', 'value': balance}
        }]
    };

    var isSuccess = callBackDoOperation(transaction);
    if (!isSuccess) {
        throw "{'code':'100113','msg':'投资失败'}";
    }

    if (balance == 0) {
        var transaction = {
            'operations': [{
                'type': 4,
                'set_metadata': {'key': 'contractStatus', 'value': CONTRACT_STATUS.FINISH}
            }]
        };

        callBackDoOperation(transaction);
        settlement();

    }
}

/** 结束众筹**/
function finish() {

    if (sender != registrant) {
        throw "{'code':'100104','msg':'该操作需要是创意者本人哟'}";
    }
    checkContractStatus();
    var transaction = {
        'operations': [{
            'type': 4,
            'set_metadata': {'key': 'contractStatus', 'value': CONTRACT_STATUS.FINISH}
        }]
    };

    callBackDoOperation(transaction);

    settlement();
    return ;

}

/** 众筹结束将额度转给创意者**/
function settlement() {
    var info = JSON.parse(callBackGetAccountMetaData(thisAddress, 'info').value);
    var total = info.total;
    var balance = getAmount('balance');
    var registrantInput = {
        'action':'finishRaise',
        'project':'ideas'+info.classify+info.name+thisAddress,
        'amount':total - balance
    };

    var transaction = {
        'operations': [{
            'type': 3,
            'payment': {
                'dest_address': registrant
            },
            'input': JSON.stringify(registrantInput)
        }]
    };

    callBackDoOperation(transaction);
}

/** 创意分润**/
function profit(input) {
    if (sender != registrant) {
        throw "{'code':'100108','msg':'分润需由创意者触发'}";
    }
    if (input.amount <= 0) {
        throw "{'code':'100105','msg':'金额不对'}";
    }
    var investorRecords = getInvestorRecords();
    if (investorRecords.length == 0) {
        return;
    }
    var info = JSON.parse(callBackGetAccountMetaData(thisAddress, 'info').value);
    var project = info.classify + info.name + thisAddress;
    var operations = [];
    var balance = input.amount;
    for (var i = 0 ; i < investorRecords.length; i++) {
        var investor = investorRecords[i];
        var profit = paseInt(input.amount * investor.rate / 100);
        balance = balance - profit;
        var incomeInput = {
            'project':'invest' + project,
            'amount': profit,
            'contractAddress': thisAddress,
            'action':'incomeOfInveste'
        };
        operations.push({
            'type': 3,
            'payment': {
                'dest_address': investor.address
            },
            'input': JSON.stringify(incomeInput)
        });
    }
    var incomeInput = {
        'contractAddress':thisAddress,
        'project':'ideas' + project,
        'amount': balance,
        'action':'incomeOfIdea'
    };
    operations.push({
        'type': 3,
        'payment': {
            'dest_address': registrant
        },
        'input': JSON.stringify(incomeInput)
    });
    var transaction = {
        'operations': operations
    };

    callBackDoOperation(transaction);
}

/** 检查众筹状态**/
function checkContractStatus() {

    var contractStatus = callBackGetAccountMetaData(thisAddress, 'contractStatus');
    if (contractStatus.value == CONTRACT_STATUS.FINISH) {
        throw "{'code':'100105','msg':'合约已结束'}";
    }
}

/** 获取投资记录**/
function getInvestorRecords() {
    var records = [];

    var metadata = callBackGetAccountMetaData(thisAddress, 'investors');
    if (metadata) {
        records = JSON.parse(metadata.value);
    }

    return records;
};

const CONTRACT_STATUS = {
    CROWDFUNDING : 'crowdfunding',
    FINISH : 'finish'
};

const ACTION = {
    CREATE : 'create',
    INVEST : 'invest',
    FINISH : 'finish',
    PROFIT : 'profit'
};
