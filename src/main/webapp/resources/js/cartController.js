var cartApp = angular.module('cartApp', []);

cartApp.controller(
	'cartCtrl', 
	function($scope, $http)
	{
		$scope.entireAppName = 'springtest';
		$scope.baseUrl = new Array('/'+$scope.entireAppName, 'api', 'cart').join('/');
		$scope.csrfHeader = document.getElementsByTagName('meta')['_csrf_header'].getAttribute('content');
		$scope.csrfToken = document.getElementsByTagName('meta')['_csrf'].getAttribute('content');
		$scope.cartId = '';
		
		$scope.formatUrl = function(){
			var array = new Array(arguments.length)
			for(var i=0; i<arguments.length;++i)
				array[i] = arguments[i]
			return array.join('/');
		}

		$scope.trace = function(msg){
			console.log(msg);
		}

		// 初始化购物车
		$scope.initCartId = function(cartId) {
			$scope.cartId = cartId;
			$scope.refreshCart($scope.cartId);
		};

		// 获取购物车内容
		$scope.refreshCart = function(cartId) {
			if(cartId.length<=0)
				return;

			var url = $scope.formatUrl($scope.baseUrl, $scope.cartId);
			$scope.trace(url);
			$http.get(url)
				.success(function(result) {
					$scope.trace(JSON.stringify(result));
					$scope.cart = result.data;
					$scope.trace(JSON.stringify($scope.cart));
				});
		};

		// 清空购物车
		$scope.clearCart = function() {
			var url = $scope.formatUrl($scope.baseUrl, $scope.cartId)
			$scope.trace(url);
			$http.delete(url, {
					headers : {[$scope.csrfHeader] : $scope.csrfToken}
				})
				.success(function(result) {
					$scope.trace(JSON.stringify(result));
					$scope.refreshCart($scope.cartId);
				});
		};

		// 添加到购物车
		$scope.addToCart = function(productId) {
			var url = $scope.formatUrl($scope.baseUrl, 'add', productId)
			$scope.trace(url);
			$http.put(url, {}, {
					headers : {[$scope.csrfHeader] : $scope.csrfToken}
				})
				.success(function(result) {
					$scope.trace(JSON.stringify(result));
					alert("Product Successfully added to the Cart!");
					$scope.refreshCart($scope.cartId);
				});
		};

		// 从购物车移除
		$scope.removeFromCart = function(productId) {
			var url = $scope.formatUrl($scope.baseUrl, 'remove', productId)
			$scope.trace(url);
			$http.put(url, {}, {
					headers : {[$scope.csrfHeader] : $scope.csrfToken}
				})
				.success(function(result) {
					$scope.trace(JSON.stringify(result));
					$scope.refreshCart($scope.cartId);
				});
		};
});

