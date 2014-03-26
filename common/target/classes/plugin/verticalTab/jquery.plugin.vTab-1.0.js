(function($) {
	$.fn.vTab = function(options) {
		options = $.extend($.fn.vTab.defaults , options);

		this.each(function(idx) {
			var $this = $(this);
			var $$this = null;
			var state = null;
			var $title = $this.find(">h3");
			var $list = $this.find(">div");

			$this.addClass(options.classnm);
			$title.addClass("title");
			$list.addClass("list").hide();

			if (options.isActive) {
				$title.eq(options.activeNumber).addClass("active");
				$list.eq(options.activeNumber).addClass("active").show();
			}

			$title.each(function(index) {
				$$this = $(this);
				if ($.trim($list.eq(index).html()).length > 0) {
					$$this.append("<span></span>");
				}
				$$this.find("a[href=#]").click(function() {

					if ($list.eq(index).hasClass("active") && !options.isClosed) {
						return false;
					}
					if (options.isClosed) {
						state = $title.eq(index).hasClass("active");
					}

					options.startFn();

					$title.removeClass("active");
					$list.removeClass("active").slideUp(options.delay);

					if (options.isClosed && !state) {
						$title.eq(index).addClass("active");
						$list.eq(index).addClass("active").slideDown(options.delay);
						return false;
					} else if (options.isClosed && state) {
						return false;
					}
					$title.eq(index).toggleClass("active");
					$list.eq(index).toggleClass("active").slideToggle(options.delay , function() {
						options.completeFn();
					});
					return false;
				});
			});
		});
	};
	$.fn.vTab.defaults = {
			"isActive" : true ,
			"isClosed" : false ,
			"activeNumber" : 0 ,
			"delay" : 300 ,
			"startFn" : function() {},
			"completeFn" : function() {},
			"classnm" : "vTab"
	};
})(jQuery);