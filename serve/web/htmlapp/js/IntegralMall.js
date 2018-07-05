var dates = [];
var account = Get("account");
getIntegral();
getSignDates();
initSignBtn();
initCalSignBtn();

function getIntegral() {
	$.get(BASE_URL + "/integral?type=get&account=" + account, function(data, status) {
		if(status === "success") {
			var myObject = JSON.parse(data);
			if(myObject.result === 0) {
				$("#integral").html(myObject.data);
			} else {
				$("#integral").html("error:" + myObject.result);
			}
		}
	});
}

function getSignDates() {
	var myDate = new Date()
	var url = BASE_URL + "/integral?type=get_sign&account=" + account + "&year=" + myDate.getFullYear() + "&month=" + (myDate.getMonth() + 1);
	$.get(url, function(data, status) {
		if(status === "success") {
			console.log("data--" + data);
			var obj = JSON.parse(data);
			if(obj.result === 0) {
				dates = obj.list;
			}
		}
		initCal();
		initSignBtn();
	});
}

function initSignBtn() {
	$("#btn_sign").html(isSign(returnDateStr(new Date())) ? "已签到" : "签到")
		.css("background-color", isSign(returnDateStr(new Date())) ? "#d5d5d5" : "#FFA500")
		.click(function() {
			setSelsetDay(returnDateStr(new Date()));
			initCal();
			$("#box_bg").fadeIn(FADE_TIME);
		});
}

function initCalSignBtn() {
	$("#btn_cal_sign").click(function() {
		if(!isSign(selectDate)) {
			if(selectDate < returnDateStr(new Date())) {
				// 补签
				alert("暂时没有补签券！");
				/*$.post(BASE_URL + "/integral?type=sign", {
				        account: account,
				        date: selectDate,
				        status: 1
				    },
				    function(data, status) {
				        alert("Data: " + data + "\nStatus: " + status);
				        jQuery('.box_bg').fadeOut(FADE_TIME);
				        getIntegral();
				        getSignDates();
				    });*/
			} else if(selectDate > returnDateStr(new Date())) {
				//未到时间
				alert("还未到时间哦！");
			} else {
				// 签到
				$.post(BASE_URL + "/integral?type=sign", {
						account: account,
						date: selectDate,
						status: 0
					},
					function(data, status) {
						alert("Data: " + data + "\nStatus: " + status);
						jQuery('.box_bg').fadeOut(FADE_TIME);
						getIntegral();
						getSignDates();
					});

			}

		} else {
			jQuery('.box_bg').fadeOut(FADE_TIME);
		}
	});
	$("#img_close").click(function() {
		jQuery('.box_bg').fadeOut(FADE_TIME);
	});
}

/**
 * 判断某一天是否已签到
 * @param {Object} dayStr 格式：20180701
 */
function isSign(dayStr) {
	var has = false;
	for(d in dates) {
		if(dayStr === dates[d]) {
			has = true;
			break;
		}
	}
	return has;
}

var selectDate;

function setSelsetDay(day) {
	selectDate = day;
	if(!isSign(day)) {
		if(day < returnDateStr(new Date())) {
			// 小于今天
			$("#btn_cal_sign")
				.html("补签")
				.css("background-color", "#1dc33a");
		} else if(day > returnDateStr(new Date())) {
			//未来
			$("#btn_cal_sign")
				.html("未到时间")
				.css("background-color", "#DCDCDC");
		} else {
			// 今天
			$("#btn_cal_sign")
				.html("签到+10")
				.css("background-color", "#FFA500");
		}

	} else {
		$("#btn_cal_sign")
			.html("已签到")
			.css("background-color", "#DCDCDC");
	}
}