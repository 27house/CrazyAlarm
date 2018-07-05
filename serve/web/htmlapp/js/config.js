var BASE_URL = "http://192.168.0.136:8080/crazy";
var FADE_TIME = 300;


/**
 * @return {string}
 */
function Get(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r != null) {
		return unescape(r[2]);
	}
	return null;
}

function isPassive() {
	var supportsPassiveOption = false;
	try {
		addEventListener("test", null, Object.defineProperty({}, 'passive', {
			get: function() {
				supportsPassiveOption = true;
			}
		}));
	} catch(e) {}
	return supportsPassiveOption;
}

// ========== 使用到的方法 ==========

var dateObj = (function() {
	var _date = new Date();

	return {
		getDate: function() {
			return _date;
		},

		setDate: function(date) {
			_date = date;
		}
	}
})();

function returnDateStr(date) { // 日期转字符串
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var day = date.getDate();

	month = month < 9 ? ('0' + month) : ('' + month);
	day = day < 9 ? ('0' + day) : ('' + day);

	return year + month + day;
};

function changingStr(fDate) { // 字符串转日期
	var fullDate = fDate.split("-");

	return new Date(fullDate[0], fullDate[1] - 1, fullDate[2]);
};