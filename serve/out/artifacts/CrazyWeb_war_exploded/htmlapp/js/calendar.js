// 关于月份： 在设置时要-1，使用时要+1

function initCal() {
	$('#calendar').html("");
	$('#calendar').calendar({
		ifSwitch: true, // 是否切换月份
		hoverDate: true, // hover是否显示当天信息
		backToday: true // 是否返回当天
	});
}

(function($, window, document, undefined) {

	var Calendar = function(elem, options) {
		this.$calendar = elem;

		this.defaults = {
			ifSwitch: true,
			hoverDate: false,
			backToday: false
		};

		this.opts = $.extend({}, this.defaults, options);

		// console.log(this.opts);
	};

	Calendar.prototype = {
		showHoverInfo: function(obj) { // hover 时显示当天信息
			var _dateStr = $(obj).attr('data');
			// 选中item的位置  t 上 、l 左
			var offset_t = $(obj).offset().top + (this.$calendar_today.height() - $(obj).height()) / 2;
			var offset_l = $(obj).offset().left + $(obj).width();
			var changeStr = _dateStr.substr(0, 4) + '-' + _dateStr.substr(4, 2) + '-' + _dateStr.substring(6);
			var _week = changingStr(changeStr).getDay();
			var _weekStr = '';

			this.$calendar_today.show();

			this.$calendar_today
				.css({
					left: offset_l + 30,
					top: offset_t
				})
				.stop()
				.animate({
					left: offset_l + 16,
					top: offset_t,
					opacity: 1
				});

			switch(_week) {
				case 0:
					_weekStr = '星期日';
					break;
				case 1:
					_weekStr = '星期一';
					break;
				case 2:
					_weekStr = '星期二';
					break;
				case 3:
					_weekStr = '星期三';
					break;
				case 4:
					_weekStr = '星期四';
					break;
				case 5:
					_weekStr = '星期五';
					break;
				case 6:
					_weekStr = '星期六';
					break;
			}

			this.$calendarToday_date.text(changeStr);
			this.$calendarToday_week.text(_weekStr);
		},
		showCalendar: function() { // 输入数据并显示
			var self = this;
			var year = dateObj.getDate().getFullYear();
			var month = dateObj.getDate().getMonth() + 1;
			var dateStr = returnDateStr(dateObj.getDate());
			var firstDay = new Date(year, month - 1, 1); // 当前月的第一天

			this.$calendarTitle_text.text(year + '/' + dateStr.substr(4, 2));

			this.$calendarDate_item.each(function(i) {
				// allDay: 得到当前列表显示的所有天数
				var allDay = new Date(year, month - 1, i + 1 - firstDay.getDay());
				var allDay_str = returnDateStr(allDay);

				$(this).text(allDay.getDate()).attr('data', allDay_str);
				if(returnDateStr(new Date()) === allDay_str) {
					$(this).html("今天");
					$(this).css('font-size', '10px');
					$(this).css('color', '#FF0000');
				} else if(returnDateStr(firstDay).substr(0, 6) === allDay_str.substr(0, 6)) {
					// 给属于本月的 改变文字颜色
					// 给属于本月的 改变文字颜色
					$(this).css('font-size', '12px');
					$(this).css('color', '#333333');
				} else {
					$(this).css('font-size', '12px');
					$(this).css('color', '#999999');
				}

				if(selectDate === allDay_str) {
					// 选中的
					$(this)
						.css('border-bottom', '#FFA500 solid 2px')
						.css('border-left', '#ffffff solid 0px')
						.css('border-top', '#ffffff solid 0px')
						.css('border-right', '#ffffff solid 0px');
				} else {
					$(this)
						.css('border-bottom', '#ffffff solid 2px')
						.css('border-left', '#ffffff solid 0px')
						.css('border-top', '#ffffff solid 0px')
						.css('border-right', '#ffffff solid 0px');
				}
				if(isSign(allDay_str)) {
					$(this).css("background-image", "url(img/ic_sign_2.png)")
						.css('background-repeat', 'no-repeat')
						.css('background-position', 'center');
				}
				// 70:dialog的宽、88：calendar的宽、14item的宽
				var h = ((($(window).width() * 70 / 100) * 88 / 100) * 14 / 100) - 4 + 'px';
				$(this).width('14%');
				$(this).height(h);
				$(this).css('line-height', h);

			});
		},

		renderDOM: function() { // 渲染DOM
			this.$calendar_title = $('<div class="calendar-title"></div>');
			this.$calendar_week = $('<ul class="calendar-week"></ul>');
			this.$calendar_date = $('<ul class="calendar-date"></ul>');
			this.$calendar_today = $('<div class="calendar-today"></div>');

			var _titleStr = '<a href="#" class="title"></a>' +
				'<a href="javascript:;" id="backToday">T</a>' +
				'<div class="arrow">' +
				'<span class="arrow-prev"><</span>' +
				'<span class="arrow-next">></span>' +
				'</div>';
			var _weekStr = '<li class="item">日</li>' +
				'<li class="item">一</li>' +
				'<li class="item">二</li>' +
				'<li class="item">三</li>' +
				'<li class="item">四</li>' +
				'<li class="item">五</li>' +
				'<li class="item">六</li>';
			var _dateStr = '';
			var _dayStr = '<i class="triangle"></i>' +
				'<p class="date"></p>' +
				'<p class="week"></p>';

			for(var i = 0; i < 6; i++) {
				_dateStr += '<li class="item">26</li>' +
					'<li class="item">26</li>' +
					'<li class="item">26</li>' +
					'<li class="item">26</li>' +
					'<li class="item">26</li>' +
					'<li class="item">26</li>' +
					'<li class="item">26</li>';
			}

			this.$calendar_title.html(_titleStr);
			this.$calendar_week.html(_weekStr);
			this.$calendar_date.html(_dateStr);
			this.$calendar_today.html(_dayStr);

			this.$calendar.append(this.$calendar_title, this.$calendar_week, this.$calendar_date, this.$calendar_today);
			this.$calendar.show();
		},

		inital: function() { // 初始化
			var self = this;

			this.renderDOM();

			this.$calendarTitle_text = this.$calendar_title.find('.title');
			this.$backToday = $('#backToday');
			this.$arrow_prev = this.$calendar_title.find('.arrow-prev');
			this.$arrow_next = this.$calendar_title.find('.arrow-next');
			this.$calendarDate_item = this.$calendar_date.find('.item');
			this.$calendarToday_date = this.$calendar_today.find('.date');
			this.$calendarToday_week = this.$calendar_today.find('.week');

			this.showCalendar();

			if(this.opts.backToday) {
				this.$backToday.bind('click', function() {
					if(!self.$calendarDate_item.hasClass('item-curDay')) {
						dateObj.setDate(new Date());

						self.showCalendar();
					}
				});
			}

			this.$calendarDate_item.hover(function() {
				// 显示标签
				//				self.showHoverInfo($(this));
				var _dateStr = $(this).attr('data');
				setSelsetDay(_dateStr);
				self.showCalendar();
			}, function() {
				self.$calendar_today.css({
					left: 0,
					top: 0
				}).hide();
			});
		},

		constructor: Calendar
	};

	$.fn.calendar = function(options) {
		var calendar = new Calendar(this, options);

		return calendar.inital();
	};

})(jQuery, window, document);