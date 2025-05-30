! function(e) {
    "object" == typeof module && "object" == typeof module.exports ? e(require("jquery"), window, document) : "undefined" != typeof jQuery && e(jQuery, window, document)
}(function(e, t, i, n) {
    ! function() {
        function t(e, t, i) {
            return new Array(i + 1 - e.length).join(t) + e
        }

        function n() {
            if (1 === arguments.length) {
                var t = arguments[0];
                return "string" == typeof t && (t = e.fn.timepicker.parseTime(t)), new Date(0, 0, 0, t.getHours(), t.getMinutes(), t.getSeconds())
            }
            return 3 === arguments.length ? new Date(0, 0, 0, arguments[0], arguments[1], arguments[2]) : 2 === arguments.length ? new Date(0, 0, 0, arguments[0], arguments[1], 0) : new Date(0, 0, 0)
        }
        e.TimePicker = function() {
            var t = this;
            t.container = e(".ui-timepicker-container"), t.ui = t.container.find(".ui-timepicker"), 0 === t.container.length && (t.container = e("<div></div>").addClass("ui-timepicker-container").addClass("ui-timepicker-hidden ui-helper-hidden").appendTo("body").hide(), t.ui = e("<div></div>").addClass("ui-timepicker").addClass("ui-widget ui-widget-content ui-menu").addClass("ui-corner-all").appendTo(t.container), t.viewport = e("<ul></ul>").addClass("ui-timepicker-viewport").appendTo(t.ui), e.fn.jquery >= "1.4.2" && t.ui.delegate("a", "mouseenter.timepicker", function() {
                t.activate(!1, e(this).parent())
            }).delegate("a", "mouseleave.timepicker", function() {
                t.deactivate(!1)
            }).delegate("a", "click.timepicker", function(i) {
                i.preventDefault(), t.select(!1, e(this).parent())
            }))
        }, e.TimePicker.count = 0, e.TimePicker.instance = function() {
            return e.TimePicker._instance || (e.TimePicker._instance = new e.TimePicker), e.TimePicker._instance
        }, e.TimePicker.prototype = {
            keyCode: {
                ALT: 18,
                BLOQ_MAYUS: 20,
                CTRL: 17,
                DOWN: 40,
                END: 35,
                ENTER: 13,
                HOME: 36,
                LEFT: 37,
                NUMPAD_ENTER: 108,
                PAGE_DOWN: 34,
                PAGE_UP: 33,
                RIGHT: 39,
                SHIFT: 16,
                TAB: 9,
                UP: 38
            },
            _items: function(t, i) {
                var r, a, o = this,
                    s = e("<ul></ul>"),
                    c = null;
                for (-1 === t.options.timeFormat.indexOf("m") && t.options.interval % 60 !== 0 && (t.options.interval = 60 * Math.max(Math.round(t.options.interval / 60), 1)), r = i ? n(i) : t.options.startTime ? n(t.options.startTime) : n(t.options.startHour, t.options.startMinutes), a = new Date(r.getTime() + 864e5); a > r;) o._isValidTime(t, r) && (c = e("<li>").addClass("ui-menu-item").appendTo(s), e("<a>").addClass("ui-corner-all").text(e.fn.timepicker.formatTime(t.options.timeFormat, r)).appendTo(c), c.data("time-value", r)), r = new Date(r.getTime() + 60 * t.options.interval * 1e3);
                return s.children()
            },
            _isValidTime: function(e, t) {
                var i = null,
                    r = null;
                return t = n(t), null !== e.options.minTime ? i = n(e.options.minTime) : null === e.options.minHour && null === e.options.minMinutes || (i = n(e.options.minHour, e.options.minMinutes)), null !== e.options.maxTime ? r = n(e.options.maxTime) : null === e.options.maxHour && null === e.options.maxMinutes || (r = n(e.options.maxHour, e.options.maxMinutes)), null !== i && null !== r ? t >= i && r >= t : null !== i ? t >= i : null !== r ? r >= t : !0
            },
            _hasScroll: function() {
                var e = "undefined" != typeof this.ui.prop ? "prop" : "attr";
                return this.ui.height() < this.ui[e]("scrollHeight")
            },
            _move: function(e, t, i) {
                var n = this;
                if (n.closed() && n.open(e), !n.active) return void n.activate(e, n.viewport.children(i));
                var r = n.active[t + "All"](".ui-menu-item").eq(0);
                r.length ? n.activate(e, r) : n.activate(e, n.viewport.children(i))
            },
            register: function(t, i) {
                var n = this,
                    r = {};
                r.element = e(t), r.element.data("TimePicker") || (r.options = e.metadata ? e.extend({}, i, r.element.metadata()) : e.extend({}, i), r.widget = n, e.extend(r, {
                    next: function() {
                        return n.next(r)
                    },
                    previous: function() {
                        return n.previous(r)
                    },
                    first: function() {
                        return n.first(r)
                    },
                    last: function() {
                        return n.last(r)
                    },
                    selected: function() {
                        return n.selected(r)
                    },
                    open: function() {
                        return n.open(r)
                    },
                    close: function() {
                        return n.close(r)
                    },
                    closed: function() {
                        return n.closed(r)
                    },
                    destroy: function() {
                        return n.destroy(r)
                    },
                    parse: function(e) {
                        return n.parse(r, e)
                    },
                    format: function(e, t) {
                        return n.format(r, e, t)
                    },
                    getTime: function() {
                        return n.getTime(r)
                    },
                    setTime: function(e, t) {
                        return n.setTime(r, e, t)
                    },
                    option: function(e, t) {
                        return n.option(r, e, t)
                    }
                }), n._setDefaultTime(r), n._addInputEventsHandlers(r), r.element.data("TimePicker", r))
            },
            _setDefaultTime: function(t) {
                "now" === t.options.defaultTime ? t.setTime(n(new Date)) : t.options.defaultTime && t.options.defaultTime.getFullYear ? t.setTime(n(t.options.defaultTime)) : t.options.defaultTime && t.setTime(e.fn.timepicker.parseTime(t.options.defaultTime))
            },
            _addInputEventsHandlers: function(t) {
                var i = this;
                t.element.bind("keydown.timepicker", function(e) {
                    switch (e.which || e.keyCode) {
                        case i.keyCode.ENTER:
                        case i.keyCode.NUMPAD_ENTER:
                            e.preventDefault(), i.closed() ? t.element.trigger("change.timepicker") : i.select(t, i.active);
                            break;
                        case i.keyCode.UP:
                            t.previous();
                            break;
                        case i.keyCode.DOWN:
                            t.next();
                            break;
                        default:
                            i.closed() || t.close(!0)
                    }
                }).bind("focus.timepicker", function() {
                    t.open()
                }).bind("blur.timepicker", function() {
                    setTimeout(function() {
                        t.element.data("timepicker-user-clicked-outside") && t.close()
                    })
                }).bind("change.timepicker", function() {
                    t.closed() && t.setTime(e.fn.timepicker.parseTime(t.element.val()))
                })
            },
            select: function(t, i) {
                var n = this,
                    r = t === !1 ? n.instance : t;
                n.setTime(r, e.fn.timepicker.parseTime(i.children("a").text())), n.close(r, !0)
            },
            activate: function(e, t) {
                var i = this,
                    n = e === !1 ? i.instance : e;
                if (n === i.instance) {
                    if (i.deactivate(), i._hasScroll()) {
                        var r = t.offset().top - i.ui.offset().top,
                            a = i.ui.scrollTop(),
                            o = i.ui.height();
                        0 > r ? i.ui.scrollTop(a + r) : r >= o && i.ui.scrollTop(a + r - o + t.height())
                    }
                    i.active = t.eq(0).children("a").addClass("ui-state-hover").attr("id", "ui-active-item").end()
                }
            },
            deactivate: function() {
                var e = this;
                e.active && (e.active.children("a").removeClass("ui-state-hover").removeAttr("id"), e.active = null)
            },
            next: function(e) {
                return (this.closed() || this.instance === e) && this._move(e, "next", ".ui-menu-item:first"), e.element
            },
            previous: function(e) {
                return (this.closed() || this.instance === e) && this._move(e, "prev", ".ui-menu-item:last"), e.element
            },
            first: function(e) {
                return this.instance === e ? this.active && 0 === this.active.prevAll(".ui-menu-item").length : !1
            },
            last: function(e) {
                return this.instance === e ? this.active && 0 === this.active.nextAll(".ui-menu-item").length : !1
            },
            selected: function(e) {
                return this.instance === e && this.active ? this.active : null
            },
            open: function(t) {
                var n = this,
                    r = t.getTime(),
                    a = t.options.dynamic && r;
                if (!t.options.dropdown) return t.element;
                switch (t.element.data("timepicker-event-namespace", Math.random()), e(i).bind("click.timepicker-" + t.element.data("timepicker-event-namespace"), function(e) {
                        t.element.get(0) === e.target ? t.element.data("timepicker-user-clicked-outside", !1) : t.element.data("timepicker-user-clicked-outside", !0).blur()
                    }), (t.rebuild || !t.items || a) && (t.items = n._items(t, a ? r : null)), (t.rebuild || n.instance !== t || a) && (e.fn.jquery < "1.4.2" ? (n.viewport.children().remove(), n.viewport.append(t.items), n.viewport.find("a").bind("mouseover.timepicker", function() {
                        n.activate(t, e(this).parent())
                    }).bind("mouseout.timepicker", function() {
                        n.deactivate(t)
                    }).bind("click.timepicker", function(i) {
                        i.preventDefault(), n.select(t, e(this).parent())
                    })) : (n.viewport.children().detach(), n.viewport.append(t.items))), t.rebuild = !1, n.container.removeClass("ui-helper-hidden ui-timepicker-hidden ui-timepicker-standard ui-timepicker-corners").show(), t.options.theme) {
                    case "standard":
                        n.container.addClass("ui-timepicker-standard");
                        break;
                    case "standard-rounded-corners":
                        n.container.addClass("ui-timepicker-standard ui-timepicker-corners")
                }
                n.container.hasClass("ui-timepicker-no-scrollbar") || t.options.scrollbar || (n.container.addClass("ui-timepicker-no-scrollbar"), n.viewport.css({
                    paddingRight: 40
                }));
                var o = n.container.outerHeight() - n.container.height(),
                    s = t.options.zindex ? t.options.zindex : t.element.offsetParent().css("z-index"),
                    c = t.element.offset();
                n.container.css({
                    top: c.top + t.element.outerHeight(),
                    left: c.left
                }), n.container.show(), n.container.css({
                    left: t.element.offset().left,
                    height: n.ui.outerHeight() + o,
                    width: t.element.outerWidth(),
                    zIndex: s,
                    cursor: "default"
                });
                var u = n.container.width() - (n.ui.outerWidth() - n.ui.width());
                return n.ui.css({
                    width: u
                }), n.viewport.css({
                    width: u
                }), t.items.css({
                    width: u
                }), n.instance = t, r ? t.items.each(function() {
                    var i, a = e(this);
                    return i = e.fn.jquery < "1.4.2" ? e.fn.timepicker.parseTime(a.find("a").text()) : a.data("time-value"), i.getTime() === r.getTime() ? (n.activate(t, a), !1) : !0
                }) : n.deactivate(t), t.element
            },
            close: function(t) {
                var n = this;
                return n.instance === t && (n.container.addClass("ui-helper-hidden ui-timepicker-hidden").hide(), n.ui.scrollTop(0), n.ui.children().removeClass("ui-state-hover")), e(i).unbind("click.timepicker-" + t.element.data("timepicker-event-namespace")), t.element
            },
            closed: function() {
                return this.ui.is(":hidden")
            },
            destroy: function(e) {
                var t = this;
                return t.close(e, !0), e.element.unbind(".timepicker").data("TimePicker", null)
            },
            parse: function(t, i) {
                return e.fn.timepicker.parseTime(i)
            },
            format: function(t, i, n) {
                return n = n || t.options.timeFormat, e.fn.timepicker.formatTime(n, i)
            },
            getTime: function(t) {
                var i = this,
                    n = e.fn.timepicker.parseTime(t.element.val());
                return n instanceof Date && !i._isValidTime(t, n) ? null : n instanceof Date && t.selectedTime ? t.format(n) === t.format(t.selectedTime) ? t.selectedTime : n : n instanceof Date ? n : null
            },
            setTime: function(t, i, r) {
                var a = this,
                    o = t.selectedTime;
                if ("string" == typeof i && (i = t.parse(i)), i && i.getMinutes && a._isValidTime(t, i)) {
                    if (i = n(i), t.selectedTime = i, t.element.val(t.format(i, t.options.timeFormat)), r) return t
                } else t.selectedTime = null;
                return null === o && null === t.selectedTime || (t.element.trigger("time-change", [i]), e.isFunction(t.options.change) && t.options.change.apply(t.element, [i])), t.element
            },
            option: function(t, i, n) {
                if ("undefined" == typeof n) return t.options[i];
                var r, a, o = t.getTime();
                "string" == typeof i ? (r = {}, r[i] = n) : r = i, a = ["minHour", "minMinutes", "minTime", "maxHour", "maxMinutes", "maxTime", "startHour", "startMinutes", "startTime", "timeFormat", "interval", "dropdown"], e.each(r, function(i) {
                    t.options[i] = r[i], t.rebuild = t.rebuild || e.inArray(i, a) > -1
                }), t.rebuild && t.setTime(o)
            }
        }, e.TimePicker.defaults = {
            timeFormat: "hh:mm p",
            minHour: null,
            minMinutes: null,
            minTime: null,
            maxHour: null,
            maxMinutes: null,
            maxTime: null,
            startHour: null,
            startMinutes: null,
            startTime: null,
            interval: 30,
            dynamic: !0,
            theme: "standard",
            zindex: null,
            dropdown: !0,
            scrollbar: !1,
            change: function() {}
        }, e.TimePicker.methods = {
            chainable: ["next", "previous", "open", "close", "destroy", "setTime"]
        }, e.fn.timepicker = function(t) {
            if ("string" == typeof t) {
                var i, n, r = Array.prototype.slice.call(arguments, 1);
                return i = "option" === t && arguments.length > 2 ? "each" : -1 !== e.inArray(t, e.TimePicker.methods.chainable) ? "each" : "map", n = this[i](function() {
                    var i = e(this),
                        n = i.data("TimePicker");
                    return "object" == typeof n ? n[t].apply(n, r) : void 0
                }), "map" === i && 1 === this.length ? e.makeArray(n).shift() : "map" === i ? e.makeArray(n) : n
            }
            if (1 === this.length && this.data("TimePicker")) return this.data("TimePicker");
            var a = e.extend({}, e.TimePicker.defaults, t);
            return this.each(function() {
                e.TimePicker.instance().register(this, a)
            })
        }, e.fn.timepicker.formatTime = function(e, i) {
            var n = i.getHours(),
                r = n % 12,
                a = i.getMinutes(),
                o = i.getSeconds(),
                s = {
                    hh: t((0 === r ? 12 : r).toString(), "0", 2),
                    HH: t(n.toString(), "0", 2),
                    mm: t(a.toString(), "0", 2),
                    ss: t(o.toString(), "0", 2),
                    h: 0 === r ? 12 : r,
                    H: n,
                    m: a,
                    s: o,
                    p: n > 11 ? "PM" : "AM"
                },
                c = e,
                u = "";
            for (u in s) s.hasOwnProperty(u) && (c = c.replace(new RegExp(u, "g"), s[u]));
            return c = c.replace(new RegExp("a", "g"), n > 11 ? "pm" : "am")
        }, e.fn.timepicker.parseTime = function() {
            var t = [
                    [/^(\d+)$/, "$1"],
                    [/^:(\d)$/, "$10"],
                    [/^:(\d+)/, "$1"],
                    [/^(\d):([7-9])$/, "0$10$2"],
                    [/^(\d):(\d\d)$/, "$1$2"],
                    [/^(\d):(\d{1,})$/, "0$1$20"],
                    [/^(\d\d):([7-9])$/, "$10$2"],
                    [/^(\d\d):(\d)$/, "$1$20"],
                    [/^(\d\d):(\d*)$/, "$1$2"],
                    [/^(\d{3,}):(\d)$/, "$10$2"],
                    [/^(\d{3,}):(\d{2,})/, "$1$2"],
                    [/^(\d):(\d):(\d)$/, "0$10$20$3"],
                    [/^(\d{1,2}):(\d):(\d\d)/, "$10$2$3"]
                ],
                i = t.length;
            return function(r) {
                var a = n(new Date),
                    o = !1,
                    s = !1,
                    c = !1,
                    u = !1,
                    l = !1;
                if ("undefined" == typeof r || !r.toLowerCase) return null;
                r = r.toLowerCase(), o = /a/.test(r), s = o ? !1 : /p/.test(r), r = r.replace(/[^0-9:]/g, "").replace(/:+/g, ":");
                for (var m = 0; i > m; m += 1)
                    if (t[m][0].test(r)) {
                        r = r.replace(t[m][0], t[m][1]);
                        break
                    } return r = r.replace(/:/g, ""), 1 === r.length ? c = r : 2 === r.length ? c = r : 3 === r.length || 5 === r.length ? (c = r.substr(0, 1), u = r.substr(1, 2), l = r.substr(3, 2)) : (4 === r.length || r.length > 5) && (c = r.substr(0, 2), u = r.substr(2, 2), l = r.substr(4, 2)), r.length > 0 && r.length < 5 && (r.length < 3 && (u = 0), l = 0), c === !1 || u === !1 || l === !1 ? !1 : (c = parseInt(c, 10), u = parseInt(u, 10), l = parseInt(l, 10), o && 12 === c ? c = 0 : s && 12 > c && (c += 12), c > 24 ? r.length >= 6 ? e.fn.timepicker.parseTime(r.substr(0, 5)) : e.fn.timepicker.parseTime(r + "0" + (o ? "a" : "") + (s ? "p" : "")) : (a.setHours(c, u, l), a))
            }
        }()
    }()
});