(function(window, jQuery, undefined) {

    var HTMLS = {
        ovl: '<div class="J_WinpopMask winpop-mask" id="J_WinpopMask"></div>' + '<div class="J_WinpopBox winpop-box" id="J_WinpopBox">' + '<div class="J_WinpopMain winpop-main"></div>' + '<div class="J_WinpopBtns winpop-btns"></div>' + '</div>',
        alert: '<input type="button" class="J_AltBtn pop-btn alert-button" value="确定">',
        confirm: '<input type="button" class="J_CfmFalse pop-btn confirm-false" value="取消">' + '<input type="button" class="J_CfmTrue pop-btn confirm-true" value="确定">'
    }

    function Winpop() {
        var config = {};
        this.get = function(n) {
            return config[n];
        }

        this.set = function(n, v) {
            config[n] = v;
        }
        this.init();
    }

    Winpop.prototype = {
        init: function() {
            this.createDom();
            this.bindEvent();
        },
        createDom: function() {
            var body = jQuery("body"),
                ovl = jQuery("#J_WinpopBox");

            if (ovl.length === 0) {
                body.append(HTMLS.ovl);
            }

            this.set("ovl", jQuery("#J_WinpopBox"));
            this.set("mask", jQuery("#J_WinpopMask"));
        },
        bindEvent: function() {
            var _this = this,
                ovl = _this.get("ovl"),
                mask = _this.get("mask");
            ovl.on("click", ".J_AltBtn", function(e) {
                _this.hide();
            });
            ovl.on("click", ".J_CfmTrue", function(e) {
                var cb = _this.get("confirmBack");
                _this.hide();
                cb && cb(true);
            });
            ovl.on("click", ".J_CfmFalse", function(e) {
                var cb = _this.get("confirmBack");
                _this.hide();
                cb && cb(false);
            });
            mask.on("click", function(e) {
                _this.hide();
            });
            jQuery(document).on("keyup", function(e) {
                var kc = e.keyCode,
                    cb = _this.get("confirmBack");;
                if (kc === 27) {
                    _this.hide();
                } else if (kc === 13) {
                    _this.hide();
                    if (_this.get("type") === "confirm") {
                        cb && cb(true);
                    }
                }
            });
        },
        alert: function(str, btnstr) {
            var str = typeof str === 'string' ? str : str.toString(),
                ovl = this.get("ovl");
            this.set("type", "alert");
            ovl.find(".J_WinpopMain").html(str);
            if (typeof btnstr == "undefined") {
                ovl.find(".J_WinpopBtns").html(HTMLS.alert);
            } else {
                ovl.find(".J_WinpopBtns").html(btnstr);
            }
            this.show();
        },
        confirm: function(str, callback) {
            var str = typeof str === 'string' ? str : str.toString(),
                ovl = this.get("ovl");
            this.set("type", "confirm");
            ovl.find(".J_WinpopMain").html(str);
            ovl.find(".J_WinpopBtns").html(HTMLS.confirm);
            this.set("confirmBack", (callback || function() {}));
            this.show();
        },
        show: function() {
            this.get("ovl").show();
            this.get("mask").show();
        },
        hide: function() {
            var ovl = this.get("ovl");
            ovl.find(".J_WinpopMain").html("");
            ovl.find(".J_WinpopBtns").html("");
            ovl.hide();
            this.get("mask").hide();
        },
        destory: function() {
            this.get("ovl").remove();
            this.get("mask").remove();
            delete window.alert;
            delete window.confirm;
        }
    };

    var obj = new Winpop();
    window.alert = function(str) {
        obj.alert.call(obj, str);
    };
    window.confirm = function(str, cb) {
        obj.confirm.call(obj, str, cb);
    };
})(window, jQuery);