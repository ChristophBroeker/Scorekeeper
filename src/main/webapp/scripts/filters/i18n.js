angular.module('skI18n', ['ngSanitize'])
    .filter('i18n', ['I18n', function (I18n) {
        return function (input, params) {
            return I18n.translate(input, params);
        };
    }])
    .filter('i18nNumber', ['I18n', function (I18n) {
        return function (input) {
            return I18n.messageFormat('{0,number,integer}', [input]);
        };
    }])
    .filter('i18nCurrency', ['I18n', function (I18n) {
        return function (input) {
            return I18n.messageFormat('{0,number,currency}', [input]);
        };
    }])
    .filter('booleanYesNo', function () {
        return function (input) {
            if (input === true || (_.isString(input) && input.toLowerCase() === 'true')) {
                return 'yes';
            } else if (input === false || (_.isString(input) && input.toLowerCase() === 'false')) {
                return 'no';
            } else {
                return input;
            }
        };
    })
    .factory('I18n', ['$http', function ($http) {
        var currencySymbols = {
            'EUR': '€'
        };

        var formatReplacers = {
            'number': function (formatStyle, parameter) {
                if (!internationalize.dictionary) {
                    return '';
                }

                var thousand = internationalize.dictionary['global.format.number.thousand'];
                var decimal = internationalize.dictionary['global.format.number.decimal'];

                if (!formatStyle || formatStyle.length === 0) {
                    // Not really supported yet
                    return parameter;
                } else if (formatStyle === 'currency') {
                    var currSymbol = internationalize.dictionary['global.format.number.currency.defaultSymbol'];
                    var currValue = 0;
                    if (!isFinite(parameter)) {
                        currValue = parseFloat(parameter.value);

                        if (currencySymbols[parameter.currency]) {
                            currSymbol = currencySymbols[parameter.currency];
                        } else {
                            currSymbol = parameter.currency;
                        }
                    } else {
                        currValue = parseFloat(parameter);
                    }

                    var format = internationalize.dictionary['global.format.number.currency.format'];
                    if (!format) {
                        format = '%s %v';
                    }

                    return accounting.formatMoney(currValue, currSymbol, 2, thousand, decimal, format);
                } else if (formatStyle === 'integer') {
                    return accounting.formatNumber(parseFloat(parameter), 0, thousand, decimal);
                }
            }
        };

        var formatReplacement = function (args, parameter) {
            if (!args || args.length === 0) {
                return parameter;
            } else {
                var formatType = args[0];
                return formatReplacers[formatType].call(this, args[1], parameter);
            }
        };

        var internationalize = {
            dictionary: undefined,
            loadDictionary: function (isoLang) {
                $http.get('i18n/' + isoLang + '.json').success(function (data) {
                    internationalize.dictionary = data;
                });
            },
            translate: function (key, params) {
                if (!internationalize.dictionary) {
                    return '';
                }

                if (key in internationalize.dictionary) {
                    var text = internationalize.dictionary[key];
                    if ((typeof params === 'undefined')) {
                        return text;
                    } else {
                        return this.messageFormat(text, params);
                    }
                } else {
                    // TODO: Push error to server

                    return '' + key;
                }
            },
            /**
             * This method should behave according to http://docs.oracle.com/javase/7/docs/api/java/text/MessageFormat.html
             */
            messageFormat: function (messageString, parameters) {
                var foundFormatElems = messageString.match(/\{.+?\}/gi);

                var replacements = _.map(foundFormatElems, function (formatElem) {
                    var content = formatElem.substring(1, formatElem.length - 1);
                    var parts = content.split(',', 3);
                    var paramIndex = parts[0];

                    return formatReplacement(_.tail(parts), parameters[paramIndex]);
                });
                var replacementMap = _.zipObject(foundFormatElems, replacements);
                var finalString = messageString;
                _.forIn(replacementMap, function (value, key) {
                    finalString = finalString.replace(key, value);
                });
                return finalString;
            }
        };

        // internationalize.loadDictionary('de_DE');

        return internationalize;
    }]);