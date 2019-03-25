angular.module('springPortfolio.services', [])
    //.constant('sockJsProtocols', ["xhr-streaming", "xhr-polling"]) // only allow XHR protocols
    .constant('sockJsProtocols', [])
    .factory('StompClient', ['sockJsProtocols', '$q', function (sockJsProtocols, $q) {
        var stompClient;
        var wrappedSocket = {
            init: function (url) {
                if (sockJsProtocols.length > 0) {
                    stompClient = webstomp.over(new SockJS(url, null, {transports: sockJsProtocols}));
                }
                else {
                    stompClient = webstomp.over(new SockJS(url));
                }
            },
            connect: function () {
                return $q(function (resolve, reject) {
                    if (!stompClient) {
                        reject("STOMP client not created");
                    } else {
                        stompClient.connect({}, function (frame) {
                            resolve(frame);
                        }, function (error) {
                            reject("STOMP protocol error " + error);
                        });
                    }
                });
            },
            disconnect: function() {
                stompClient.disconnect();
            },
            subscribe: function (destination) {
                var deferred = $q.defer();
                if (!stompClient) {
                    deferred.reject("STOMP client not created");
                } else {
                    stompClient.subscribe(destination, function (message) {
                        deferred.notify(JSON.parse(message.body));
                    });
                }
                return deferred.promise;
            },
            subscribeDave: function (destination,positions) {
            	
                var deferred = $q.defer();
                if (!stompClient) {
                    deferred.reject("STOMP client not created");
                } else {
                	var tickers = [];
                	angular.forEach(positions, function(value, key){
                		this.push("'"+key+"'");
                	},tickers);
                	var selector = "#containsAny(headers['simpDestination'],{"+tickers+"})";                	
                	//var headers = {'selector': "@websocketsInstrumentFilter.filter(headers['nativeHeaders'])"};
                	//var headers = {'selector': "@websocketsInstrumentFilter.filter()"};
                	//var headers = {'selector': "headers['nativeHeaders']['dave'].contains('melia')"};
                	// var headers = {'selector': "headers['simpDestination'].contains('MSFT')"};
                	// var headers = {'selector': "#containsAny(headers['simpDestination'],{'MSFT'})"};
                	var headers = {'selector': selector};
                	//var headers = {};
                    stompClient.subscribe(destination, function (message) {
                        deferred.notify(JSON.parse(message.body));
                    },headers);
                }
                return deferred.promise;
            },            
            subscribeSingle: function (destination) {
                return $q(function (resolve, reject) {
                    if (!stompClient) {
                        reject("STOMP client not created");
                    } else {
                        stompClient.subscribe(destination, function (message) {
                            resolve(JSON.parse(message.body));
                        });
                    }
                });
            },
            send: function (destination, object, headers) {
                stompClient.send(destination, object, headers);
            }
        };
        return wrappedSocket;
    }])
    .factory('TradeService', ['StompClient', '$q', function (stompClient, $q) {

        return {
            connect: function (url) {
                stompClient.init(url);
                return stompClient.connect().then(function (frame) {
                    return frame.headers['user-name'];
                });
            },
            disconnect: function() {
                stompClient.disconnect();
            },
            loadPositions: function() {
                return stompClient.subscribeSingle("/app/positions");
            },
            fetchQuoteStreamOld: function (positions) {
                return stompClient.subscribe("/topic/price.stock.*",positions);
            },
            fetchQuoteStream: function (ticker) {
                return stompClient.subscribe("/topic/price.stock."+ticker);
            },           
            fetchPositionUpdateStream: function () {
                return stompClient.subscribe("/user/queue/position-updates");
            },
            fetchErrorStream: function () {
                return stompClient.subscribe("/user/queue/errors");
            },
            sendTradeOrder: function(tradeOrder) {
                return stompClient.send("/app/trade", JSON.stringify(tradeOrder), {});
            }
        };

    }]);
