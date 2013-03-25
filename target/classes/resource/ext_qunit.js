/**
 * 重载QUnit部分接口实现批量执行控制功能
 */
(function() {
	if (!QUnit)
		return;

	var ms = QUnit.moduleStart, d = QUnit.done;

	QUnit.moduleStart = function() {
		var h = setInterval(function() {
			if (window && window['baidu']) {
				clearInterval(h);
				ms.apply(this, arguments);
				start();
			}
		}, 20);
		stop();
	};

	QUnit.done = function() {
	
//	     arguments[0] && console.log(arguments[0]);
//        arguments[1] && console.log(arguments[1]);
           
		if (top && top.probe) {
			top.$(top.probe).trigger('done', [ arguments, _cov() ]);
		}
	/*	
		if (top && top.brtest) {
			top.$(top.brtest).trigger('done', arguments);
		}*/
		d.apply(this, arguments);
	};

	function _cov() {
		var cov = window._$jscoverage;
		if (cov != undefined) {
			for(var key in cov){
				delete cov[key]['source'];
			}
		}
		return cov;
	}
	
	push = function(result, actual, expected, message) {
    	message = message || (result ? "okay" : "failed");
        QUnit.ok( result, result ? message + ": " + expected : message + ", expected: " + QUnit.jsDump.parse(expected) + " result: " + QUnit.jsDump.parse(actual) );
    };
    
    approximateEqual = function(actual, expected, difference, message){
    	if(typeof difference == "string"){
    		var message = difference;
    		var difference = 1;
    	}
    	push(Math.abs(parseInt(actual) - parseInt(expected)) <= difference, actual, expected, message);
    };
})();
